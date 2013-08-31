package br.com.mcampos.ejb.security;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.UserStatus;

@Local
public interface UserStatusSessionLocal extends BaseSessionInterface<UserStatus>
{
}
