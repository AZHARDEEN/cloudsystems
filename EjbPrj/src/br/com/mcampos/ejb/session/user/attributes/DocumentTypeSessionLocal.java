package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.entity.user.attributes.DocumentType;

import java.util.List;

import javax.ejb.Local;

@Local
public interface DocumentTypeSessionLocal 
{
    void add( DocumentTypeDTO documentType );
    void update( DocumentTypeDTO documentType );
    void delete( Integer id );
    List<DocumentTypeDTO> getAll();
    DocumentTypeDTO get( Integer id);
    Integer getIdMaxValue ();
}
