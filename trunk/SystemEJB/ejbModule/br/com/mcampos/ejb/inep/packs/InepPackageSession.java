package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;

@Remote
public interface InepPackageSession extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( Authentication auth );

	List<InepPackage> getAll( Authentication auth, DBPaging page );
}
