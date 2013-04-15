package br.com.mcampos.ejb.user.usertype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface UserTypeSessionLocal extends BaseSessionInterface<UserType>
{
}
