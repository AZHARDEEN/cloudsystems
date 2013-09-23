package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.inep.InepEvent;

@Remote
public interface InepPackageSession extends BaseCrudSessionInterface<InepEvent>
{
	List<InepEvent> getAll( PrincipalDTO auth );

	List<InepEvent> getAll( PrincipalDTO auth, DBPaging page );

	Integer getNextId( PrincipalDTO auth );
}
