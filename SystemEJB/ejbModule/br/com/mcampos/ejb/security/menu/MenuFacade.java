package br.com.mcampos.ejb.security.menu;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.task.Task;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface MenuFacade extends BaseSessionInterface<Menu>
{
	public List<Menu> getMenus( Collaborator auth ) throws ApplicationException;

	public List<Menu> getTopContextMenu( ) throws ApplicationException;

	public List<Task> getTaks( Menu entity );

	public void changeParent( Menu entity, Menu newParent );

	public Menu add( Menu item, List<Task> tasks );

	public Menu add( Menu item, Task task );

	public Menu remove( Menu item, Task task );

	Task getRootTask( );

}
