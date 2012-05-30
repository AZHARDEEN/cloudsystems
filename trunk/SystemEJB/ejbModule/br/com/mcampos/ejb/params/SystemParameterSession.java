package br.com.mcampos.ejb.params;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface SystemParameterSession extends BaseSessionInterface<SystemParameters>
{
}
