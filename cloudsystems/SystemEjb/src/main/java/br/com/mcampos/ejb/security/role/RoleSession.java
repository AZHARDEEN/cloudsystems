package br.com.mcampos.ejb.security.role;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;

@Remote
public interface RoleSession extends BaseCrudSessionInterface<Role>
{
	Role getRootRole( );

	Task getRootTask( );

	@Override
	Integer getNextId( );

	void changeParent( PrincipalDTO auth, Role entity, Role newParent );

	List<Task> getTaks( Role entity );

	public Role add( PrincipalDTO auth, Role role, List<Task> tasks );

	public Role add( PrincipalDTO auth, Role role, Task task );

	public Role remove( PrincipalDTO auth, Role role, Task task );

	public List<Menu> getMenus( Role role ) throws ApplicationException;
}
