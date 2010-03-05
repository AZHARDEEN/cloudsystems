package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PadPK;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.core.NoSuchPermissionException;
import com.anoto.api.core.Pen;
import com.anoto.api.core.PenCreationException;
import com.anoto.api.core.PenHome;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnodeFacade", mappedName = "CloudSystems-EjbPrj-AnodeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnodeFacadeBean extends AbstractSecurity implements AnodeFacade
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    @EJB
    private AnodeFormSessionLocal formSession;
    @EJB
    private AnodePenSessionLocal penSession;
    @EJB
    private MediaSessionLocal mediaSession;
    @EJB
    private PadSessionLocal padSession;
    @EJB
    private PGCSessionLocal pgcSession;

    public AnodeFacadeBean()
    {
    }

    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.add( DTOFactory.copy( entity ) ).toDTO();
    }

    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        formSession.delete( entity.getId() );
    }

    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.get( entity.getId() ).toDTO();
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return toFormList( formSession.getAll() );
    }

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.update( DTOFactory.copy( entity ) ).toDTO();
    }


    public MediaDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        /*
         * As etapas para adicionar um pad:
         * 1) Verificar a existência do formulário.
         * 2) Inserir a mídia.
         * 3) Vincular o formulário à midia
         */
        AnotoForm form = formSession.get( entity.getId() );
        Media media = mediaSession.add( DTOFactory.copy( pad ) );
        media = formSession.addPadFile( form, media );

        return media.toDTO();
    }

    public MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        /*
         * As etapas para adicionar um pad:
         * 1) Verificar a existência do formulário.
         * 2) Inserir a mídia.
         * 3) Vincular o formulário à midia
         */
        AnotoForm form = formSession.get( entity.getId() );
        Media media = mediaSession.get( pad.getId() );
        return formSession.removePadFile( form, media ).toDTO();
    }

    public List<PadDTO> getPads( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm entity = formSession.get( form.getId() );
        return toPadList( formSession.getPads( entity ) );
    }


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 7;
    }

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.nextId();
    }


    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */


    public PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.add( DTOFactory.copy( entity ) ).toDTO();
    }

    public void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        penSession.delete( entity.getId() );
    }

    public PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.get( entity.getId() ).toDTO();
    }

    public List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( penSession.getAll() );
    }

    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.update( DTOFactory.copy( entity ) ).toDTO();
    }

    protected List<FormDTO> toFormList( List<AnotoForm> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FormDTO> dtoList = new ArrayList<FormDTO>( list.size() );
        for ( AnotoForm f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<MediaDTO> toMediaList( List<Media> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtoList = new ArrayList<MediaDTO>( list.size() );
        for ( Media f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<PenDTO> toPenList( List<AnotoPen> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PenDTO> dtoList = new ArrayList<PenDTO>( list.size() );
        for ( AnotoPen f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<PadDTO> toPadList( List<Pad> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PadDTO> dtoList = new ArrayList<PadDTO>( list.size() );
        for ( Pad f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<AnotoPenPageDTO> toPenPageList( List<AnotoPenPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPenPageDTO> dtoList = new ArrayList<AnotoPenPageDTO>( list.size() );
        for ( AnotoPenPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    public byte[] getObject( AuthenticationDTO auth, MediaDTO key ) throws ApplicationException
    {
        authenticate( auth );
        return mediaSession.getObject( key.getId() );
    }

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM Páginas
     *
     * *************************************************************************
     * *************************************************************************
     */

    protected List<AnotoPageDTO> toPageList( List<AnotoPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPageDTO> dtoList = new ArrayList<AnotoPageDTO>( list.size() );
        for ( AnotoPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

    public List<AnotoPageDTO> getPages( AuthenticationDTO auth, PadDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        PadPK key = new PadPK( pad.getFormId(), pad.getId() );
        Pad entity = padSession.get( key );

        return toPageList( padSession.getPages( entity ) );
    }


    public List<MediaDTO> getImages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        return toMediaList( padSession.getImages( getPageEntity( page ) ) );
    }

    public MediaDTO removeFromPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPage pageEntity = getPageEntity( page );
        return padSession.removeImage( pageEntity, mediaSession.get( image.getId() ) ).toDTO();
    }

    public MediaDTO addToPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException
    {
        authenticate( auth );
        Media imageEntity = mediaSession.add( DTOFactory.copy( image ) );
        return padSession.addImage( getPageEntity( page ), imageEntity ).toDTO();
    }


    protected List<AnotoPen> loadPenEntityList( List<PenDTO> pens ) throws ApplicationException
    {
        List<AnotoPen> entities = new ArrayList<AnotoPen>( pens.size() );
        for ( PenDTO pen : pens ) {
            entities.add( penSession.get( pen.getId() ) );
        }
        return entities;
    }

    public void removePens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );

        List<AnotoPen> entities = loadPenEntityList( pens );
        padSession.removePens( getPageEntity( page ), entities );
    }

    public void addPens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPen> entities = loadPenEntityList( pens );
        padSession.addPens( getPageEntity( page ), entities );
    }


    public List<PenDTO> getAvailablePens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( padSession.getAvailablePens( getPageEntity( page ) ) );
    }

    protected AnotoPage getPageEntity( AnotoPageDTO page )
    {
        AnotoPagePK key = new AnotoPagePK( page );
        AnotoPage entity = padSession.getPage( key );
        return entity;
    }

    public List<PenDTO> getPens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( padSession.getPens( getPageEntity( page ) ) );
    }


    protected List<PGCDTO> toPgcList( List<Pgc> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PGCDTO> dtoList = new ArrayList<PGCDTO>( list.size() );
        for ( Pgc f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<PgcPenPageDTO> toPgcPenPageList( List<PgcPenPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcPenPageDTO> dtoList = new ArrayList<PgcPenPageDTO>( list.size() );
        for ( PgcPenPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public List<PGCDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return toPgcList( pgcSession.getAll() );
    }

    public List<PgcPenPageDTO> get( AuthenticationDTO auth, AnotoPenPageDTO penPage ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( penPage.getPenId() );
        AnotoPage page = getPageEntity( penPage.getPage() );
        AnotoPenPage entity = padSession.getPenPage( pen, page );
        return toPgcPenPageList( pgcSession.getAll( entity ) );
    }


    public PGCDTO add( PGCDTO dto ) throws ApplicationException
    {
        //authenticate( auth ); IT´S FREE FOR NOW
        /*Does this media exists??*/
        Pgc pgc = DTOFactory.copy( dto );
        Media media = mediaSession.add( pgc.getMedia() );
        pgc.setMedia( media );
        pgc.setInsertDate( new Date() );
        System.out.println( "Pgc: " + media.getName() + " is loaded!" );
        Pen pen = getPgcPenObject( pgc );
        PgcStatus status = new PgcStatus( 1 );
        pgc.setPgcStatus( status );
        /*Search for a pen in our database*/
        pgc = pgcSession.add( pgc );
        verifyBindings( pgc, pen );
        return pgc.toDTO();
    }

    protected boolean verifyBindings( Pgc pgc, Pen pen ) throws ApplicationException
    {
        String penId;
        AnotoPen anotoPen;
        try {
            penId = pen.getPenData().getPenSerial();
            System.out.println( "Pgc has this pen id: " + penId );
            anotoPen = penSession.get( penId );
            if ( anotoPen == null )
                System.out.println( "No pen id database: " + penId );
        }
        catch ( NoSuchPermissionException e ) {
            anotoPen = null;
        }
        if ( anotoPen == null ) {
            pgcSession.setPgcStatus( pgc, PgcStatus.statusNoPen );
            return false;
        }
        return hasAnotoPages( pgc, pen, anotoPen );
    }

    protected boolean hasAnotoPages( Pgc pgc, Pen pen, AnotoPen anotoPen ) throws ApplicationException
    {
        boolean missAny = false;
        Iterator it = pen.getPageAddresses();
        while ( it.hasNext() ) {
            String address = ( String )it.next();
            List<AnotoPage> list = padSession.getPages( address );
            /*Now I have a page and a pen */
            if ( SysUtils.isEmpty( list ) == false )
                attachPenPage( list, anotoPen, pgc );
            else {
                missAny = true;
                pgcSession.setPgcStatus( pgc, PgcStatus.statusNoPenForm );
            }
        }
        return !missAny;
    }

    protected boolean attachPenPage( List<AnotoPage> pages, AnotoPen pen, Pgc pgc ) throws ApplicationException
    {
        AnotoPenPage item;
        AnotoPenPagePK key;

        key = new AnotoPenPagePK();
        key.setPen( pen );
        for ( AnotoPage page : pages ) {
            key.setPage( page );
            item = padSession.getPenPage( pen, page );
            if ( item != null ) {
                pgcSession.attach( pgc, item );
            }
        }
        return true;
    }

    protected Pen getPgcPenObject( Pgc pgc )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgc.getMedia().getObject() );
        Pen pen = null;
        try {
            pen = PenHome.read( is );
        }
        catch ( PenCreationException e ) {
            System.err.println( e.getMessage() );
            pen = null;
        }
        return pen;
    }

    public List<AnotoPenPageDTO> getPenPages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPenPage> list = pgcSession.get( getPageEntity( page ) );
        return toPenPageList( list );
    }
}

