package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;


import com.bea.common.engine.InvalidParameterException;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "UserStatusSession", mappedName = "CloudSystems-EjbPrj-UserStatusSession" )
@Remote
@Local
public class UserStatusSessionBean implements UserStatusSession,
                                              UserStatusSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public UserStatusSessionBean()
    {
    }

    public void add( UserStatusDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException ( "UserStatusDTO não pode ser nula" );
        em.persist( DTOFactory.copy( dto ) );
    }

    public void update( UserStatusDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }
    


    public void delete( Integer id )
    {
        UserStatus userStatus = em.find(UserStatus.class, id );
        em.remove(userStatus);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<UserStatusDTO> getAll()
    {
        List<UserStatus> list;
        List<UserStatusDTO> dtos = null;
        
        list = ( em.createNamedQuery("UserStatus.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<UserStatusDTO>( list.size () );
        for ( UserStatus item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public UserStatusDTO get( Integer id )
    {
        try {
            return DTOFactory.copy( (UserStatus) em.find ( UserStatus.class, id ) );
        }
        catch ( NoResultException e )
        {
            throw new EJBException ( "Nao existe Status com id: " + id.toString() );
        }
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( uts_id_in, 0 ) ) + 1 as idMax from user_status" );
        
        return (Integer) query.getSingleResult();
    }
}
