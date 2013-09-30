package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;

@Remote
public interface StationSession extends BaseSessionInterface
{
	InepEvent getCurrentEvent( PrincipalDTO auth );

	/**
	 * Brief Obtem uma lista de inscrições de acordo com uma pesquisa pela parte do número de inscrições. A procura pelas inscrições deve levar em conta o
	 * evento e se o usuário corrente pertence a algum posto aplicador.
	 * 
	 * @param auth
	 * @param evt
	 * @param part
	 * @return List<InepSubscription> Lista de inscrições
	 */
	List<InepSubscription> getSubscriptions( PrincipalDTO auth, InepEvent evt, String part );
}
