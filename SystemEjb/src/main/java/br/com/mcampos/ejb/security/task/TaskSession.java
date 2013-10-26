package br.com.mcampos.ejb.security.task;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;

@Remote
public interface TaskSession extends BaseCrudSessionInterface<Task>
{
	Task getRootTask( );

	public List<Role> getRoles( Integer id );

	public List<Menu> getMenus( Integer id );

	public void changeParent( PrincipalDTO auth, Task entity, Task newParent );

	public Role getRootRole( );

	public List<Menu> getTopContextMenu( ) throws ApplicationException;

	public Task add( PrincipalDTO auth, Task task, Role role );

	public Task remove( PrincipalDTO auth, Task task, Role role );

	public Task add( PrincipalDTO auth, Task task, Menu menu );

	public Task remove( PrincipalDTO auth, Task task, Menu menu );

}
