package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.ejb.entity.user.attributes.UserType;

import java.util.List;

import javax.ejb.Remote;

import javax.persistence.Query;

@Remote
public interface UserTypeSession
{
    void add( UserTypeDTO dto );
    void update( UserTypeDTO dto );
    void delete( Integer id );
    List<UserTypeDTO> getAll();
    UserTypeDTO get ( Integer id);
    Integer getIdMaxValue ();
}
