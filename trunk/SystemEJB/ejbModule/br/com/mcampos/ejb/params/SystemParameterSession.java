package br.com.mcampos.ejb.params;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.SystemParameters;

@Remote
public interface SystemParameterSession extends BaseSessionInterface<SystemParameters>
{
}
