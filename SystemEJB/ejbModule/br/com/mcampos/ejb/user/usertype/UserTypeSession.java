package br.com.mcampos.ejb.user.usertype;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface UserTypeSession extends BaseSessionInterface<UserType>
{
}
