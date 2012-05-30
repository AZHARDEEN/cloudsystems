package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface InepPackageSession extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( Authentication auth );
}
