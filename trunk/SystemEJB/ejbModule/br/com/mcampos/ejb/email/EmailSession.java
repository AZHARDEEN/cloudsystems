package br.com.mcampos.ejb.email;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.EMail;

@Remote
public interface EmailSession extends BaseSessionInterface<EMail>
{

}
