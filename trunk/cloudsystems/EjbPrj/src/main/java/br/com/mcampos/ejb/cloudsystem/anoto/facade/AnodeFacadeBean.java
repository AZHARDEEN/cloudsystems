package br.com.mcampos.ejb.cloudsystem.anoto.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.ApplicationException;
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
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.anoto.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPageUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.session.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session.AnotoPenUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.session.PenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.session.PgcAttachmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.session.PgcPropertySessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.session.FieldTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "AnodeFacade", mappedName = "AnodeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnodeFacadeBean extends AbstractSecurity implements AnodeFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6532257362892303311L;
	private static final Logger LOGGER = LoggerFactory.getLogger( AnodeFacadeBean.class.getSimpleName( ) );

	protected static final int SystemMessageTypeId = 7;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private AnotoFormSessionLocal formSession;
	@EJB
	private FormMediaSessionLocal formMediaSession;
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

	@EJB
	private PgcFieldSessionLocal pgcFieldSession;

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private AnotoPenUserSessionLocal penUserSession;

	@EJB
	private PgcPropertySessionLocal pgcPropertySession;

	@EJB
	private PgcPageAttachmentSessionLocal pgcPageAttachmentSession;

	@EJB
	private PgcAttachmentSessionLocal pgcAttachmentSession;

	@EJB
	private PenPageSessionLocal penPageSession;

	public void addPens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoPen> entities = this.loadPenEntityList( pens );
		AnotoForm aForm = this.formSession.get( form.getId( ) );
		if ( form != null ) {
			this.formSession.add( aForm, entities );
		}
	}

	public void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoPen> entities = this.loadPenEntityList( pens );
		for ( AnotoPen pen : entities ) {
			List<?> list = this.pgcSession.getAll( pen );
			if ( SysUtils.isEmpty( list ) == false ) {
				this.throwRuntimeException( 1 );
			}
		}
		AnotoForm aForm = this.formSession.get( form.getId( ) );
		if ( aForm != null ) {
			this.formSession.remove( aForm, entities );
		}
	}

	@Override
	public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		Company company = this.companySession.get( auth.getCurrentCompany( ) );
		return AnotoUtils.toFormList( this.formSession.getAll( company ) );
	}

	/*
	 * Esta funcao nao possui autenticações pois necessita ser usado no upload
	 * de um pgc, o qual não possui usuário
	 */

	@Override
	public List<PadDTO> getPads( FormDTO form ) throws ApplicationException
	{
		AnotoForm entity = this.formSession.get( form.getId( ) );
		return AnotoUtils.toPadList( this.formSession.getPads( entity ) );
	}

	@Override
	public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm aForm = this.formSession.get( form.getId( ) );
		if ( aForm != null ) {
			return AnotoUtils.toPenList( this.formSession.getAvailablePens( aForm ) );
		}
		else {
			return Collections.emptyList( );
		}
	}

	@Override
	public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm aForm = this.formSession.get( form.getId( ) );
		if ( aForm == null ) {
			return Collections.emptyList( );
		}
		List<PenDTO> pens = AnotoUtils.toPenList( this.formSession.getPens( aForm ) );
		return this.linkToUser( pens );
	}

	@Override
	public List<AnotoPageFieldDTO> getSearchableFields( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm aForm = this.formSession.get( form.getId( ) );
		if ( aForm == null ) {
			return Collections.emptyList( );
		}
		List<AnotoPageField> searchables = this.pageFieldSession.getSearchable( aForm );
		if ( SysUtils.isEmpty( searchables ) ) {
			return Collections.emptyList( );
		}
		return AnotoUtils.toAnotoPageFieldDTO( searchables );
	}

	private List<PenDTO> linkToUser( List<PenDTO> pens ) throws ApplicationException
	{
		if ( SysUtils.isEmpty( pens ) ) {
			return Collections.emptyList( );
		}
		for ( PenDTO pen : pens ) {
			this.linkToUser( pen );
		}
		return pens;
	}

	private PenDTO linkToUser( PenDTO pen ) throws ApplicationException
	{
		if ( pen == null ) {
			return null;
		}
		AnotoPenUser penUser = this.penUserSession.getCurrentUser( pen.getId( ) );
		if ( penUser != null ) {
			ListUserDTO user = UserUtil.copy( penUser.getPerson( ) );
			pen.setUser( user );
		}
		else {
			pen.setUser( null );
		}
		return pen;
	}

	@Override
	protected EntityManager getEntityManager( )
	{
		return this.em;
	}

	@Override
	public Integer getMessageTypeId( )
	{
		return SystemMessageTypeId;
	}

	public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return this.formSession.nextId( );
	}

	public MediaDTO addFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
	{
		this.authenticate( auth );
		Media entity = this.mediaSession.add( MediaUtil.createEntity( media ) );
		AnotoForm anotoForm = this.formSession.get( form.getId( ) );
		return this.formSession.addFile( anotoForm, entity ).getMedia( ).toDTO( );
	}

	public void removeFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
	{
		this.authenticate( auth );
		Media entity = this.mediaSession.add( MediaUtil.createEntity( media ) );
		AnotoForm anotoForm = this.formSession.get( form.getId( ) );
		this.formSession.removeFile( anotoForm, entity );
	}

	public List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm anotoForm = this.formSession.get( form.getId( ) );
		if ( anotoForm == null ) {
			return Collections.emptyList( );
		}
		List<FormMedia> list = this.formSession.getFiles( anotoForm );
		if ( SysUtils.isEmpty( list ) ) {
			return Collections.emptyList( );
		}
		List<Media> medias = new ArrayList<Media>( list.size( ) );
		for ( FormMedia fm : list ) {
			medias.add( fm.getMedia( ) );
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

	/* *************************************************************************
	 * *************************************************************************
	 * 
	 * OPERACAO EM CANETAS
	 * 
	 * *************************************************************************
	 * *************************************************************************
	 */

	@Override
	public byte[ ] getObject( MediaDTO key ) throws ApplicationException
	{
		return this.mediaSession.getObject( key.getId( ) );
	}

	/* *************************************************************************
	 * *************************************************************************
	 * 
	 * OPERACAO EM Páginas
	 * 
	 * *************************************************************************
	 * *************************************************************************
	 */

	@Override
	public List<AnotoPageDTO> getPages( AuthenticationDTO auth, PadDTO pad ) throws ApplicationException
	{
		this.authenticate( auth );
		PadPK key = new PadPK( pad.getFormId( ), pad.getId( ) );
		Pad entity = this.padSession.get( key );

		return AnotoUtils.toPageList( this.padSession.getPages( entity ) );
	}

	@Override
	public List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm entity = this.formSession.get( form.getId( ) );

		return AnotoUtils.toPageList( this.padSession.getPages( entity ) );
	}

	@Override
	public List<AnotoPageDTO> getPages( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPageList( this.padSession.getPages( ) );
	}

	@Override
	public List<MediaDTO> getImages( AnotoPageDTO page ) throws ApplicationException
	{
		return AnotoUtils.toMediaList( this.padSession.getImages( this.getPageEntity( page ) ) );
	}

	@Override
	public MediaDTO removeFromPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPage pageEntity = this.getPageEntity( page );
		return this.padSession.removeImage( pageEntity, this.mediaSession.get( image.getId( ) ) ).toDTO( );
	}

	@Override
	public MediaDTO addToPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException
	{
		this.authenticate( auth );
		Media imageEntity = this.mediaSession.add( MediaUtil.createEntity( image ) );
		return this.padSession.addImage( this.getPageEntity( page ), imageEntity ).toDTO( );
	}

	protected List<AnotoPen> loadPenEntityList( List<PenDTO> pens ) throws ApplicationException
	{
		List<AnotoPen> entities = new ArrayList<AnotoPen>( pens.size( ) );
		for ( PenDTO pen : pens ) {
			entities.add( this.penSession.get( pen.getId( ) ) );
		}
		return entities;
	}

	@Override
	public void removePens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );

		List<AnotoPen> entities = this.loadPenEntityList( pens );
		this.padSession.removePens( this.getPageEntity( page ), entities );
	}

	@Override
	public void addPens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoPen> entities = this.loadPenEntityList( pens );
		this.padSession.addPens( this.getPageEntity( page ), entities );
	}

	@Override
	public List<PenDTO> getAvailablePens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPenList( this.padSession.getAvailablePens( this.getPageEntity( page ) ) );
	}

	protected AnotoPage getPageEntity( AnotoPageDTO page )
	{
		AnotoPagePK key = new AnotoPagePK( page );
		AnotoPage entity = this.padSession.getPage( key );
		return entity;
	}

	@Override
	public List<PenDTO> getPens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPenList( this.padSession.getPens( this.getPageEntity( page ) ) );
	}

	@Override
	public List<PGCDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPgcList( this.pgcSession.getAll( ) );
	}

	@Override
	public List<PGCDTO> getSuspendedPgc( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPgcList( this.pgcSession.getSuspended( ) );
	}

	@Override
	public List<PgcPenPageDTO> get( AuthenticationDTO auth, AnotoPenPageDTO penPage ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPen pen = this.penSession.get( penPage.getPen( ).getId( ) );
		AnotoPage page = this.getPageEntity( penPage.getPage( ) );
		AnotoPenPage entity = this.padSession.getPenPage( pen, page );
		return AnotoUtils.toPgcPenPageList( this.pgcSession.getAll( entity ) );
	}

	@Override
	public List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props, Integer maxRecords, Boolean bNewFirst )
			throws ApplicationException
	{
		this.authenticate( auth );
		LOGGER.info( "getAllPgcPenPage" );
		if ( props != null && props.size( ) > 0 ) {
			/* Trocar o DTO pela entidade */
			Object value;

			value = props.get( "form" );
			if ( value != null ) {
				AnotoForm entity = this.formSession.get( ( (FormDTO) value ).getId( ) );
				if ( entity != null ) {
					props.put( "form", entity );
				}
			}
		}
		List<PgcPage> list = this.pgcPenPageSession.getAll( props, maxRecords, bNewFirst );
		if ( SysUtils.isEmpty( list ) ) {
			LOGGER.info( "List<PgcPage> is empty" );
			return Collections.emptyList( );
		}
		List<AnotoResultList> resultList = new ArrayList<AnotoResultList>( list.size( ) );
		LOGGER.info( "Converting to DTO. List<PgcPage> has " + list.size( ) + " elements " );
		try {
			for ( PgcPage page : list ) {
				AnotoResultList item = new AnotoResultList( );

				if ( page.getPgc( ) == null ) {
					LOGGER.error( "Page has no PGC [" + page.toString( ) + "]" );
					continue;
				}
				if ( page.getPgc( ).getPgcPenPages( ) == null ) {
					LOGGER.error( "PGC has not PenPages [" + page.toString( ) + "]" );
					continue;
				}
				item.setForm( page.getPgc( ).getPgcPenPages( ).get( 0 ).getPenPage( ).getPage( ).getPad( ).getForm( ).toDTO( ) );
				item.setPen( page.getPgc( ).getPgcPenPages( ).get( 0 ).getPenPage( ).getPen( ).toDTO( ) );
				item.setPgcPage( page.toDTO( ) );
				if ( resultList.contains( item ) == false ) {
					this.loadProperties( item, page );
					resultList.add( item );
				}
			}
		}
		catch ( Exception e ) {
			LOGGER.error( "getAllPgcPenPage", e );
		}
		LOGGER.info( "returning a List<AnotoResultList> with " + resultList.size( ) + " Elements" );
		return resultList;
	}

	private void getExportFields( AnotoResultList item, PgcPage page ) throws ApplicationException
	{
		List<AnotoPageField> fields = this.pageFieldSession.getFieldsToExport( );
		if ( SysUtils.isEmpty( fields ) == false ) {
			for ( AnotoPageField field : fields ) {
				PgcFieldPK key = new PgcFieldPK( );
				key.setBookId( page.getBookId( ) );
				key.setPageId( page.getPageId( ) );
				key.setPgcId( page.getPgcId( ) );
				key.setName( field.getName( ) );
				PgcField pgcField = this.pgcFieldSession.get( key );
				if ( pgcField != null ) {
					item.getFields( ).add( pgcField.toDTO( ) );
				}
			}
		}
	}

	private void loadProperties( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
	{
		if ( item == null ) {
			return;
		}
		try {
			this.loadUserData( item );
			List<PgcProperty> prop = this.pgcPropertySession.get( pgcPage.getPgc( ).getId( ), PgcProperty.cellNumber );
			if ( SysUtils.isEmpty( prop ) == false ) {
				item.setCellNumber( prop.get( 0 ).getValue( ) );
			}
			prop = this.pgcPropertySession.getGPS( item.getPgcPage( ).getPgc( ).getId( ) );
			if ( SysUtils.isEmpty( prop ) == false ) {
				item.setLatitude( prop.get( 3 ).getValue( ) );
				item.setLongitude( prop.get( 4 ).getValue( ) );
			}
			this.loadBarCode( item, pgcPage );
			this.loadAttach( item, pgcPage );
			this.getExportFields( item, pgcPage );
		}
		catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	private void loadAttach( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
	{
		List<PgcAttachment> attachs = this.pgcAttachmentSession.get( pgcPage.getPgc( ).getId( ) );
		item.setAttach( SysUtils.isEmpty( attachs ) == false );
	}

	private void loadUserData( AnotoResultList item ) throws ApplicationException
	{
		AnotoPenUser penUser = this.penUserSession.getUser( item.getPen( ).getId( ), item.getPgcPage( ).getPgc( ).getInsertDate( ) );
		if ( penUser != null ) {
			item.setUserName( penUser.getPerson( ).getName( ) );
			if ( SysUtils.isEmpty( penUser.getPerson( ).getDocuments( ) ) == false ) {
				for ( UserDocument doc : penUser.getPerson( ).getDocuments( ) ) {
					if ( doc.getDocumentType( ).getId( ).equals( DocumentTypeDTO.typeEmail ) ) {
						item.setEmail( doc.getCode( ) );
						break;
					}
				}
			}
		}

	}

	private void loadBarCode( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
	{
		List<PgcPageAttachment> list = this.pgcPageAttachmentSession.getAll( pgcPage );
		if ( SysUtils.isEmpty( list ) == false ) {
			for ( PgcPageAttachment attach : list ) {
				if ( attach.getType( ).equals( PgcAttachmentDTO.typeBarCode ) ) {
					item.setBarcodeValue( attach.getValue( ) );
					break;
				}
			}
		}
	}

	protected boolean attachPenPage( List<AnotoPage> pages, AnotoPen pen, Pgc pgc ) throws ApplicationException
	{
		AnotoPenPage item;

		for ( AnotoPage page : pages ) {
			item = this.penPageSession.get( page, pen );
			if ( item != null ) {
				this.pgcSession.attach( pgc, item );
			}
		}
		return true;
	}

	@Override
	public List<AnotoPenPageDTO> getPenPages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoPenPage> list = this.pgcSession.get( this.getPageEntity( page ) );
		return AnotoUtils.toPenPageList( list );
	}

	@Override
	public void delete( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
	{
		this.authenticate( auth );
		Pgc entity = this.pgcSession.get( pgc.getId( ) );
		if ( entity != null ) {
			this.pgcPenPageSession.delete( entity );
			this.pgcSession.delete( pgc.getId( ) );
		}
	}

	@Override
	public List<MediaDTO> getImages( PgcPageDTO page ) throws ApplicationException
	{
		return AnotoUtils.toMediaList( this.pgcSession.getImages( PgcPageUtil.createEntity( page ) ) );
	}

	@Override
	public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
	{
		this.authenticate( auth );
		List<PgcField> fields = this.pgcSession.getFields( PgcPageUtil.createEntity( page ) );
		if ( SysUtils.isEmpty( fields ) ) {
			return Collections.emptyList( );
		}
		return AnotoUtils.toPgcFieldList( fields );
	}

	@Override
	public void update( AuthenticationDTO auth, PgcFieldDTO field ) throws ApplicationException
	{
		this.authenticate( auth );
		PgcField pgcField = this.pgcFieldSession.get( new PgcFieldPK( field ) );
		if ( pgcField != null ) {
			PgcFieldUtil.update( pgcField, field );
			this.pgcFieldSession.update( pgcField );
		}
		else {
			pgcField = PgcFieldUtil.createEntity( field );
			this.pgcFieldSession.add( pgcField );
		}
	}

	@Override
	public Integer remove( AuthenticationDTO auth, AnotoResultList item ) throws ApplicationException
	{
		this.authenticate( auth );
		return this.pgcSession.remove( item );
	}

	@Override
	public List<PgcAttachmentDTO> getAttachments( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
	{
		this.authenticate( auth );
		return AnotoUtils.toPgcAttachmentList( this.pgcSession.getAttachments( PgcPageUtil.createEntity( page ) ) );
	}

	@Override
	public List<MediaDTO> getAttachments( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
	{
		this.authenticate( auth );
		Pgc entity = this.pgcSession.get( pgc.getId( ) );
		if ( entity != null ) {
			List<PgcAttachment> attachments = this.pgcSession.getAttachments( entity );
			if ( SysUtils.isEmpty( attachments ) ) {
				return Collections.emptyList( );
			}
			List<MediaDTO> medias = new ArrayList<MediaDTO>( attachments.size( ) );
			for ( PgcAttachment a : attachments ) {
				medias.add( this.mediaSession.get( a.getMediaId( ) ).toDTO( ) );
			}
			return medias;
		}
		else {
			return Collections.emptyList( );
		}
	}

	@Override
	public List<PgcPropertyDTO> getProperties( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
	{
		this.authenticate( auth );
		Pgc entity = this.pgcSession.get( pgc.getId( ) );
		if ( entity != null ) {
			List<PgcProperty> attachments = this.pgcSession.getProperties( entity );
			if ( SysUtils.isEmpty( attachments ) ) {
				return Collections.emptyList( );
			}
			List<PgcPropertyDTO> list = new ArrayList<PgcPropertyDTO>( attachments.size( ) );
			for ( PgcProperty a : attachments ) {
				PgcPropertyDTO dto = new PgcPropertyDTO( );
				dto.setId( a.getId( ) );
				dto.setValue( a.getValue( ) );
				list.add( dto );
			}
			return list;
		}
		else {
			return Collections.emptyList( );
		}
	}

	@Override
	public List<PgcPropertyDTO> getGPS( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException
	{
		this.authenticate( auth );
		Pgc entity = this.pgcSession.get( pgc.getId( ) );
		if ( entity != null ) {
			List<PgcProperty> attachments = this.pgcSession.getGPS( entity );
			if ( SysUtils.isEmpty( attachments ) ) {
				return Collections.emptyList( );
			}
			List<PgcPropertyDTO> list = new ArrayList<PgcPropertyDTO>( attachments.size( ) );
			for ( PgcProperty a : attachments ) {
				PgcPropertyDTO dto = new PgcPropertyDTO( );
				dto.setId( a.getId( ) );
				dto.setValue( a.getValue( ) );
				list.add( dto );
			}
			return list;
		}
		else {
			return Collections.emptyList( );
		}
	}

	@Override
	public void addToPage( AuthenticationDTO auth, PadDTO padDTO, String pageAddress, List<AnotoPageFieldDTO> fields ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPage page = this.padSession.getPage( new AnotoPagePK( pageAddress, padDTO.getForm( ).getId( ), padDTO.getId( ) ) );
		if ( page == null ) {
			return;
		}
		this.padSession.add( page, fields );
	}

	@Override
	public List<FieldTypeDTO> getFieldTypes( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return this.fieldTypeSession.toDTOList( this.fieldTypeSession.getAll( ) );
	}

	@Override
	public void addFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException
	{
		this.authenticate( auth );
		if ( SysUtils.isEmpty( fields ) ) {
			return;
		}
		List<AnotoPageField> list = AnotoUtils.toAnotoPageField( fields );
		AnotoPage page = this.getAnotoPage( fields.get( 0 ).getPage( ) );
		this.pageFieldSession.add( page, list );
	}

	@Override
	public void refreshFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException
	{
		this.authenticate( auth );
		if ( SysUtils.isEmpty( fields ) ) {
			return;
		}

		AnotoPage page = this.getAnotoPage( fields.get( 0 ).getPage( ) );
		if ( page != null ) {
			List<AnotoPageField> list = AnotoUtils.toAnotoPageField( fields );
			this.pageFieldSession.refresh( page, list );
		}
	}

	protected AnotoPage getAnotoPage( AnotoPageDTO dto )
	{
		AnotoPage page = this.getEntityManager( ).find( AnotoPage.class, new AnotoPagePK( dto ) );
		return page;
	}

	@Override
	public List<AnotoPageFieldDTO> getFields( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPage page = this.getAnotoPage( anotoPage );
		if ( page == null ) {
			return Collections.emptyList( );
		}
		return AnotoUtils.toAnotoPageFieldDTO( this.pageFieldSession.getAll( page ) );
	}

	@Override
	public void update( AuthenticationDTO auth, AnotoPageFieldDTO dto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPageField entity = DTOFactory.copy( dto );
		this.pageFieldSession.update( entity );
	}

	@Override
	public void update( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException
	{
		this.authenticate( auth );

		AnotoPage page = AnotoPageUtil.createEntity( anotoPage );
		this.padSession.update( page );
	}

	@Override
	public byte[ ] getPDFTemplate( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );

		AnotoForm eForm = this.formSession.get( form.getId( ) );
		if ( eForm == null ) {
			return null;
		}
		FormMedia formMedia = this.formMediaSession.getPDFTemplate( eForm );
		if ( formMedia == null ) {
			return null;
		}
		return this.mediaSession.getObject( formMedia.getMediaId( ) );
	}
}
