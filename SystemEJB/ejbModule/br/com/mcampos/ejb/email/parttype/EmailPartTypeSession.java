package br.com.mcampos.ejb.email.parttype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.system.EMailPartType;

@Remote
public interface EmailPartTypeSession extends BaseCrudSessionInterface<EMailPartType>
{

}
