package br.com.mcampos.ejb.security.task;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.role.Role;

@Remote
public interface TaskSession extends BaseSessionInterface<Task>
{
	List<Task> getRootTasks( );

	public List<Role> getRoles( Integer id );

	public List<Menu> getMenus( Integer id );

	public void changeParent( Task entity, Task newParent );

	public Role getRootRole( );

	public List<Menu> getTopContextMenu( ) throws ApplicationException;

	public Task add( Task task, Role role );

	public Task remove( Task task, Role role );

	public Task add( Task task, Menu menu );

	public Task remove( Task task, Menu menu );

}
