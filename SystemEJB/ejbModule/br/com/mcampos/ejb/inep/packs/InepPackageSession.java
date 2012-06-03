package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface InepPackageSession extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( Collaborator auth );

	List<InepPackage> getAll( Collaborator auth, DBPaging page );

	Integer getNextId( Collaborator auth );
}
