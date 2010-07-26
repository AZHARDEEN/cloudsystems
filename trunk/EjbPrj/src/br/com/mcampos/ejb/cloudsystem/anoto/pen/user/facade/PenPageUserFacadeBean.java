package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.facade;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session.AnotoPenUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.session.PenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "PenPageUserFacade", mappedName = "CloudSystems-EjbPrj-PenPageUserFacade" )
@Remote
public class PenPageUserFacadeBean extends AbstractSecurity implements PenPageUserFacade
{

    protected static final int SystemMessageTypeId = 34;
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private AnotoPenUserSessionLocal penUserSession;

    @EJB
    private PenPageSessionLocal penPageSession;

    @EJB
    private AnotoFormSessionLocal formSession;

    @EJB
    private ClientSessionLocal clienSession;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private AnodePenSessionLocal penSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }


    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        return AnotoUtils.toFormList( formSession.getAll( company ) );
    }

    public List<PenUserDTO> getAll( AuthenticationDTO auth, Integer formId, Boolean bActive ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = formSession.get( formId );
        if ( form == null )
            throwException( 1 );
        List<AnotoPenPage> penPages = penPageSession.get( form );
        if ( SysUtils.isEmpty( penPages ) )
            return Collections.emptyList();
        List<PenUserDTO> list = new ArrayList<PenUserDTO>( penPages.size() );
        PenUserDTO dto;
        for ( AnotoPenPage p : penPages ) {
            dto = new PenUserDTO();
            dto.setPenPage( p.toDTO() );
            if ( list.contains( dto ) )
                continue;
            AnotoPenUser user = penUserSession.getCurrentUser( p.getPen().getId() );
            if ( user != null ) {
                dto.setUser( UserUtil.copy( user.getPerson() ) );
                dto.setFromDate( user.getFromDate() );
            }
            list.add( dto );
        }
        return list;
    }

    public List<ListUserDTO> getCollaborators( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        List<Client> list = clienSession.getAllPersonClients( company );
        return ClientUtil.toUserDTOList( list );
    }


    public void changeUser( AuthenticationDTO auth, String penId, Integer userId ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( penId );
        if ( pen == null )
            throwException( 2 );
        Person person = personSession.get( userId );
        if ( person == null )
            throwException( 3 );
        AnotoPenUser penUser = new AnotoPenUser( pen, person );
        penUserSession.add( penUser );

    }
}
