package br.com.mcampos.ejb.inep;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepTask;

@Remote
public interface InepSession extends BaseSessionInterface<InepTask>
{

}
