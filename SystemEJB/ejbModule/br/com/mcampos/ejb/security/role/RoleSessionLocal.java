package br.com.mcampos.ejb.security.role;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.Role;
import br.com.mcampos.entity.security.Task;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface RoleSessionLocal extends BaseSessionInterface<Role>
{
	Role getRootRole( );

	@Override
	Integer getNextId( );

	void changeParent( PrincipalDTO auth, Role entity, Role newParent );

	List<Task> getTaks( Role entity );

	public Role add( PrincipalDTO auth, Role role, List<Task> tasks );

	public Role add( PrincipalDTO auth, Role role, Task task );

	public Role remove( PrincipalDTO auth, Role role, Task task );

}
