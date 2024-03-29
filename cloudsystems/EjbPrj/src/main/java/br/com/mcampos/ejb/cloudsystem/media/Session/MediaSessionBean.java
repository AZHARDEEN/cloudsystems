package br.com.mcampos.ejb.cloudsystem.media.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

@Stateless( name = "MediaSession", mappedName = "MediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class MediaSessionBean extends Crud<Integer, Media> implements MediaSessionLocal
{
	public MediaSessionBean( )
	{
	}

	@Override
	public Media add( Media entity ) throws ApplicationException
	{
		entity.setId( null );
		return super.add( entity );
	}

	@Override
	public void delete( Integer key ) throws ApplicationException
	{
		delete( Media.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Media get( Integer key ) throws ApplicationException
	{
		return get( Media.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public byte[ ] getObject( Integer key ) throws ApplicationException
	{
		Media entity = get( key );
		byte[ ] object = null;

		if ( entity != null ) {
			if ( entity.getObject( ) != null && entity.getObject( ).length > 0 ) {
				object = entity.getObject( );
			}
		}

		return object;
	}
}
