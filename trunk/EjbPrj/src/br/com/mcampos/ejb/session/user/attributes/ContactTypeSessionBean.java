package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.ContactType;

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

@Stateless( name = "ContactTypeSession", mappedName = "CloudSystems-EjbPrj-ContactTypeSession" )
public class ContactTypeSessionBean implements ContactTypeSession,
                                               ContactTypeSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;


    private EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager( EntityManager em ) 
    {
        this.em = em;
    }

    public ContactTypeSessionBean()
    {
        super ();
    }
    

    public void add( ContactTypeDTO contactType )
    {
        getEntityManager().persist( DTOFactory.copy ( contactType ));
    }

    public void update( ContactTypeDTO contactType )
    {
        getEntityManager().merge( DTOFactory.copy ( contactType ) );
    }

    public void delete( Integer id )
    {
        ContactType contactType;
        
        contactType = getEntityManager().find( ContactType.class, id );
        if ( contactType != null )
            em.remove(contactType);
    }

    /**<id>select o from ContactType o</id>
	 */
     @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<ContactTypeDTO> getAll()
    {
        List<ContactType> list;
        List<ContactTypeDTO> dtos = null;
        
        list = getEntityManager().createNamedQuery("ContactType.findAll").getResultList();
        if ( list == null )
            return dtos;
        dtos = new ArrayList<ContactTypeDTO>( list.size () );
        for ( ContactType item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public ContactTypeDTO get( Integer id )
    {
        ContactType contactType;
        
        contactType = getEntityManager().find( ContactType.class, id );
        if ( contactType != null )
            return DTOFactory.copy ( contactType );
        else
            return null;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = getEntityManager().createNativeQuery( "Select max( coalesce ( cct_id_in, 0 ) ) + 1 as maxId from contact_type" );
        
        return (Integer) query.getSingleResult();
    }
}
