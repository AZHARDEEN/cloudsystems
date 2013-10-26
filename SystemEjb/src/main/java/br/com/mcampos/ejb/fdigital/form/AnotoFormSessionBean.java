package br.com.mcampos.ejb.fdigital.form;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.fdigital.form.media.FormMediaSessionLocal;
import br.com.mcampos.ejb.fdigital.form.pad.PadSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.fdigital.AnotoForm;
import br.com.mcampos.jpa.fdigital.AnotoPage;
import br.com.mcampos.jpa.fdigital.FormMedia;
import br.com.mcampos.jpa.fdigital.Pad;
import br.com.mcampos.jpa.system.Media;

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

	@EJB
	private PadSessionLocal padSession;

	@Override
	protected Class<AnotoForm> getEntityClass( )
	{
		return AnotoForm.class;
	}

	@Override
	public AnotoForm merge( AnotoForm newEntity )
	{
		if( newEntity.getId( ) == null || newEntity.getId( ).equals( 0 ) ) {
			newEntity.setId( getNextId( ) );
		}
		if( newEntity.getFrmInsertDt( ) == null ) {
			newEntity.setFrmInsertDt( new Date( ) );
		}
		return super.merge( newEntity );
	}

	@Override
	public AnotoForm getRelationships( AnotoForm f )
	{
		if( f == null ) {
			return null;
		}
		f = get( f.getId( ) );
		if( f == null ) {
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
	 * ejb.fdigital.form.AnotoForm, br.com.mcampos.dto.system.MediaDTO)
	 */
	@Override
	public AnotoForm add( PrincipalDTO auth, AnotoForm f, MediaDTO m )
	{
		if( f == null || m == null ) {
			return null;
		}
		f = merge( f );
		Media media = mediaSession.add( m );
		FormMedia fm = new FormMedia( );
		fm.setMedia( media );
		f.add( fm );
		formMediaSession.add( auth, fm );
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
	public AnotoForm remove( PrincipalDTO auth, AnotoForm f, FormMedia fm )
	{
		if( f == null || fm == null ) {
			return null;
		}
		f = merge( f );
		f.remove( fm );
		formMediaSession.remove( auth, fm.getId( ) );
		return f;
	}

	@Override
	public byte[ ] getObject( Media media )
	{
		Media m = mediaSession.get( media.getId( ) );
		if( m != null ) {
			return m.getObject( );
		}
		else {
			return null;
		}
	}

	@Override
	public AnotoForm add( PrincipalDTO auth, AnotoForm f, MediaDTO m, List<AnotoPage> pages )
	{
		if( f == null || m == null || m.getObject( ) == null ) {
			return null;
		}
		merge( f );
		m.setFormat( "pad" );
		m.setMimeType( "application/xml" );
		Media media = mediaSession.add( m );
		if( media == null ) {
			return null;
		}
		Pad pad = new Pad( );
		pad.setMedia( media );
		f.add( pad );
		padSession.add( auth, pad );
		padSession.add( pad, pages );
		return f;
	}
}
