package br.com.mcampos.ejb.inep;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.jpa.inep.InepStation;

@Local
public interface InepStationSessionLocal extends CollaboratorBaseSessionInterface<InepStation>
{

}
