package br.com.mcampos.ejb.email;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface EmailSessionLocal extends BaseSessionInterface<EMail>
{

}
