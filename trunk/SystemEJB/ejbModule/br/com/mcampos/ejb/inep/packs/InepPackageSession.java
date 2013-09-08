package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepPackageSession extends BaseCrudSessionInterface<InepEvent>
{
	List<InepEvent> getAll( PrincipalDTO auth );

	List<InepEvent> getAll( PrincipalDTO auth, DBPaging page );

	Integer getNextId( PrincipalDTO auth );
}
