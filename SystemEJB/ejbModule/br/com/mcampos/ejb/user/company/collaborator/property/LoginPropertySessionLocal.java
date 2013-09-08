package br.com.mcampos.ejb.user.company.collaborator.property;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.user.company.collaborator.UserPropertyInterface;
import br.com.mcampos.entity.security.LoginProperty;

@Local
public interface LoginPropertySessionLocal extends BaseCrudSessionInterface<LoginProperty>, UserPropertyInterface
{
}
