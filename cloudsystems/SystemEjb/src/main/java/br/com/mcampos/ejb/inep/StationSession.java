package br.com.mcampos.ejb.inep;

import java.io.Serializable;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;

@Remote
public interface StationSession extends BaseSessionInterface
{
	InepEvent getCurrentEvent( PrincipalDTO auth );

	InepSubscription getSubscription( PrincipalDTO auth, Serializable key );
}
