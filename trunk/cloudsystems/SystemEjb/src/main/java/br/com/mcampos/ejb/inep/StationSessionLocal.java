package br.com.mcampos.ejb.inep;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.StationGradeDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.inep.InepSubscription;

@Local
public interface StationSessionLocal extends BaseSessionInterface
{
	void setMissing( PrincipalDTO auth, InepSubscription subscription );

	void setObserverInformation( PrincipalDTO auth, InepSubscription subscription, Integer[ ] grades );

	void setInterviewerInformation( PrincipalDTO auth, InepSubscription subscription, int[ ] elements, int grade );

	StationGradeDTO getStationGrade( PrincipalDTO auth, InepSubscription subscription );
}
