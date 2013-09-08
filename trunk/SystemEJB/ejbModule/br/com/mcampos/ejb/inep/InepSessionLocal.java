package br.com.mcampos.ejb.inep;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepTask;

@Local
public interface InepSessionLocal extends BaseCrudSessionInterface<InepTask>
{

}
