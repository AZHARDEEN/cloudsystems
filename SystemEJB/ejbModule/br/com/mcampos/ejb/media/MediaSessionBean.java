package br.com.mcampos.ejb.media;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class MediaSessionBean
 */
@Stateless
@LocalBean
public class MediaSessionBean extends SimpleSessionBean<Media> implements MediaSessionBeanRemote, MediaSessionBeanLocal
{

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
		Media entity = createEntity( m );

		return merge( entity );
	}

	private Media createEntity( MediaDTO source )
	{
		Media target = new Media( source.getId( ) );

		target.setMimeType( source.getMimeType( ) );
		target.setName( source.getName( ) );
		target.setObject( source.getObject( ) );
		target.setFormat( source.getFormat( ) );
		target.setMedInsetDt( new Date( ) );
		return target;

	}

	@Override
	public Media findByName( String name )
	{
		Media media = getByNamedQuery( Media.getByName, name );
		return media;
	}
}
