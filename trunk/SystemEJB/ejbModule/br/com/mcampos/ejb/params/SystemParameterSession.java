package br.com.mcampos.ejb.params;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.system.SystemParameters;

@Remote
public interface SystemParameterSession extends BaseCrudSessionInterface<SystemParameters>
{
}
