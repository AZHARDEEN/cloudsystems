package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;


import br.com.mcampos.ejb.session.user.attributes.UserDocumentSessionLocal;

import java.security.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "UserSession", mappedName = "CloudSystems-EjbPrj-UserSession" )
public class UserSessionBean implements UserSessionLocal
{

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB CollaboratorSessionLocal collaborator;
    @EJB ClientSessionLocal client;
    @EJB UserDocumentSessionLocal userDocumentoSession;

    public UserSessionBean ()
    {
    }


    public Long getRecordCount ()
    {
        Long recordCount;

        recordCount = ( Long )em.createNativeQuery( "SELECT COUNT(*) FROM USERS" ).getSingleResult();
        return recordCount;
    }


    /** <code>select o from Users o</code> */
    public List<ListUserDTO> getUsersByRange ( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery( "Users.findAll" );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return copy( query.getResultList() );
    }


    protected List<ListUserDTO> copy ( List<Users> list )
    {
        List<ListUserDTO> dtos = null;

        if ( list == null )
            return dtos;
        dtos = new ArrayList<ListUserDTO>( list.size() );
        for ( Users item : list )
            dtos.add( DTOFactory.copy( item ) );
        return dtos;

    }

    public UserDTO get ( Integer id )
    {
        Users entity;

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
    public Users findByDocumentList ( List<UserDocumentDTO> list )
    {
        Users user = null, foundUser = null;
        for ( UserDocumentDTO dto : list ) {
            user = getUserByDocument( dto );
            if ( user != null ) {
                if ( foundUser != null ) {
                    if ( foundUser.getId().equals( user.getId() ) == false )
                        throw new InvalidParameterException ("Os dados fornecidos não pertencem ao um mesmo usuario");
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
     * @return Users - Entity user.
     */
    @TransactionAttribute( value = TransactionAttributeType.NOT_SUPPORTED )
    public Users getUserByDocument ( UserDocumentDTO dto )
    {
        UserDocument userDocument;

        userDocument = getUserDocumentoSession().find( dto );
        if ( userDocument == null )
            return null;
        return em.find( Users.class, userDocument.getUserId() );
    }



    public List<ListUserDTO> getBusinessList ( Integer userId )
    {
        List<ListUserDTO> list = null;
        List<Collaborator> companies;

        companies = getCollaborator().getCompanies( userId );
        for ( Collaborator item : companies ) {
            if ( list == null )
                list = new ArrayList<ListUserDTO>();
            list.add( DTOFactory.copy( ( Users )item.getCompany() ) );
        }
        return list;
    }

    public CollaboratorSessionLocal getCollaborator ()
    {
        return collaborator;
    }

    public ClientSessionLocal getClient ()
    {
        return client;
    }

    public UserDocumentSessionLocal getUserDocumentoSession ()
    {
        return userDocumentoSession;
    }
}
