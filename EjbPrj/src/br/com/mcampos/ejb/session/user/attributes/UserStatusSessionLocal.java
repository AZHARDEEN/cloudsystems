package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserStatusSessionLocal
{
    void add( UserStatusDTO userStatus );
    void update( UserStatusDTO userStatus );
    void delete( Integer id );
    List<UserStatusDTO> getAll();
    UserStatusDTO get ( Integer id);
    Integer getIdMaxValue ();
}
