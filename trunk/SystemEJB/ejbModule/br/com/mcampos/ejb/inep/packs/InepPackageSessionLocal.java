package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface InepPackageSessionLocal extends BaseSessionInterface<InepPackage>
{
	List<InepPackage> getAll( Collaborator auth );
}