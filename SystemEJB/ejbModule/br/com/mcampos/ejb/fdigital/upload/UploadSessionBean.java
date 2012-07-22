package br.com.mcampos.ejb.fdigital.upload;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.pgc.Pgc;
import br.com.mcampos.ejb.fdigital.pgc.PgcSessionLocal;
import br.com.mcampos.ejb.fdigital.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.fdigital.pgcstatus.PgcStatusSessionLocal;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.system.revisionstatus.RevisionStatusSessionLocal;

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

	@Override
	public Pgc add( MediaDTO dto )
	{
		Media media = this.mediaSession.add( dto );
		Pgc n = new Pgc( media );
		n.setInsertDate( new Date( ) );
		n.setPgcStatus( this.pgcStatus.get( 1 ) );
		n.setRevisionStatus( this.revisionStatus.get( 1 ) );
		return this.pgcSession.merge( n );
	}

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
		pgc.setPgcStatus( s );
	}

	public Pgc update( Pgc pgc )
	{
		if ( pgc == null ) {
			return null;
		}
		return this.pgcSession.merge( pgc );
	}
}
