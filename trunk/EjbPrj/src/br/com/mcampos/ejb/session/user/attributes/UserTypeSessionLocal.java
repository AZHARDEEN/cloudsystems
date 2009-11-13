package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

import br.com.mcampos.dto.user.attributes.UserTypeDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserTypeSessionLocal
{
    void add( UserTypeDTO dto );
    void update( UserTypeDTO dto );
    void delete( Integer id );
    List<UserTypeDTO> getAll();
    UserTypeDTO get ( Integer id);
    Integer getIdMaxValue ();
}
