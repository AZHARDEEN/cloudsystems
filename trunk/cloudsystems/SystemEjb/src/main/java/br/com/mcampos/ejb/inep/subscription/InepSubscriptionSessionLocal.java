package br.com.mcampos.ejb.inep.subscription;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;

@Local
public interface InepSubscriptionSessionLocal extends BaseCrudSessionInterface<InepSubscription>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	public List<InepSubscription> getAll( InepEvent event );

	public List<InepSubscription> getAll( InepEvent event, String subs );

	public void setOralGrade( InepSubscription s, BigDecimal grade );

	public void setWrittenGrade( InepSubscription s, BigDecimal grade );

}
