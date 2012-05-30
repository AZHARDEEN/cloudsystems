package br.com.mcampos.ejb.inep.test;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface InepTestSessionLocal extends BaseSessionInterface<InepTest>
{

}
