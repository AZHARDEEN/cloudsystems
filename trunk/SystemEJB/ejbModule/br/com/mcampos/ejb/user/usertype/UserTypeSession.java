package br.com.mcampos.ejb.user.usertype;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.UserType;

@Remote
public interface UserTypeSession extends BaseSessionInterface<UserType>
{
}
