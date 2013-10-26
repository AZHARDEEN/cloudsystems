package br.com.mcampos.ejb.email;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.EMail;

@Remote
public interface EmailSession extends BaseCrudSessionInterface<EMail>
{

}
