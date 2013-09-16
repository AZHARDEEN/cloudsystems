package br.com.mcampos.ejb.user.usertype;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.UserType;

@Remote
public interface UserTypeSession extends BaseCrudSessionInterface<UserType>
{
}
