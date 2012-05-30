package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface InepPackageSessionLocal extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( Authentication auth );
}
