package br.com.mcampos.ejb.inep;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.entity.inep.InepOralDistribution;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.system.Media;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepOralFacade
{

	List<InepOralTest> getVarianceOralOnly( PrincipalDTO c, InepPackage pack );

	List<InepPackage> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepPackage event, PrincipalDTO auth );

	List<InepOralTeamDTO> getOralTeamToChoice( InepPackage event, PrincipalDTO auth );

	void distribute( InepPackage event, PrincipalDTO auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests );

	List<InepOralDistribution> getOralTests( InepRevisor revisor );

	List<Media> getAudios( InepSubscription test );

	boolean uploadAudio( Integer companyId, Integer eventId, String isc, MediaDTO obj );

	void updateGrade( InepOralDistribution item, int grade );

}
