package br.com.mcampos.ejb.security;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface UserStatusSession extends BaseSessionInterface<UserStatus>
{
}
