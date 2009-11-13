package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.DocumentType;


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

@Stateless( name = "DocumentTypeSession", mappedName = "CloudSystems-EjbPrj-DocumentTypeSession" )
@Remote
@Local
public class DocumentTypeSessionBean implements DocumentTypeSession,
                                                DocumentTypeSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public DocumentTypeSessionBean()
    {
    }
    

    public void add( DocumentTypeDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ));
    }

    public void update( DocumentTypeDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }

    public void delete( Integer id )
    {
        
        DocumentType documentType = em.find(DocumentType.class, id );
        if ( documentType != null )
            em.remove(documentType);
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<DocumentTypeDTO> getAll()
    {
        List<DocumentType> list;
        List<DocumentTypeDTO> dtos = null;
        
        list = ( em.createNamedQuery("DocumentType.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<DocumentTypeDTO>( list.size () );
        for ( DocumentType item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public DocumentTypeDTO get( Integer id)
    {
        DocumentType record;
        
        record = em.find ( DocumentType.class, id );
        if ( record != null )
            return DTOFactory.copy ( record );
        else 
            return null;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( doc_id_in, 0 ) ) + 1 as idMax from document_type" );
        
        return (Integer) query.getSingleResult();
    }
}
