package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.core.BasicDTO;
import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.user.CollaboratorSessionLocal;
import br.com.mcampos.ejb.session.user.CompanySessionLocal;
import br.com.mcampos.ejb.session.user.LoginSessionLocal;
import br.com.mcampos.ejb.session.user.PersonSessionLocal;

import br.com.mcampos.ejb.session.user.UserSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless( name = "UserFacadeSession", mappedName = "CloudSystems-EjbPrj-UserFacadeSession" )
@Remote
public class UserFacadeSessionBean implements UserFacadeSession
{

    @EJB
    UserSessionLocal user;
    @EJB
    LoginSessionLocal login;
    @EJB
    PersonSessionLocal person;
    @EJB
    CompanySessionLocal company;
    @EJB
    SystemMessagesSessionLocal systemMessage;
    @EJB
    CollaboratorSessionLocal collaborator;

    public UserFacadeSessionBean()
    {
    }

    public Long getUserRecordCount()
    {
        return user.getRecordCount();
    }

    public Long getClientRecordCount( Integer owner )
    {
        return null;
    }

    public List<ListUserDTO> getUsersByRange( Integer firstResult, Integer maxResults )
    {
        return user.getUsersByRange( firstResult, maxResults );
    }

    public List<ListLoginDTO> getLoginList()
    {
        return null;
    }

    public Long getLoginRecordCount()
    {
        return login.getRecordCount();
    }

    public List<ListLoginDTO> getLoginByRange( Integer firstResult, Integer maxResults )
    {
        return null;
    }

    public PersonDTO getPerson( Integer userId )
    {
        return person.get( userId );
    }

    public UserDTO getUser( Integer userId )
    {
        return user.get( userId );
    }


    public void deleteLogin( Integer[] logins )
    {
        if ( logins == null || logins.length == 0 )
            return;
        login.delete( logins );
    }


    public void add( PersonDTO dto )
    {
        testParam( dto );
        try {
            if ( dto.getId() != null && dto.getId() > 0 )
                person.update( dto );
            else
                person.add( dto );
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao incluir cadastro de pessoa", e );
        }
    }

    public void add( CompanyDTO dto )
    {
        testParam( dto );
        try {
            if ( dto.getId() != null && dto.getId() > 0 )
                company.update( dto );
            else
                company.add( dto );
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao incluir cadastro de pessoa", e );
        }
    }

    public void addBusinessEntity( UserDTO dto, LoginDTO loginDTO )
    {
        testParam( dto );
        testParam( loginDTO );
        try {
            Users entity;

            if ( dto.getId() != null && dto.getId() > 0 ) {
                /*This entity already exists*/
                /*If this entity alread has a manager (administrator) it cannot be updated*/
                if ( dto instanceof CompanyDTO ) {
                    if ( company.hasManagers( dto.getId() ) ) {
                        if ( company.isManager( dto.getId(), loginDTO.getUserId() ) == false )
                            systemMessage.throwMessage( 27 );
                    }
                    entity = company.updateBusinessEntity ( ( CompanyDTO )dto, loginDTO );
                }
                else {
                    entity = person.update( ( PersonDTO )dto );
                }
            }
            else {
                /*this is a new entity!*/
                entity = ( dto instanceof CompanyDTO ) ? company.addBusinessEntity( ( CompanyDTO )dto, loginDTO ) : person.add( ( PersonDTO )dto );
            }
            
            if ( entity instanceof Company ) 
            {
                company.addCollaborator( entity.getId(), loginDTO.getUserId(), CollaboratorType.typeManager );
            }
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao incluir cadastro de pessoa", e );
        }
    }

    public LoginDTO loginUser( LoginCredentialDTO dto )
    {
        LoginDTO loginDTO;

        loginDTO = login.loginUser( dto );
        return loginDTO;
    }

    public void logoutUser( LoginDTO dto )
    {
        login.logoutUser( dto );
    }

    public void updateLoginStatus( Integer id, Integer newStatus )
    {
        login.updateLoginStatus( id, newStatus );
    }

    public UserDTO getUserByDocument( UserDocumentDTO dto )
    {
        Users entity;
        
        entity = user.getUserByDocument( dto );
        if ( user != null ) {
            if ( user instanceof Person )
                return ( DTOFactory.copy( ( Person )user, true ) );
            else
                return ( DTOFactory.copy( ( Company )user ) );
        }
        return null;
    }

    protected void testParam( BasicDTO dto )
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
    }
    
    public List<ListUserDTO> getBusinessList ( Integer userId )
    {
        return user.getBusinessList (userId );
    }
    
    public Long getBusinessentityCount ( Integer userId )
    {
        return collaborator.getBusinessEntityCount( userId );
    }

    public UserDTO getBusinessEntity ( Integer userId, Integer currentUserId )
    {
        return collaborator.getBusinessEntity( userId, currentUserId );
    }

    public List<ListUserDTO> getBusinessEntityByRange ( Integer curentUserId, Integer itemStartNumber, Integer pageSize )
    {
        return collaborator.getBusinessEntityByRange( curentUserId, itemStartNumber, pageSize );
    }
}
