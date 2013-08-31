package br.com.mcampos.ejb.inep.subscription;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface InepSubscriptionSessionLocal extends BaseSessionInterface<InepSubscription>
{
	List<InepPackage> getEvents( PrincipalDTO auth );

	public List<InepSubscription> getAll( InepPackage event );

	public List<InepSubscription> getAll( InepPackage event, String subs );

	public void setOralGrade( InepSubscription s, BigDecimal grade );

	public void setWrittenGrade( InepSubscription s, BigDecimal grade );

}
