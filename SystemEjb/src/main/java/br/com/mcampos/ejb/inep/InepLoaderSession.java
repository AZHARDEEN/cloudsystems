package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepTestPK;

@Remote
public interface InepLoaderSession
{

	List<InepEvent> getAvailableEvents( );

	boolean insert( InepTestPK key, byte[ ] object );

	boolean insert( InepOralTest entity, boolean createSubscription );

}
