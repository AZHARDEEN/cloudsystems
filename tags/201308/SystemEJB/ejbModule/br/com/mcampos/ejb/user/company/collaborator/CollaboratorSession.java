package br.com.mcampos.ejb.user.company.collaborator;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.AuthorizedPageOptions;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.menu.Menu;

@Remote
public interface CollaboratorSession extends BaseSessionInterface<Collaborator>, UserPropertyInterface
{
	Collaborator find( Login login, Integer companyId );

	List<SimpleDTO> getCompanies( Login c ) throws ApplicationException;

	AuthorizedPageOptions verifyAccess( Collaborator c, String mnuUrl );

	public List<Menu> getMenus( Collaborator collaborator ) throws ApplicationException;
}
