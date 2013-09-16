package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Local
public interface InepPackageSessionLocal extends BaseCrudSessionInterface<InepEvent>
{
	List<InepEvent> getAll( PrincipalDTO auth );

	List<InepEvent> getAvailable( );
}
