package br.com.mcampos.ejb.user.company.collaborator;

import java.util.List;

import javax.ejb.Local;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.security.Login;

@Local
public interface CollaboratorSessionLocal extends BaseSessionInterface<Collaborator>
{
	Collaborator find( Login login, Integer companyId );

	List<SimpleDTO> getCompanies( Login c ) throws ApplicationException;
}
