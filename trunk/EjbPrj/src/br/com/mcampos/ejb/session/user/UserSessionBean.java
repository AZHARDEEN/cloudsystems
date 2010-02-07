package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;


import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.UserDocumentSessionLocal;

import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "UserSession", mappedName = "CloudSystems-EjbPrj-UserSession" )
@Local
public class UserSessionBean implements UserSessionLocal
{

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    UserDocumentSessionLocal userDocumentoSession;

    @EJB
    SystemMessagesSessionLocal systemMessage;

    @EJB
    LoginSessionLocal login;

    private static final Integer systemMessageTypeId = 3;


    public UserSessionBean()
    {
    }

    /**************************************************************************************
     * PROTECTED FUNCTIONS
     *************************************************************************************/
    protected UserDocumentSessionLocal getUserDocumentoSession()
    {
        return userDocumentoSession;
    }


    /**************************************************************************************
     * PUBLIC FUNCTIONS
     *************************************************************************************/

    public Integer getRecordCount( AuthenticationDTO auth )
    {
        Long recordCount;

        getLogin().authenticate( auth );
        try {
            recordCount = ( Long )em.createNativeQuery( "SELECT COUNT(*) FROM USERS" ).getSingleResult();
            return recordCount.intValue();
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }
    }


    /** <code>select o from Users o</code> */
    public List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, int firstResult, int maxResults )
    {
        getLogin().authenticate( auth );
        Query query = em.createNamedQuery( "Users.findAll" );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        try {
            return copy( query.getResultList() );
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }


    protected List<ListUserDTO> copy( List<Users> list )
    {
        List<ListUserDTO> dtos = null;

        if ( list == null )
            return dtos;
        dtos = new ArrayList<ListUserDTO>( list.size() );
        for ( Users item : list )
            dtos.add( DTOFactory.copy( item ) );
        return dtos;

    }

    public UserDTO get( AuthenticationDTO auth, Integer id )
    {
        Users entity;

        getLogin().authenticate( auth );
        entity = em.find( Users.class, id );
        if ( entity != null ) {
            if ( entity.getUserType() != null )
                em.refresh( entity.getUserType() );
            if ( entity instanceof Person )
                return DTOFactory.copy( ( Person )entity, true );
            else
                return DTOFactory.copy( ( Company )entity );
        }
        else
            return null;
    }

    /*
     * Procura por um registro na tabela de documentos de usuarios (userdocuments).
     * O único truque desta funcao é testar se o mesmo usuario é o dono de todos os
     * documentos. Por exemplo: cpf x, identidade y e email z: x, y e z devem ser do mesmo usuário ou
     * uma exceção será lancada.
     * A interface para esta função é somente local, isto significa que somente dentro do
     * container poderá ser usada.
     *
     * @param list Lista de documentos a serem procurados, pois um usuário pode ter mais
     * de um documento.
     *
     * @exception InvalidParameterException
     * @return Users - Entity de usuario
     * @see br.com.mcampos.ejb.entity.user.Users
     *
     */

    public Users findByDocumentList( List<UserDocumentDTO> list ) throws ApplicationException
    {
        Users user = null, foundUser = null;
        for ( UserDocumentDTO dto : list ) {
            user = getUserByDocument( dto );
            if ( user != null ) {
                if ( foundUser != null ) {
                    if ( foundUser.getId().equals( user.getId() ) == false )
                        throwRuntimeException( 1 );
                }
                foundUser = user;
            }
        }
        return foundUser;
    }

    /**
     * Procura por um usuario baseado em um documento do usuário ( CPF, ID...) específico
     *
     * @param dto DocumentTypeDTO
     * @return Users - Entity userSession.
     */
    public Users getUserByDocument( UserDocumentDTO dto )
    {
        UserDocument userDocument;

        userDocument = getUserDocumentoSession().find( dto );
        if ( userDocument == null )
            return null;
        return em.find( Users.class, userDocument.getUserId() );
    }


    /**
     * Procura por um usuario baseado em um documento do usuário ( CPF, ID...) específico
     *
     * @param entity DocumentTypeDTO
     * @return Users - Entity userSession.
     */
    public Users getUserByDocument( UserDocument entity )
    {
        UserDocument userDocument;

        userDocument = getUserDocumentoSession().find( entity );
        if ( userDocument == null )
            return null;
        return em.find( Users.class, userDocument.getUserId() );
    }


    /**
     * Lança uma exceção que NÃO causa rollback da transação, ou seja,
     * NÃO É derivada de runtime-excpetion
     *
     * @param id - id da mensagem a ser exibida. Cadastrada na tabela SystemMessages
     * @throws ApplicationException
     */
    protected void throwException( int id ) throws ApplicationException
    {
        getSystemMessage().throwException( systemMessageTypeId, id );
    }

    /**
     * Lança uma exceção que PROVOCA rollback da transação, ou seja,
     * E derivada de runtime-excpetion
     *
     * @param id - id da mensagem a ser exibida. Cadastrada na tabela SystemMessages
     * @throws ApplicationException
     */
    protected void throwRuntimeException( int id ) throws ApplicationException
    {
        getSystemMessage().throwRuntimeException( systemMessageTypeId, id );
    }

    protected SystemMessagesSessionLocal getSystemMessage()
    {
        return systemMessage;
    }

    protected LoginSessionLocal getLogin()
    {
        return login;
    }
}
