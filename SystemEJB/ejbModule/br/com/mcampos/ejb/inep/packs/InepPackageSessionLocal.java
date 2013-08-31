package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface InepPackageSessionLocal extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( PrincipalDTO auth );

	List<InepPackage> getAvailable( );
}
