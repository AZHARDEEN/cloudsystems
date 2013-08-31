package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.CollaboratorType;

@Local
public interface CollaboratorTypeSessionBeanLocal extends BaseSessionInterface<CollaboratorType> {

}
