package br.com.mcampos.ejb.user.client.entry;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class ClientEntrySessionBean
 */
@Stateless( mappedName = "ClientEntrySession", name = "ClientEntrySession" )
@LocalBean
public class ClientEntrySessionBean extends SimpleSessionBean<ClientEntry> implements ClientEntrySession, ClientEntrySessionLocal
{

	@Override
	protected Class<ClientEntry> getEntityClass( )
	{
		return ClientEntry.class;
	}

	@Override
	public ClientEntry merge( ClientEntry newEntity )
	{
		if ( newEntity.getInsertDate( ) == null ) {
			newEntity.setInsertDate( new Date( ) );
		}
		return super.merge( newEntity );
	}

}
