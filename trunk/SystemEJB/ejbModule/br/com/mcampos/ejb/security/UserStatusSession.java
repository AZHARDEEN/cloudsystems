package br.com.mcampos.ejb.security;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.UserStatus;

@Remote
public interface UserStatusSession extends BaseSessionInterface<UserStatus>
{
}
