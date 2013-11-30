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

	public List<InepSubscription> getAll( PrincipalDTO auth, InepEvent event, String subs );

	/**
	 * Brief Retorna uma lista de inscrições (@see InepSubscription) filtrando por uma parte do código da inscricao e também pelo posto associado ao
	 * collaborador
	 * 
	 * 
	 * @param auth
	 * @param event
	 * @param subs
	 * @param stations
	 *            Lista de id (cli_seq_in) do posto. Neste caso é o id da lista de clientes.
	 * @return List<InepSubscription>
	 */
	public List<InepSubscription> getAll( PrincipalDTO auth, InepEvent event, String subs, List<Integer> stations );

	public void setOralGrade( InepSubscription s, BigDecimal grade );

	public void setWrittenGrade( InepSubscription s, BigDecimal grade );

	public InepSubscription get( String id );

}
