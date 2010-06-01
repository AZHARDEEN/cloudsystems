package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
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
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.AnotoPageFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.image.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.FieldTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
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
    private transient EntityManager em;


    @EJB
    private AnotoFormSessionLocal formSession;
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

    @EJB
    private FieldTypeSessionLocal fieldTypeSession;

    @EJB
    private PageFieldSessionLocal pageFieldSession;


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
        if ( anotoForm == null )
            return Collections.emptyList();
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


    public List<PGCDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPgcList( pgcSession.getAll() );
    }


    public List<PGCDTO> getSuspendedPgc( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPgcList( pgcSession.getSuspended() );
    }


    public List<PgcPenPageDTO> get( AuthenticationDTO auth, AnotoPenPageDTO penPage ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( penPage.getPenId() );
        AnotoPage page = getPageEntity( penPage.getPage() );
        AnotoPenPage entity = padSession.getPenPage( pen, page );
        return AnotoUtils.toPgcPenPageList( pgcSession.getAll( entity ) );
    }


    public List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props,
                                                   Integer maxRecords ) throws ApplicationException
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
        List<PgcPage> list = pgcPenPageSession.getAll( props, maxRecords );
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoResultList> resultList = new ArrayList<AnotoResultList>();
        for ( PgcPage page : list ) {
            AnotoResultList item = new AnotoResultList();

            item.setForm( page.getPgc().getPgcPenPages().get( 0 ).getPenPage().getPage().getPad().getForm().toDTO() );
            item.setPen( page.getPgc().getPgcPenPages().get( 0 ).getPenPage().getPen().toDTO() );
            item.setPgcPage( page.toDTO() );
            if ( resultList.contains( item ) == false )
                resultList.add( item );
        }
        return resultList;
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
        System.out.println( "Pgc: " + media.getName() + " is loaded!" );
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

    public void setPgcStatus( PGCDTO dto, Integer newStatus ) throws ApplicationException
    {
        Pgc pgc = pgcSession.get( dto.getId() );
        if ( pgc != null )
            pgcSession.setPgcStatus( pgc, newStatus );
    }

    protected boolean verifyBindings( Pgc pgc, List<String> addresses ) throws ApplicationException
    {
        AnotoPen anotoPen;
        System.out.println( "Pgc has this pen id: " + pgc.getPenId() );
        anotoPen = penSession.get( pgc.getPenId() );
        if ( anotoPen == null ) {
            System.out.println( "No pen id database " + pgc.getPenId() );
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
        AnotoPageField pageField = pageFieldSession.get( new AnotoPageFieldPK( dto.getPgcPage().getAnotoPage(), dto.getName() ) );
        if ( pageField != null )
            field.setType( pageField.getType() );
        else
            field.setType( getEntityManager().find( FieldType.class, field.getType().getId() ) );
        pgcSession.add( field );
    }

    public void addPgcAttachment( PgcAttachmentDTO dto ) throws ApplicationException
    {
        Media media = null;
        if ( dto.getMedia() != null )
            media = mediaSession.add( DTOFactory.copy( dto.getMedia() ) );
        PgcPageAttachment entity = DTOFactory.copy( dto );
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
        return AnotoUtils.toMediaList( pgcSession.getImages( DTOFactory.copy( page ) ) );
    }

    public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        List<PgcField> fields = pgcSession.getFields( DTOFactory.copy( page ) );
        if ( SysUtils.isEmpty( fields ) )
            return Collections.emptyList();
        return AnotoUtils.toPgcFieldList( fields );
    }


    public void update( AuthenticationDTO auth, PgcFieldDTO field ) throws ApplicationException
    {
        authenticate( auth );
        pgcSession.update( DTOFactory.copy( field ) );
    }

    public Integer remove( AuthenticationDTO auth, AnotoResultList item ) throws ApplicationException
    {
        authenticate( auth );
        return pgcSession.remove( item );
    }

    public List<PgcAttachmentDTO> getAttachments( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPgcAttachmentList( pgcSession.getAttachments( DTOFactory.copy( page ) ) );
    }

    public List<MediaDTO> getAttachments( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
    {
        authenticate( auth );
        Pgc entity = pgcSession.get( pgc.getId() );
        if ( entity != null ) {
            List<PgcAttachment> attachments = pgcSession.getAttachments( entity );
            if ( SysUtils.isEmpty( attachments ) )
                return Collections.emptyList();
            List<MediaDTO> medias = new ArrayList<MediaDTO>( attachments.size() );
            for ( PgcAttachment a : attachments ) {
                medias.add( mediaSession.get( a.getMediaId() ).toDTO() );
            }
            return medias;
        }
        else
            return Collections.emptyList();
    }

    public List<PgcPropertyDTO> getProperties( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
    {
        authenticate( auth );
        Pgc entity = pgcSession.get( pgc.getId() );
        if ( entity != null ) {
            List<PgcProperty> attachments = pgcSession.getProperties( entity );
            if ( SysUtils.isEmpty( attachments ) )
                return Collections.emptyList();
            List<PgcPropertyDTO> list = new ArrayList<PgcPropertyDTO>( attachments.size() );
            for ( PgcProperty a : attachments ) {
                PgcPropertyDTO dto = new PgcPropertyDTO();
                dto.setId( a.getPgp_id_in() );
                dto.setValue( a.getPpg_value_ch() );
                list.add( dto );
            }
            return list;
        }
        else
            return Collections.emptyList();
    }

    public List<PgcPropertyDTO> getGPS( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
    {
        authenticate( auth );
        Pgc entity = pgcSession.get( pgc.getId() );
        if ( entity != null ) {
            List<PgcProperty> attachments = pgcSession.getGPS( entity );
            if ( SysUtils.isEmpty( attachments ) )
                return Collections.emptyList();
            List<PgcPropertyDTO> list = new ArrayList<PgcPropertyDTO>( attachments.size() );
            for ( PgcProperty a : attachments ) {
                PgcPropertyDTO dto = new PgcPropertyDTO();
                dto.setId( a.getPgp_id_in() );
                dto.setValue( a.getPpg_value_ch() );
                list.add( dto );
            }
            return list;
        }
        else
            return Collections.emptyList();
    }


    public void addToPage( AuthenticationDTO auth, PadDTO padDTO, String pageAddress,
                           List<AnotoPageFieldDTO> fields ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPage page = padSession.getPage( new AnotoPagePK( pageAddress, padDTO.getForm().getId(), padDTO.getId() ) );
        if ( page == null )
            return;
        padSession.add( page, fields );
    }

    public List<FieldTypeDTO> getFieldTypes( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return fieldTypeSession.toDTOList( fieldTypeSession.getAll() );
    }

    public void addFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException
    {
        authenticate( auth );
        if ( SysUtils.isEmpty( fields ) )
            return;
        List<AnotoPageField> list = AnotoUtils.toAnotoPageField( fields );
        AnotoPage page = getAnotoPage( fields.get( 0 ).getPage() );
        pageFieldSession.add( page, list );
    }


    public void refreshFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException
    {
        authenticate( auth );
        if ( SysUtils.isEmpty( fields ) )
            return;

        AnotoPage page = getAnotoPage( fields.get( 0 ).getPage() );
        if ( page != null ) {
            List<AnotoPageField> list = AnotoUtils.toAnotoPageField( fields );
            pageFieldSession.refresh( page, list );
        }
    }

    protected AnotoPage getAnotoPage( AnotoPageDTO dto )
    {
        AnotoPage page = getEntityManager().find( AnotoPage.class, new AnotoPagePK( dto ) );
        return page;
    }

    public List<AnotoPageFieldDTO> getFields( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPage page = getAnotoPage( anotoPage );
        if ( page == null )
            return Collections.emptyList();
        return AnotoUtils.toAnotoPageFieldDTO( pageFieldSession.getAll( page ) );
    }

    public void update( AuthenticationDTO auth, AnotoPageFieldDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPageField entity = DTOFactory.copy( dto );
        pageFieldSession.update( entity );
    }


    public void update( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException
    {
        authenticate( auth );

        AnotoPage page = DTOFactory.copy( anotoPage );
        padSession.update( page );
    }
}

