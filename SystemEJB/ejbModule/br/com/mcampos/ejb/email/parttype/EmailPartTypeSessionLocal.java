package br.com.mcampos.ejb.email.parttype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.system.EMailPartType;

@Local
public interface EmailPartTypeSessionLocal extends BaseCrudSessionInterface<EMailPartType>
{

}
