package br.com.mcampos.ejb.inep;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepTask;

@Local
public interface InepSessionLocal extends BaseCrudSessionInterface<InepTask>
{

}
