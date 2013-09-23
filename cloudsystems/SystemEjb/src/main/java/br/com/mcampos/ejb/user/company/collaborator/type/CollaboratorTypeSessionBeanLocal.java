package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.CollaboratorType;

@Local
public interface CollaboratorTypeSessionBeanLocal extends BaseCrudSessionInterface<CollaboratorType> {

}
