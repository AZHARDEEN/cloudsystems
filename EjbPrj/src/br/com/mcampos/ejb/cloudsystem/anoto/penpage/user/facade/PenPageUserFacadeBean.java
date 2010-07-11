package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.facade;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.session.PenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.PenPageUserUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity.PenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.session.PenPageUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
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
    private PenPageUserSessionLocal penPageUserSession;

    @EJB
    private PenPageSessionLocal penPageSession;

    @EJB
    private AnotoFormSessionLocal formSession;

    @EJB
    private CompanySessionLocal companySession;


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
        List<AnotoPenPage> penPages = ( List<AnotoPenPage> )penPageSession.get( form );
        if ( SysUtils.isEmpty( penPages ) )
            return Collections.emptyList();
        List<PenUserDTO> list = new ArrayList<PenUserDTO>( penPages.size() );
        PenUserDTO dto;
        for ( AnotoPenPage p : penPages ) {
            dto = new PenUserDTO();
            dto.setPenPage( p.toDTO() );
            if ( list.contains( dto ) )
                continue;
            PenUser user = null; //penPageUserSession.getCurrent( p );
            if ( user != null ) {
                dto = PenPageUserUtil.copy( user );
            }
            list.add( dto );
        }
        return list;
    }
}
