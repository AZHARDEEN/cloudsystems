package br.com.mcampos.ejb.email;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface EmailSession extends BaseSessionInterface<EMail>
{

}
