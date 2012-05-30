package br.com.mcampos.ejb.user.company.collaborator;

import java.util.List;

import javax.ejb.Remote;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.SimpleDTO;

@Remote
public interface CollaboratorSession extends BaseSessionInterface<Collaborator>{
	Collaborator find ( Authentication auth );
	List<SimpleDTO> getCompanies( Authentication auth ) throws ApplicationException;
}
