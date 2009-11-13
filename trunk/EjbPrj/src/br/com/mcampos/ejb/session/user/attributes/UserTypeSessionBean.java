package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;

import br.com.mcampos.ejb.entity.user.attributes.UserType;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "UserTypeSession", mappedName = "CloudSystems-EjbPrj-UserTypeSession" )
@Remote
@Local
public class UserTypeSessionBean implements UserTypeSession,
                                            UserTypeSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public UserTypeSessionBean()
    {
    }
    
    
    
    

    public void add( UserTypeDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ) );
    }

    public void update( UserTypeDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }

    public void delete( Integer id )
    {
        UserType userType = em.find(UserType.class, id );
        em.remove(userType);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<UserTypeDTO> getAll()
    {
        List<UserType> list;
        List<UserTypeDTO> dtos = null;
        
        list = ( em.createNamedQuery("UserType.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<UserTypeDTO>( list.size () );
        for ( UserType item : list )
            dtos.add( DTOFactory.copy (item ) );
        return dtos;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public UserTypeDTO get( Integer id)
    {
        UserType userType =  (UserType) em.find (UserType.class, id.toString());
        if ( userType != null ) return DTOFactory.copy ( userType );
        else return null;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( ust_id_in, 0 ) ) + 1 as idMax from user_type" );
        
        return (Integer) query.getSingleResult();
    }
}
