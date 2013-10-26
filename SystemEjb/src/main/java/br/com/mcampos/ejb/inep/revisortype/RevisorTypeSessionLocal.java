package br.com.mcampos.ejb.inep.revisortype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.RevisorType;

@Local
public interface RevisorTypeSessionLocal extends BaseCrudSessionInterface<RevisorType>
{

}
