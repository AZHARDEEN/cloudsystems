package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.CollaboratorType;

@Remote
public interface CollaboratorTypeSession extends BaseCrudSessionInterface<CollaboratorType> {

}
