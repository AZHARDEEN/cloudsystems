package br.com.mcampos.ejb.user.company.collaborator;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Local
public interface CollaboratorSessionLocal extends BaseCrudSessionInterface<Collaborator>
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
