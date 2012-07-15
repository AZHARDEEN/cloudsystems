package br.com.mcampos.ejb.fdigital.form;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface AnotoFormSession extends BaseSessionInterface<AnotoForm>
{
	AnotoForm getRelationships( AnotoForm f );
}
