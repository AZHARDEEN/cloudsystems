package br.com.mcampos.ejb.session.address;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.address.AddressType;


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

@Stateless( name = "AddressTypeSession", mappedName = "CloudSystems-EjbPrj-AddressTypeSession" )
@Remote
@Local
public class AddressTypeSessionBean implements AddressTypeSession,
                                               AddressTypeSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public AddressTypeSessionBean() {
    }

    private EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager( EntityManager em ) 
    {
        this.em = em;
    }


    public void add( AddressTypeDTO dto ) 
    {
        getEntityManager().persist( DTOFactory.copy ( dto ) );
    }

    public void update( AddressTypeDTO dto ) 
    {
        getEntityManager().merge(DTOFactory.copy ( dto ));
    }

    public void delete( Integer id ) 
    {
        final EntityManager em = getEntityManager();
        AddressType addressType = em.find( AddressType.class, id );
        em.remove(addressType);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<AddressTypeDTO> getAll() {
        List<AddressType> list;
        List<AddressTypeDTO> dtos = null;
        
        list = ( em.createNamedQuery("AddressType.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<AddressTypeDTO>( list.size () );
        for ( AddressType item : list )
            dtos.add( DTOFactory.copy ( item)  );
        return dtos;
    }


    /** <code>select o from AddressType o where o.id = :id </code> */
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public AddressTypeDTO get( Integer id )
    {
        AddressType record = ( AddressType ) getEntityManager().createNamedQuery("AddressType.find").setParameter("id", id).getSingleResult();
        if ( record != null ) return DTOFactory.copy ( record );
        else return null;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = getEntityManager().createNativeQuery( "Select max( coalesce ( adt_id_in, 0 ) ) + 1 as adt_id_in from address_type" );
        
        return (Integer) query.getSingleResult();
    }
    
}
