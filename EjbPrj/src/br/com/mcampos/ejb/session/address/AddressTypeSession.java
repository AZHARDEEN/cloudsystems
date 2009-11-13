package br.com.mcampos.ejb.session.address;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.entity.address.AddressType;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AddressTypeSession
{
    void add( AddressTypeDTO dto );
    void update( AddressTypeDTO dto );
    void delete( Integer id );
    List<AddressTypeDTO> getAll();
    AddressTypeDTO get ( Integer id);
    Integer getIdMaxValue ();
}
