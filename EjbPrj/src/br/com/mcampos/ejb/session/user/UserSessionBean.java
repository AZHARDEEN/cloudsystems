package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Client;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "UserSession", mappedName = "CloudSystems-EjbPrj-UserSession" )
@Remote
@Local
public class UserSessionBean implements UserSession, UserSessionLocal
{
 
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;
    
    @EJB CollaboratorSessionLocal collaborator;
    @EJB ClientSessionLocal client;

    public UserSessionBean()
    {
    }
    
    
    public Long getRecordCount ()
    {
        Long recordCount;
        
        recordCount = (Long) em.createNativeQuery( "SELECT COUNT(*) FROM USERS" ).getSingleResult();
        return recordCount;
    }

    public Long getClientRecordCount ( Integer owner )
    {
        return client.getCount( owner );
    }


    /** <code>select o from Users o</code> */
    public List<ListUserDTO> getUsersByRange( int firstResult,
                                               int maxResults )
    {
        Query query = em.createNamedQuery("Users.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return copy ( query.getResultList() );
    }
    
    
    protected List<ListUserDTO> copy ( List<Users> list )
    {
        List<ListUserDTO> dtos = null;
        
        if ( list == null )
            return dtos;
        dtos = new ArrayList<ListUserDTO>( list.size () );
        for ( Users item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
        
    }
    
    public Boolean documentExists ( String document )
    {
        Query query;
        
        try {
            query = em.createNamedQuery( "UserDocument.findByDocument" );
            query.setParameter( "document", document );
            query.getSingleResult();
            return true;
        }
        catch ( NoResultException e ) {
            return false;
        }
    }
    
    public UserDTO get ( Integer id )
    {
        Users entity;

        entity = em.find( Users.class, id );
        if ( entity != null ) {
            if ( entity.getUserType() != null )
                em.refresh( entity.getUserType() );
            if ( entity instanceof Person )
                return DTOFactory.copy( (Person) entity, true );
            else
                return DTOFactory.copy( (Company) entity );
        }
        else
            return null;
    }
    
    @TransactionAttribute( value = TransactionAttributeType.NOT_SUPPORTED )
    public UserDTO getUserByDocument( String document, Integer documentId )
    {
        Query query;
        UserDocument userDocument;
        Users user;
        UserDTO userDTO = null;

        try {
            query = em.createNamedQuery( "UserDocument.findDocument" );
            query.setParameter( "document", document );
            query.setParameter( "docType", documentId );
            userDocument = ( UserDocument )query.getSingleResult();
            if ( userDocument == null )
                return null;
            user = ( Users )em.find( Users.class, userDocument.getUserId() ); 
            if ( user != null ) {
                if ( user instanceof Person )
                    userDTO = DTOFactory.copy( (Person) user, true );
                else
                    userDTO = DTOFactory.copy ( (Company)user );
            }
            return userDTO;
        }
        catch ( NoResultException e ) {
            return null;
        }
    }

    public List<ListUserDTO> getBusinessList (Integer userId)
    {
        List<ListUserDTO> list = null;
        List<Collaborator> companies;
        
        companies = collaborator.getCompanies( userId );
        for ( Collaborator item: companies ) 
        {
            if ( list == null )
                list = new ArrayList<ListUserDTO> ();
            list.add( DTOFactory.copy ( (Users) item.getCompany() ) );
        }
        return list;
    }
}
