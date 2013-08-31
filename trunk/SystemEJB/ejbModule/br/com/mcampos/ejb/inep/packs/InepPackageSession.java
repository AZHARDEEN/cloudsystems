package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepPackageSession extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( PrincipalDTO auth );

	List<InepPackage> getAll( PrincipalDTO auth, DBPaging page );

	Integer getNextId( PrincipalDTO auth );
}
