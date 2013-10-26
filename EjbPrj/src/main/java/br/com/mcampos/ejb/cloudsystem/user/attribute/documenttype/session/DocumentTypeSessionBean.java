package br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.entity.DocumentType;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "DocumentTypeSession", mappedName = "DocumentTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class DocumentTypeSessionBean extends Crud<Integer, DocumentType> implements DocumentTypeSessionLocal
{
    public DocumentTypeSessionBean()
    {
    }


    public void delete( Integer key ) throws ApplicationException
    {
        delete( DocumentType.class, key );
    }

    public DocumentType get( Integer key ) throws ApplicationException
    {
        return get( DocumentType.class, key );
    }

    public List<DocumentType> getAll() throws ApplicationException
    {
        return getAll( DocumentType.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return null;
    }

    public DocumentType add( DocumentType entity ) throws ApplicationException
    {
        return add( entity );
    }

    public DocumentType update( DocumentType entity ) throws ApplicationException
    {
        return update( entity );
    }
}
