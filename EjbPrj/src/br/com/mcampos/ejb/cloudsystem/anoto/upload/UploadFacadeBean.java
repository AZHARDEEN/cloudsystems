package br.com.mcampos.ejb.cloudsystem.anoto.upload;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.core.AbstractSecurity;

import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "UploadFacade", mappedName = "CloudSystems-EjbPrj-UploadFacade" )
@Remote
public class UploadFacadeBean extends AbstractSecurity implements UploadFacade
{
    public static final Integer messageId = 25;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private MediaSessionLocal mediaSession;

    @EJB
    private PGCSessionLocal pgcSession;

    @EJB
    private AnodePenSessionLocal penSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public PGCDTO add( PGCDTO dto, List<String> addresses, ArrayList<MediaDTO> medias,
                       List<PgcPropertyDTO> properties ) throws ApplicationException
    {
        //authenticate( auth ); IT´S FREE FOR NOW
        /*Does this media exists??*/
        Pgc pgc = DTOFactory.copy( dto );
        Media media = mediaSession.add( pgc.getMedia() );
        pgc.setMedia( media );
        pgc.setInsertDate( new Date() );
        PgcStatus status = new PgcStatus( 1 );
        pgc.setPgcStatus( status );
        /*Search for a pen in our database*/
        pgc = pgcSession.add( pgc );
        addPgcAttachment( pgc, medias );
        addProperties( pgc, properties );
        verifyBindings( pgc, addresses );
        return pgc.toDTO();
    }


    private void addPgcAttachment( Pgc pgc, ArrayList<MediaDTO> medias ) throws ApplicationException
    {
        Media media;

        if ( SysUtils.isEmpty( medias ) == false ) {
            for ( MediaDTO m : medias ) {
                media = mediaSession.add( DTOFactory.copy( m ) );
                PgcAttachment attach = new PgcAttachment();
                attach.setMediaId( media.getId() );
                attach.setPgcId( pgc.getId() );
                getEntityManager().persist( attach );
            }
        }
    }

    private void addProperties( Pgc pgc, List<PgcPropertyDTO> properties ) throws ApplicationException
    {
        int nSequence = 1;

        for ( PgcPropertyDTO p : properties ) {
            for ( String s : p.getValues() ) {
                s = s.replaceAll( "'", "" );
                if ( SysUtils.isEmpty( s ) )
                    continue;
                PgcProperty property = new PgcProperty();
                property.setPgc_id_in( pgc.getId() );
                property.setPgp_id_in( p.getId() );
                property.setPpg_value_ch( s );
                property.setPgp_seq_in( nSequence );
                nSequence++;
                getEntityManager().persist( property );
            }
        }
    }


    private boolean verifyBindings( Pgc pgc, List<String> addresses ) throws ApplicationException
    {
        AnotoPen anotoPen;
        anotoPen = penSession.get( pgc.getPenId() );
        if ( anotoPen == null ) {
            pgcSession.setPgcStatus( pgc, PgcStatus.statusNoPen );
            return false;
        }
        return hasAnotoPages( pgc, addresses, anotoPen );
    }

    private boolean hasAnotoPages( Pgc pgc, List<String> addresses, AnotoPen anotoPen ) throws ApplicationException
    {
        for ( String address : addresses ) {
            List<AnotoPage> list = padSession.getPages( address );
            if ( SysUtils.isEmpty( list ) == false )
                attachPenPage( list, anotoPen, pgc );
            else {
                if ( pgc.getPgcStatus().getId() != PgcStatus.statusNoPenForm )
                    pgcSession.setPgcStatus( pgc, PgcStatus.statusNoPenForm );
            }
        }
        return true;
    }

    private boolean attachPenPage( List<AnotoPage> pages, AnotoPen pen, Pgc pgc ) throws ApplicationException
    {
        AnotoPenPage item;

        for ( AnotoPage page : pages ) {
            item = padSession.getPenPage( pen, page );
            if ( item != null ) {
                pgcSession.attach( pgc, item );
            }
        }
        return true;
    }
}
