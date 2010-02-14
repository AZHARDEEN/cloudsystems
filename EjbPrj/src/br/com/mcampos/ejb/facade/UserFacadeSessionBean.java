package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.core.BasicDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;
import br.com.mcampos.ejb.session.system.SystemMessage.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.user.CollaboratorSessionLocal;
import br.com.mcampos.ejb.session.user.CompanySessionLocal;
import br.com.mcampos.ejb.session.user.PersonSessionLocal;

import br.com.mcampos.ejb.session.user.UserSessionLocal;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;


@Stateless( name = "UserFacadeSession", mappedName = "CloudSystems-EjbPrj-UserFacadeSession" )
public class UserFacadeSessionBean implements UserFacadeSession
{

    @EJB
    UserSessionLocal user;
    @EJB
    PersonSessionLocal person;
    @EJB
    CompanySessionLocal company;
    @EJB
    SystemMessagesSessionLocal systemMessage;
    @EJB
    CollaboratorSessionLocal collaborator;

    private static final Integer systemMessageTypeId = 3;


    public UserFacadeSessionBean()
    {
    }

    public Integer getUserRecordCount( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return user.getRecordCount( auth );
    }

    public Integer getClientRecordCount( AuthenticationDTO auth, Integer owner ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return null;
    }

    public List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, Integer firstResult,
                                              Integer maxResults ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return user.getUsersByRange( auth, firstResult, maxResults );
    }

    public PersonDTO getPerson( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return person.get( userId );
    }

    public UserDTO getUser( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return user.get( auth, userId );
    }


    public void add( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException
    {
        testParam( auth, dto );
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

    public void add( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException
    {
        testParam( auth, dto );
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

    public void addBusinessEntity( AuthenticationDTO auth, UserDTO dto, LoginDTO loginDTO ) throws ApplicationException
    {
        testParam( auth, dto );
        testParam( loginDTO );
        try {
            Users entity;

            if ( dto.getId() != null && dto.getId() > 0 ) {
                /*This entity already exists*/
                /*If this entity alread has a manager (administrator) it cannot be updated*/
                if ( dto instanceof CompanyDTO ) {
                    if ( company.hasManagers( dto.getId() ) ) {
                        if ( company.isManager( dto.getId(), loginDTO.getUserId() ) == false )
                            systemMessage.throwException( systemMessageTypeId, 27 );
                    }
                    entity = company.updateBusinessEntity( ( CompanyDTO )dto, loginDTO );
                }
                else {
                    entity = person.update( ( PersonDTO )dto );
                }
            }
            else {
                /*this is a new entity!*/
                entity = ( dto instanceof CompanyDTO ) ? company.addBusinessEntity( ( CompanyDTO )dto, loginDTO ) :
                         person.add( ( PersonDTO )dto );
            }

            if ( entity instanceof Company ) {
                company.addCollaborator( entity.getId(), loginDTO.getUserId(), CollaboratorType.typeManager );
            }
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao incluir cadastro de pessoa", e );
        }
    }

    public UserDTO getUserByDocument( AuthenticationDTO auth, UserDocumentDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
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

    protected void testParam( BasicDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
    }

    protected void testParam( AuthenticationDTO auth, BasicDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
    }


    public UserDTO getMyCompany( AuthenticationDTO auth, Integer businessId ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return collaborator.getBusinessEntity( auth, businessId );
    }

    public Integer getMyCompanyCount( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return collaborator.getBusinessEntityCount( auth );
    }


    public List<ListUserDTO> getMyCompaniesByRange( AuthenticationDTO auth, Integer startNumber,
                                                    Integer pageSize ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return collaborator.getBusinessEntityByRange( auth, startNumber, pageSize );
    }


    public List<MenuDTO> getMenuList( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        return collaborator.getMenuList( auth );
    }
}
