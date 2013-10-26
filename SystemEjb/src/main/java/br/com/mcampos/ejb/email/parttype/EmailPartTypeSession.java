package br.com.mcampos.ejb.email.parttype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.EMailPartType;

@Remote
public interface EmailPartTypeSession extends BaseCrudSessionInterface<EMailPartType>
{

}
