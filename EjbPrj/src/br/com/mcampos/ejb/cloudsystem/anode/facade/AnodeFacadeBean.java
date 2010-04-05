package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PadPK;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.session.PgcPenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnodeFacade", mappedName = "CloudSystems-EjbPrj-AnodeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnodeFacadeBean extends AbstractSecurity implements AnodeFacade
{
    protected static final int SystemMessageTypeId = 7;

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

    @EJB
    private PgcPenPageSessionLocal pgcPenPageSession;

    public AnodeFacadeBean()
    {
    }

    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null )
            throwCommomException( 3 );
        try {
            return formSession.add( DTOFactory.copy( entity ) ).toDTO();
        }
        catch ( EJBException e ) {
            throwException( 1 );
            return null;
        }
    }

    public void addPens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPen> entities = loadPenEntityList( pens );
        formSession.add( DTOFactory.copy( form ), entities );
    }


    public void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPen> entities = loadPenEntityList( pens );
        for ( AnotoPen pen : entities ) {
            List list = pgcSession.getAll( pen );
            if ( SysUtils.isEmpty( list ) == false )
                throwRuntimeException( 1 );
        }
        formSession.remove( DTOFactory.copy( form ), entities );
    }


    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null )
            throwCommomException( 3 );
        try {
            formSession.delete( entity.getId() );
        }
        catch ( Exception e ) {
            throwRuntimeException( 2 );
        }
    }

    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null || SysUtils.isZero( entity.getId() ) )
            throwCommomException( 3 );
        AnotoForm form = formSession.get( entity.getId() );
        return form != null ? form.toDTO() : null;
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toFormList( formSession.getAll() );
    }

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.update( DTOFactory.copy( entity ) ).toDTO();
    }


    public PadDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad, List<String> pages ) throws ApplicationException
    {
        authenticate( auth );
        /*
         * As etapas para adicionar um pad:
         * 1) Verificar a existência do formulário.
         * 2) Inserir a mídia.
         * 3) Vincular o formulário à midia
         */
        AnotoForm form = formSession.get( entity.getId() );
        pad.setFormat( "pad" );
        Media media = mediaSession.add( DTOFactory.copy( pad ) );
        Pad padentity = formSession.addPadFile( form, media, pages );
        return padentity.toDTO();
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

    /*
     * Esta funcao nao possui autenticações pois necessita ser usado no upload de um pgc, o qual não possui usuário
     */

    public List<PadDTO> getPads( FormDTO form ) throws ApplicationException
    {
        AnotoForm entity = formSession.get( form.getId() );
        return AnotoUtils.toPadList( formSession.getPads( entity ) );
    }

    public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPenList( formSession.getAvailablePens( DTOFactory.copy( form ) ) );
    }

    public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPenList( formSession.getPens( DTOFactory.copy( form ) ) );
    }


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.nextId();
    }


    public MediaDTO addFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media entity = mediaSession.add( DTOFactory.copy( media ) );
        AnotoForm anotoForm = formSession.get( form.getId() );
        return formSession.addFile( anotoForm, entity ).getMedia().toDTO();
    }


    public void removeFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media entity = mediaSession.add( DTOFactory.copy( media ) );
        AnotoForm anotoForm = formSession.get( form.getId() );
        formSession.removeFile( anotoForm, entity );
    }

    public List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm anotoForm = formSession.get( form.getId() );
        List<FormMedia> list = formSession.getFiles( anotoForm );
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<Media> medias = new ArrayList<Media>( list.size() );
        for ( FormMedia fm : list ) {
            medias.add( fm.getMedia() );
        }
        return AnotoUtils.toMediaList( medias );
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
        return AnotoUtils.toPenList( penSession.getAll() );
    }

    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.update( DTOFactory.copy( entity ) ).toDTO();
    }

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    public byte[] getObject( MediaDTO key ) throws ApplicationException
    {
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

    public List<AnotoPageDTO> getPages( AuthenticationDTO auth, PadDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        PadPK key = new PadPK( pad.getFormId(), pad.getId() );
        Pad entity = padSession.get( key );

        return AnotoUtils.toPageList( padSession.getPages( entity ) );
    }

    public List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm entity = formSession.get( form.getId() );

        return AnotoUtils.toPageList( padSession.getPages( entity ) );
    }


    public List<AnotoPageDTO> getPages( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPageList( padSession.getPages() );
    }


    public List<MediaDTO> getImages( AnotoPageDTO page ) throws ApplicationException
    {
        return AnotoUtils.toMediaList( padSession.getImages( getPageEntity( page ) ) );
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
        return AnotoUtils.toPenList( padSession.getAvailablePens( getPageEntity( page ) ) );
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
        return AnotoUtils.toPenList( padSession.getPens( getPageEntity( page ) ) );
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


    public List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props ) throws ApplicationException
    {
        authenticate( auth );
        if ( props != null && props.size() > 0 ) {
            /*Trocar o DTO pela entidade*/
            Object value;

            value = props.get( "form" );
            if ( value != null ) {
                AnotoForm entity = formSession.get( ( ( FormDTO )value ).getId() );
                if ( entity != null )
                    props.put( "form", entity );
            }
        }
        List<PgcPenPage> list = pgcPenPageSession.getAll( props );
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoResultList> resultList = new ArrayList<AnotoResultList> ( );
        for ( PgcPenPage entity : list )
        {
            for ( PgcPage page : entity.getPgc().getPages() ) {
                AnotoResultList item = new AnotoResultList ( );
                item.setForm( entity.getPenPage().getPage().getPad().getForm().toDTO() );
                item.setPen( entity.getPenPage().getPen().toDTO() );
                item.setPgcPage( page.toDTO() );
                if ( resultList.contains( item ) == false )
                    resultList.add ( item );
            }
        }
        return resultList;
    }


    public PGCDTO add( PGCDTO dto, String penId, List<String> addresses ) throws ApplicationException
    {
        //authenticate( auth ); IT´S FREE FOR NOW
        /*Does this media exists??*/
        Pgc pgc = DTOFactory.copy( dto );
        Media media = mediaSession.add( pgc.getMedia() );
        pgc.setMedia( media );
        pgc.setInsertDate( new Date() );
        System.out.println( "Pgc: " + media.getName() + " is loaded!" );
        PgcStatus status = new PgcStatus( 1 );
        pgc.setPgcStatus( status );
        /*Search for a pen in our database*/
        pgc = pgcSession.add( pgc );
        verifyBindings( pgc, penId, addresses );
        return pgc.toDTO();
    }

    protected boolean verifyBindings( Pgc pgc, String penId, List<String> addresses ) throws ApplicationException
    {
        AnotoPen anotoPen;
        System.out.println( "Pgc has this pen id: " + penId );
        anotoPen = penSession.get( penId );
        if ( anotoPen == null )
            System.out.println( "No pen id database: " + penId );
        if ( anotoPen == null ) {
            pgcSession.setPgcStatus( pgc, PgcStatus.statusNoPen );
            return false;
        }
        return hasAnotoPages( pgc, addresses, anotoPen );
    }

    protected boolean hasAnotoPages( Pgc pgc, List<String> addresses, AnotoPen anotoPen ) throws ApplicationException
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

    public List<AnotoPenPageDTO> getPenPages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPenPage> list = pgcSession.get( getPageEntity( page ) );
        return AnotoUtils.toPenPageList( list );
    }

    public List<PgcPenPageDTO> getPgcPenPages( PGCDTO pgc ) throws ApplicationException
    {
        List<PgcPenPage> list = pgcSession.get( DTOFactory.copy( pgc ) );
        return AnotoUtils.toPgcPenPageList( list );
    }


    public void delete( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
    {
        authenticate( auth );
        Pgc entity = pgcSession.get( pgc.getId() );
        if ( entity != null ) {
            pgcPenPageSession.delete( entity );
            pgcSession.delete( pgc.getId() );
        }
    }

    public void addProcessedImage( PGCDTO pgc, MediaDTO media, int book, int page ) throws ApplicationException
    {
        Pgc entity = pgcSession.get( pgc.getId() );
        if ( entity != null ) {
            Media mediaEntity = mediaSession.add( DTOFactory.copy( media ) );
            PgcProcessedImage pi = new PgcProcessedImage( new PgcPage( entity, book, page ), mediaEntity, book, page );
            pgcSession.add( pi );
        }
    }

    public void addPgcField( PgcFieldDTO dto ) throws ApplicationException
    {
        Media media = null;
        if ( dto.getMedia() != null )
            media = mediaSession.add( DTOFactory.copy( dto.getMedia() ) );
        PgcField field = DTOFactory.copy( dto );
        field.setMedia( media );
        pgcSession.add( field );
    }

    public void addPgcAttachment( PgcAttachmentDTO dto ) throws ApplicationException
    {
        Media media = null;
        if ( dto.getMedia() != null )
            media = mediaSession.add( DTOFactory.copy( dto.getMedia() ) );
        PgcAttachment entity = DTOFactory.copy( dto );
        entity.setMedia( media );
        pgcSession.add( entity );
    }

    public void add( PgcPageDTO dto ) throws ApplicationException
    {
        PgcPage entity = DTOFactory.copy( dto );
        pgcSession.add( entity );
    }

    public List<MediaDTO> getImages( PgcPageDTO page ) throws ApplicationException
    {
        return AnotoUtils.toMediaList( pgcSession.getImages( DTOFactory.copy ( page ) ) );
    }
}

