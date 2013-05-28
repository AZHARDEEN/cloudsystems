package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface InepOralFacade
{

	List<InepOralTest> getVarianceOralOnly( Collaborator c, InepPackage pack );

	List<InepPackage> getEvents( Collaborator auth );

	InepRevisor getRevisor( InepPackage event, Collaborator auth );

}
