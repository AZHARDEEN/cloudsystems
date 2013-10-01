package br.com.mcampos.ejb.media;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.system.Media;

/**
 * Session Bean implementation class MediaSessionBean
 */
@Stateless
@LocalBean
public class MediaSessionBean extends SimpleSessionBean<Media> implements MediaSessionBeanRemote, MediaSessionBeanLocal
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2220683069716480L;

	@Override
	protected Class<Media> getEntityClass( )
	{
		return Media.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.media.MediaSessionBeanLocal#add(br.com.mcampos.dto
	 * .MediaDTO)
	 */
	@Override
	public Media add( MediaDTO m )
	{
		Media entity = this.createEntity( m );

		return this.merge( entity );
	}

	private Media createEntity( MediaDTO source )
	{
		Media target = new Media( source.getId( ) );

		target.setMimeType( source.getMimeType( ) );
		target.setName( source.getName( ) );
		target.setObject( source.getObject( ) );
		if ( source.getObject( ) == null ) {
			target.setSize( source.getSize( ) );
		}
		target.setFormat( source.getFormat( ) );
		target.setInsertDate( new Date( ) );
		target.setPath( source.getPath( ) );
		return target;

	}

	@Override
	public Media findByName( String name )
	{
		Media media = this.getByNamedQuery( Media.getByName, name );
		return media;
	}

	@Override
	public Media merge( Media newEntity )
	{
		if ( newEntity.getInsertDate( ) == null ) {
			newEntity.setInsertDate( new Date( ) );
		}
		return super.merge( newEntity );
	}
}
