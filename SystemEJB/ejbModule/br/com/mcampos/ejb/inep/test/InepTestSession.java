package br.com.mcampos.ejb.inep.test;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface InepTestSession extends BaseSessionInterface<InepTest>
{
	boolean insert( InepTestPK key, byte[ ] object );
}
