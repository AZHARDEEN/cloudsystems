package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;

@Local
public interface InepPackageSessionLocal extends BaseCrudSessionInterface<InepEvent>
{
	@Override
	List<InepEvent> getAll( PrincipalDTO auth );

	List<InepEvent> getAvailable( PrincipalDTO c );

	List<InepEvent> getAvailable( );

}
