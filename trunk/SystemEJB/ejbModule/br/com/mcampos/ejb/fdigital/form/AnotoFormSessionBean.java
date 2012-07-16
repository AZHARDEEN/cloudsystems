package br.com.mcampos.ejb.fdigital.form;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.fdigital.form.media.FormMedia;
import br.com.mcampos.ejb.fdigital.form.media.FormMediaSessionLocal;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;

/**
 * Session Bean implementation class AnotoFormSessionBean
 */
@Stateless( name = "AnotoFormSession", mappedName = "AnotoFormSession" )
@LocalBean
public class AnotoFormSessionBean extends SimpleSessionBean<AnotoForm> implements AnotoFormSession, AnotoFormLocal
{
	@EJB
	private MediaSessionBeanLocal mediaSession;

	@EJB
	private FormMediaSessionLocal formMediaSession;

	@Override
	protected Class<AnotoForm> getEntityClass( )
	{
		return AnotoForm.class;
	}

	@Override
	public AnotoForm merge( AnotoForm newEntity )
	{
		if ( newEntity.getId( ) == null || newEntity.getId( ).equals( 0 ) ) {
			newEntity.setId( getNextId( ) );
		}
		if ( newEntity.getFrmInsertDt( ) == null ) {
			newEntity.setFrmInsertDt( new Date( ) );
		}
		return super.merge( newEntity );
	}

	@Override
	public AnotoForm getRelationships( AnotoForm f )
	{
		if ( f == null ) {
			return null;
		}
		f = get( f.getId( ) );
		if ( f == null ) {
			return null;
		}
		f.getPads( ).size( );
		f.getClients( ).size( );
		f.getMedias( ).size( );
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.fdigital.form.AnotoFormSession#add(br.com.mcampos.
	 * ejb.fdigital.form.AnotoForm, br.com.mcampos.dto.MediaDTO)
	 */
	@Override
	public AnotoForm add( AnotoForm f, MediaDTO m )
	{
		if ( f == null || m == null )
			return null;
		f = merge( f );
		Media media = mediaSession.add( m );
		FormMedia fm = new FormMedia( );
		fm.setMedia( media );
		f.add( fm );
		formMediaSession.merge( fm );
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.fdigital.form.AnotoFormSession#remove(br.com.mcampos
	 * .ejb.fdigital.form.AnotoForm,
	 * br.com.mcampos.ejb.fdigital.form.media.FormMedia)
	 */
	@Override
	public AnotoForm remove( AnotoForm f, FormMedia fm )
	{
		if ( f == null || fm == null )
			return null;
		f = merge( f );
		f.remove( fm );
		formMediaSession.remove( fm );
		return f;
	}
}
