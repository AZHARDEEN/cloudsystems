package br.com.mcampos.ejb.inep;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepTask;

@Local
public interface InepSessionLocal extends BaseSessionInterface<InepTask>
{

}
