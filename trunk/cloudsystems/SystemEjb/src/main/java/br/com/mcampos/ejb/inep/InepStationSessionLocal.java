package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.inep.StationDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepStation;

@Local
public interface InepStationSessionLocal extends CollaboratorBaseSessionInterface<InepStation>
{
	List<StationDTO> getAll( InepEvent evt );
}
