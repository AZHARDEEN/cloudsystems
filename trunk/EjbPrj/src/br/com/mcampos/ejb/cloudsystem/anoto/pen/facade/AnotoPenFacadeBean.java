package br.com.mcampos.ejb.cloudsystem.anoto.pen.facade;


import br.com.mcampos.dto.anoto.LinkedUserDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPenUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session.AnotoPenUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoPenFacade", mappedName = "CloudSystems-EjbPrj-AnotoPenFacade" )
@TransactionAttribute( value = TransactionAttributeType.REQUIRES_NEW )
public class AnotoPenFacadeBean extends AbstractSecurity implements AnotoPenFacade
{

    protected static final int SystemMessageTypeId = 29;
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private AnodePenSessionLocal penSession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private AnotoPenUserSessionLocal penUserSession;

    @EJB
    private UserDocumentSessionLocal userDocument;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private ClientSessionLocal clientSession;

    public AnotoPenFacadeBean()
    {
    }


    public List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        List<PenDTO> pens = AnotoUtils.toPenList( penSession.getAll() );
        return linkToUser( pens );
    }

    private List<PenDTO> linkToUser( List<PenDTO> pens ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( pens ) )
            return Collections.emptyList();
        for ( PenDTO pen : pens ) {
            linkToUser( pen );
        }
        return pens;
    }

    private PenDTO linkToUser( PenDTO pen ) throws ApplicationException
    {
        if ( pen == null )
            return null;
        AnotoPenUser penUser = penUserSession.getCurrentUser( pen.getId() );
        if ( penUser != null ) {
            LinkedUserDTO user = new LinkedUserDTO();
            user.setUser( UserUtil.copy( penUser.getPerson() ) );
            pen.setUser( user );
        }
        else {
            pen.setUser( null );
        }
        return pen;
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }


    public void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen == null )
            throwException( 1 );
        penSession.delete( entity.getId() );
    }


    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen == null )
            throwException( 2 );
        pen = penSession.update( AnotoPenUtil.update( pen, entity ) );
        AnotoPenUser penUser = penUserSession.getCurrentUser( pen.getId() );
        if ( entity.getUser() != null && entity.getUser().getUser() != null ) {
            ListUserDTO userDto = entity.getUser().getUser();
            Person person = personSession.get( userDto.getId() );
            if ( penUser != null ) {
                if ( penUser.getPerson().equals( person ) == false ) {
                    penUserSession.delete( penUser );
                }
                penUser.setPerson( person );
            }
            else {
                penUser = new AnotoPenUser( pen, person );
            }
            penUser = penUserSession.add( penUser );
        }
        else {
            if ( penUser != null )
                penUserSession.delete( penUser );
        }
        return linkToUser( pen.toDTO() );
    }


    public PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        if ( entity == null )
            return null;
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen != null )
            throwException( 5 );
        Person person = null;
        if ( entity.getUser() != null && entity.getUser().getUser() != null )
            person = personSession.get( entity.getUser().getUser().getId() );
        return penSession.add( AnotoPenUtil.createEntity( entity ), person ).toDTO();
    }

    public PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        PenDTO dto = penSession.get( entity.getId() ).toDTO();
        return linkToUser( dto );
    }

    public ListUserDTO findUser( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        if ( userId == null )
            return null;
        authenticate( auth );
        Person person = personSession.get( userId );
        return person != null ? UserUtil.copy( person ) : null;
    }

    public ListUserDTO findUserByEmail( AuthenticationDTO auth, String document ) throws ApplicationException
    {
        if ( document == null )
            return null;
        authenticate( auth );
        UserDocument doc = userDocument.findEmail( document );
        return doc != null ? UserUtil.copy( doc.getUser() ) : null;
    }

    public List<ClientDTO> getClients( AuthenticationDTO auth ) throws ApplicationException
    {
        Company myCompany = getCompany( auth );
        List<Client> list = clientSession.getAllPersonClients( myCompany );
        return ClientUtil.toDTOList( list );
    }

    protected Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator coll = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        return coll.getCompany();
    }
}
