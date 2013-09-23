package br.com.mcampos.ejb.user.company.collaborator;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.AuthorizedPageOptions;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.user.Collaborator;

@Remote
public interface CollaboratorSession extends BaseCrudSessionInterface<Collaborator>, UserPropertyInterface
{
	Collaborator find( PrincipalDTO login );

	List<SimpleDTO> getCompanies( PrincipalDTO c ) throws ApplicationException;

	AuthorizedPageOptions verifyAccess( PrincipalDTO c, String mnuUrl );

	public List<Menu> getMenus( PrincipalDTO collaborator ) throws ApplicationException;
}
