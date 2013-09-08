package br.com.mcampos.ejb.system;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.system.LogProgramException;

@Local
public interface LogProgramExceptionSessionLocal extends BaseCrudSessionInterface<LogProgramException>
{

}
