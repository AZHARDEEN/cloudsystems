package br.com.mcampos.ejb.fdigital.form.pad;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPageSessionLocal;
import br.com.mcampos.entity.fdigital.AnotoPage;
import br.com.mcampos.entity.fdigital.Pad;

/**
 * Session Bean implementation class PadSessionBean
 */
@Stateless( name = "PadSession", mappedName = "PadSession" )
@LocalBean
public class PadSessionBean extends SimpleSessionBean<Pad> implements PadSessionLocal, PadSession
{
	@EJB
	private AnotoPageSessionLocal pageSession;

	@Override
	protected Class<Pad> getEntityClass( )
	{
		return Pad.class;
	}

	@Override
	public Pad add( Pad pad, List<AnotoPage> pages )
	{
		for ( AnotoPage page : pages ) {
			pad.add( page );
			this.pageSession.merge( page );
		}
		return pad;
	}

	@Override
	public Pad merge( Pad newEntity )
	{
		newEntity = super.merge( newEntity );
		if ( newEntity.getInsertDate( ) == null ) {
			newEntity.setInsertDate( new Date( ) );
		}
		if ( newEntity.getUnique( ) == null ) {
			newEntity.setUnique( false );
		}
		return newEntity;
	}

}
