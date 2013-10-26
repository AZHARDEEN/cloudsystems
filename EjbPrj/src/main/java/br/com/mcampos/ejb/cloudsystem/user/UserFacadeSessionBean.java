package br.com.mcampos.ejb.cloudsystem.user;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.core.BasicDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.PersonSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "UserFacadeSession", mappedName = "UserFacadeSession" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class UserFacadeSessionBean implements UserFacadeSession
{

    @EJB
    UserSessionLocal user;
    @EJB
    PersonSessionLocal person;
    @EJB
    SystemMessagesSessionLocal systemMessage;

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


    public UserDTO getUserByDocument( AuthenticationDTO auth, UserDocumentDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        Users entity;

        entity = user.getUserByDocument( dto );
        if ( user != null ) {
            if ( user instanceof Person )
                return ( PersonUtil.copy( ( Person )user ) );
            else
                return ( DTOFactory.copy( ( Company )user ) );
        }
        return null;
    }

    private void testParam( AuthenticationDTO auth, BasicDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 26 );
    }
}
