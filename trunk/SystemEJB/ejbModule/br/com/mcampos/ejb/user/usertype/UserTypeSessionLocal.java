package br.com.mcampos.ejb.user.usertype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.UserType;

@Local
public interface UserTypeSessionLocal extends BaseCrudSessionInterface<UserType>
{
}
