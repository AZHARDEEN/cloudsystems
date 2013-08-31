package br.com.mcampos.ejb.security.menu;

import java.util.List;

import javax.ejb.Local;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.Menu;
import br.com.mcampos.entity.security.Role;
import br.com.mcampos.entity.security.Task;
import br.com.mcampos.entity.user.Collaborator;

@Local
public interface MenuFacadeLocal extends BaseSessionInterface<Menu>
{
	public List<Menu> getMenus( Collaborator auth ) throws ApplicationException;

	public List<Menu> getTopContextMenu( ) throws ApplicationException;

	public List<Task> getTaks( Menu entity );

	public void changeParent( Menu entity, Menu newParent );

	public Menu add( Menu item, List<Task> tasks );

	public Menu add( Menu item, Task task );

	public Menu remove( Menu item, Task task );

	/*
	 * this is local only and only
	 */
	public void addRoleToMenu( Role role, List<Menu> availableMenus );

	public Menu get( String Url );
}
