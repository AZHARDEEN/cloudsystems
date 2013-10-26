package br.com.mcampos.ejb.security;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.UserStatus;

@Remote
public interface UserStatusSession extends BaseCrudSessionInterface<UserStatus>
{
}
