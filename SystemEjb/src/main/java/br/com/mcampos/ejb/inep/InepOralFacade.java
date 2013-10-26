package br.com.mcampos.ejb.inep;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.system.Media;

@Remote
public interface InepOralFacade extends BaseSessionInterface
{

	List<InepOralTest> getVarianceOralOnly( PrincipalDTO c, InepEvent pack );

	List<InepEvent> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepEvent event, PrincipalDTO auth );

	List<InepOralTeamDTO> getOralTeamToChoice( InepEvent event, PrincipalDTO auth );

	void distribute( InepEvent event, PrincipalDTO auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests );

	List<InepOralDistribution> getOralTests( InepRevisor revisor );

	List<Media> getAudios( InepSubscription test );

	boolean uploadAudio( Integer companyId, Integer eventId, String isc, MediaDTO obj );

	void updateGrade( InepOralDistribution item, int grade );

}
