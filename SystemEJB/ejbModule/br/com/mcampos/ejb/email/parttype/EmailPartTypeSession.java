package br.com.mcampos.ejb.email.parttype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface EmailPartTypeSession extends BaseSessionInterface<EMailPartType>
{

}
