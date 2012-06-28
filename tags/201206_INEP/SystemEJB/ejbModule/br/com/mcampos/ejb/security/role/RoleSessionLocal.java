package br.com.mcampos.ejb.security.role;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.task.Task;

@Local
public interface RoleSessionLocal extends BaseSessionInterface<Role>
{
	Role getRootRole( );

	@Override
	Integer getNextId( );

	void changeParent( Role entity, Role newParent );

	List<Task> getTaks( Role entity );

	public List<Task> getRootTask( );

	public Role add( Role role, List<Task> tasks );

	public Role add( Role role, Task task );

	public Role remove( Role role, Task task );

}
