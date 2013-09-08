package br.com.mcampos.ejb.user.company.collaborator.property;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.security.LoginProperty;

@Remote
public interface LoginPropertySession extends BaseCrudSessionInterface<LoginProperty>
{

}
