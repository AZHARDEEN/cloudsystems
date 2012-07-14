package br.com.mcampos.ejb.fdigital.form;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class AnotoFormSessionBean
 */
@Stateless( name = "AnotoFormSession", mappedName = "AnotoFormSession" )
@LocalBean
public class AnotoFormSessionBean extends SimpleSessionBean<AnotoForm> implements AnotoFormSession, AnotoFormLocal
{
	@Override
	protected Class<AnotoForm> getEntityClass( )
	{
		return AnotoForm.class;
	}

	@Override
	public AnotoForm merge( AnotoForm newEntity )
	{
		if ( newEntity.getId( ) == null || newEntity.getId( ).equals( 0 ) )
			newEntity.setId( getNextId( ) );
		if ( newEntity.getFrmInsertDt( ) == null )
			newEntity.setFrmInsertDt( new Date( ) );
		return super.merge( newEntity );
	}
}
