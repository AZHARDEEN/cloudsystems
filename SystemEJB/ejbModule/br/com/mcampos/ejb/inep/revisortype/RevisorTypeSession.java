package br.com.mcampos.ejb.inep.revisortype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.RevisorType;

@Remote
public interface RevisorTypeSession extends BaseCrudSessionInterface<RevisorType>
{

}
