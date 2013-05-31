package br.com.mcampos.ejb.inep;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface InepOralFacade
{

	List<InepOralTest> getVarianceOralOnly( Collaborator c, InepPackage pack );

	List<InepPackage> getEvents( Collaborator auth );

	InepRevisor getRevisor( InepPackage event, Collaborator auth );

	List<InepOralTeamDTO> getOralTeamToChoice( InepPackage event, Collaborator auth );

	void distribute( InepPackage event, Collaborator auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests );

	List<InepOralDistribution> getOralTests( InepRevisor revisor );

	List<Media> getAudios( InepSubscription test );

	boolean uploadAudio( Integer companyId, Integer eventId, String isc, MediaDTO obj );

}
