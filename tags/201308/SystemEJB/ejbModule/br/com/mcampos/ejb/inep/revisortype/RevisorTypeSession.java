package br.com.mcampos.ejb.inep.revisortype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.RevisorType;

@Remote
public interface RevisorTypeSession extends BaseSessionInterface<RevisorType>
{

}
