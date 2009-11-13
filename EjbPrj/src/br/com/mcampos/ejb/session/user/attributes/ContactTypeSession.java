package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.entity.user.attributes.ContactType;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ContactTypeSession
{
    void add( ContactTypeDTO contactType );
    void update( ContactTypeDTO contactType );
    void delete( Integer id );
    List<ContactTypeDTO> getAll();
    ContactTypeDTO get( Integer id );
    Integer getIdMaxValue ();
}
