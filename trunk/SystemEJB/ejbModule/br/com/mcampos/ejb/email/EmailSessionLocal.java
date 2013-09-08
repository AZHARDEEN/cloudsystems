package br.com.mcampos.ejb.email;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.system.EMail;

@Local
public interface EmailSessionLocal extends BaseCrudSessionInterface<EMail>
{

}
