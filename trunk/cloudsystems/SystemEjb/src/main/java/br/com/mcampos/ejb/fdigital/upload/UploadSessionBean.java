package br.com.mcampos.ejb.fdigital.upload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.fdigital.pen.AnotoPenSessionLocal;
import br.com.mcampos.ejb.fdigital.penpage.AnotoPenPageSessionLocal;
import br.com.mcampos.ejb.fdigital.pgc.PgcSessionLocal;
import br.com.mcampos.ejb.fdigital.pgcstatus.PgcStatusSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.system.revisionstatus.RevisionStatusSessionLocal;
import br.com.mcampos.jpa.fdigital.AnotoPen;
import br.com.mcampos.jpa.fdigital.AnotoPenPage;
import br.com.mcampos.jpa.fdigital.Pgc;
import br.com.mcampos.jpa.fdigital.PgcStatus;
import br.com.mcampos.jpa.system.Media;

/**
 * Session Bean implementation class UploadSessionBean
 */
@Stateless( name = "UploadSession", mappedName = "UploadSession" )
@LocalBean
public class UploadSessionBean implements UploadSession
{
	@EJB
	private PgcSessionLocal pgcSession;

	@EJB
	private MediaSessionBeanLocal mediaSession;

	@EJB
	private PgcStatusSessionLocal pgcStatus;

	@EJB
	private RevisionStatusSessionLocal revisionStatus;

	@EJB
	private AnotoPenSessionLocal penSession;

	@EJB
	private AnotoPenPageSessionLocal penPageSession;

	@Override
	public void setStatus( Pgc pgc, Integer status )
	{
		if ( pgc == null || status == null ) {
			return;
		}
		pgc = this.pgcSession.merge( pgc );
		if ( pgc == null ) {
			return;
		}
		PgcStatus s = this.pgcStatus.get( status );
		if ( s == null ) {
			return;
		}
		pgc.setStatus( s );
	}

	@Override
	public Pgc persist( Pgc pgc, MediaDTO dto )
	{
		if ( pgc == null || dto == null ) {
			return null;
		}
		Media media = this.mediaSession.add( dto );
		if ( media == null ) {
			return null;
		}
		pgc.setMedia( media );
		pgc.setInsertDate( new Date( ) );
		if ( pgc.getStatus( ) == null || pgc.getStatus( ).getId( ) == null ) {
			pgc.getStatus( ).setId( 1 );
		}
		pgc.setStatus( this.pgcStatus.get( (pgc.getStatus( ).getId( )) ) );
		pgc.setRevisionStatus( this.revisionStatus.get( 1 ) );
		return this.pgcSession.merge( pgc );
	}

	@Override
	public AnotoPen getPen( String pen )
	{
		AnotoPen entity = this.penSession.get( pen );
		return entity;
	}

	@Override
	public List<AnotoPenPage> getPenPages( String penId, List<String> pages )
	{
		AnotoPen pen = getPen( penId );
		if ( pen == null ) {
			return Collections.emptyList( );
		}
		ArrayList<AnotoPenPage> list = new ArrayList<AnotoPenPage>( pages.size( ) );
		for( String pageId : pages ) {
			AnotoPenPage penPage = this.penPageSession.get( pen, pageId );
			if ( penPage == null ) {
				return Collections.emptyList( );
			}
			list.add( penPage );
		}
		return list;
	}
}
