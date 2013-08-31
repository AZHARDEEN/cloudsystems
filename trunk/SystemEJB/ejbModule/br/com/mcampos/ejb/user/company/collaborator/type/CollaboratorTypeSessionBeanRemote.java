package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.CollaboratorType;

@Remote
public interface CollaboratorTypeSessionBeanRemote extends BaseSessionInterface<CollaboratorType> {

}
