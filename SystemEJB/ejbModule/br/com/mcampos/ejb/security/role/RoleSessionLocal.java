package br.com.mcampos.ejb.security.role;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Local
public interface RoleSessionLocal extends BaseCrudSessionInterface<Role>
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
