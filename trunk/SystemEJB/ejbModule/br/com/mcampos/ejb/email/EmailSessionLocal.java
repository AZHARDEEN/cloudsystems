package br.com.mcampos.ejb.email;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.EMail;

@Local
public interface EmailSessionLocal extends BaseSessionInterface<EMail>
{

}
