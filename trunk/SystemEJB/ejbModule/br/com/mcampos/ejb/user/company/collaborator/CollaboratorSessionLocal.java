package br.com.mcampos.ejb.user.company.collaborator;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface CollaboratorSessionLocal extends BaseSessionInterface<Collaborator>
{
	/*
	 * 
	 * 
	 * 
	 * Collaborator find( Login login, Integer companyId );
	 * 
	 * List<SimpleDTO> getCompanies( Login c ) throws ApplicationException;
	 * 
	 * Collaborator add( Login login, Integer companyId );
	 */
	Collaborator find( PrincipalDTO login );

	Collaborator add( Login login, Integer companyId );

}
