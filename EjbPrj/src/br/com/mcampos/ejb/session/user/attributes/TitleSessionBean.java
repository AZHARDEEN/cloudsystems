package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.Gender;
import br.com.mcampos.ejb.entity.user.attributes.Title;

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

@Stateless( name = "TitleSession", mappedName = "CloudSystems-EjbPrj-TitleSession" )
@Remote
@Local
public class TitleSessionBean implements TitleSession, TitleSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public TitleSessionBean()
    {
    }
    

    public void add( TitleDTO title )
    {
        em.persist( DTOFactory.copy ( title ) );
    }

    public void update( TitleDTO title )
    {
        em.merge( DTOFactory.copy ( title ) );
    }

    public void delete( Integer id )
    {
        Title title = em.find(Title.class, id );
        em.remove(title);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<TitleDTO> getAll()
    {
        List<Title> list;
        List<TitleDTO> dtos = null;
        
        list = ( em.createNamedQuery("Title.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<TitleDTO>( list.size () );
        for ( Title item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public TitleDTO get( Integer id )
    {
        return DTOFactory.copy ( (Title) em.find ( Title.class, id ) );
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( ttl_id_in, 0 ) ) + 1 as idMax from title" );
        
        return (Integer) query.getSingleResult();
    }
}
