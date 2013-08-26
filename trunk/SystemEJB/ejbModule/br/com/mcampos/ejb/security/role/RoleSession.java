package br.com.mcampos.ejb.security.role;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.task.Task;

@Remote
public interface RoleSession extends BaseSessionInterface<Role>
{
	Role getRootRole( );

	Task getRootTask( );

	@Override
	Integer getNextId( );

	void changeParent( Role entity, Role newParent );

	List<Task> getTaks( Role entity );

	public Role add( Role role, List<Task> tasks );

	public Role add( Role role, Task task );

	public Role remove( Role role, Task task );

	public List<Menu> getMenus( Role role ) throws ApplicationException;
}
