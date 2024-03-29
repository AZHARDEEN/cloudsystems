package br.com.mcampos.ejb.cloudsystem.anoto.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.anoto.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.session.AnotoFormUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "AnotoFormFacade", mappedName = "AnotoFormFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnotoFormFacadeBean extends AbstractSecurity implements AnotoFormFacade
{
	/**
     *
     */
	private static final long			serialVersionUID	= 563473291692369258L;

	public static final Integer			messageId			= 9;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager		em;

	@EJB
	private AnotoFormSessionLocal		formSession;
	@EJB
	private AnodePenSessionLocal		penSession;
	@EJB
	private MediaSessionLocal			mediaSession;
	@EJB
	private PGCSessionLocal				pgcSession;
	@EJB
	private PadSessionLocal				padSession;
	@EJB
	private FormMediaSessionLocal		formMediaSession;
	@EJB
	private CompanySessionLocal			companySession;
	@EJB
	private AnotoFormUserSessionLocal	formUserSession;
	@EJB
	private ClientSessionLocal			clientSession;
	@EJB
	private PgcPageSessionLocal			pgcPageSession;
	@EJB
	private PgcFieldSessionLocal		pgcFieldSession;

	public AnotoFormFacadeBean( )
	{
	}

	@Override
	protected EntityManager getEntityManager( )
	{
		return this.em;
	}

	@Override
	public Integer getMessageTypeId( )
	{
		return messageId;
	}

	@Override
	public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
	{
		this.authenticate( auth );
		if( entity == null ) {
			this.throwCommomException( 3 );
		}
		Company company = this.companySession.get( auth.getCurrentCompany( ) );
		return this.formSession.add( AnotoFormUtil.createEntity( entity ), company ).toDTO( );

	}

	private AnotoForm getExistent( FormDTO dto ) throws ApplicationException
	{
		AnotoForm form = this.formSession.get( dto.getId( ) );
		if( form == null ) {
			this.throwException( 6 );
		}
		return form;
	}

	@Override
	public void addPens( AuthenticationDTO auth, FormDTO formDto, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoPen> entities = this.loadPenEntityList( pens );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		this.formSession.add( form, entities );
	}

	private void belongsToCompany( Integer formId, Integer companyId ) throws ApplicationException
	{
		AnotoFormUser formUser = this.formUserSession.get( formId, companyId );
		if( formUser == null ) {
			this.throwRuntimeException( 7 );
		}
	}

	protected List<AnotoPen> loadPenEntityList( List<PenDTO> pens ) throws ApplicationException
	{
		List<AnotoPen> entities = new ArrayList<AnotoPen>( pens.size( ) );
		for( PenDTO pen : pens ) {
			entities.add( this.penSession.get( pen.getId( ) ) );
		}
		return entities;
	}

	@Override
	public void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
	{
		this.authenticate( auth );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		List<AnotoPen> entities = this.loadPenEntityList( pens );
		for( AnotoPen pen : entities ) {
			List<?> list = this.pgcSession.getAll( pen );
			if( SysUtils.isEmpty( list ) == false ) {
				this.throwRuntimeException( 1 );
			}
		}
		this.formSession.remove( this.getExistent( form ), entities );
	}

	@Override
	public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( entity );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		this.formSession.delete( entity.getId( ) );
	}

	@Override
	public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( entity );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		return(form != null ? form.toDTO( ) : null);
	}

	@Override
	public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		Company company = this.companySession.get( auth.getCurrentCompany( ) );
		return AnotoUtils.toFormList( this.formSession.getAll( company ) );
	}

	@Override
	public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( entity );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		AnotoFormUtil.update( form, entity );
		return this.formSession.update( form ).toDTO( );
	}

	@Override
	public PadDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad, List<String> pages, Boolean bUnique ) throws ApplicationException
	{
		this.authenticate( auth );
		/*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
		AnotoForm form = this.getExistent( entity );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		pad.setFormat( "pad" );
		Media media = this.mediaSession.add( MediaUtil.createEntity( pad ) );
		Pad padentity = this.formSession.addPadFile( form, media, pages, bUnique );
		return padentity.toDTO( );
	}

	@Override
	public MediaDTO removePad( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException
	{
		this.authenticate( auth );
		/*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
		AnotoForm form = this.getExistent( entity );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		Media media = this.mediaSession.get( pad.getId( ) );
		return this.formSession.removePadFile( form, media ).toDTO( );
	}

	@Override
	public List<PadDTO> getPads( FormDTO form ) throws ApplicationException
	{
		AnotoForm entity = this.formSession.get( form.getId( ) );
		return AnotoUtils.toPadList( this.formSession.getPads( entity ) );
	}

	@Override
	public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		return AnotoUtils.toPenList( this.formSession.getAvailablePens( form ) );
	}

	@Override
	public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		return AnotoUtils.toPenList( this.formSession.getPens( form ) );
	}

	@Override
	public MediaDTO addFile( AuthenticationDTO auth, FormDTO formDto, MediaDTO media ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		Media entity = this.mediaSession.add( MediaUtil.createEntity( media ) );
		return this.formSession.addFile( form, entity ).getMedia( ).toDTO( );
	}

	@Override
	public void removeFile( AuthenticationDTO auth, FormDTO formDto, MediaDTO media ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		Media entity = this.mediaSession.add( MediaUtil.createEntity( media ) );
		this.formSession.removeFile( form, entity );
	}

	@Override
	public List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		List<FormMedia> list = this.formSession.getFiles( form );
		if( SysUtils.isEmpty( list ) ) {
			return Collections.emptyList( );
		}
		List<Media> medias = new ArrayList<Media>( list.size( ) );
		for( FormMedia fm : list ) {
			medias.add( fm.getMedia( ) );
		}
		return AnotoUtils.toMediaList( medias );
	}

	public List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		return AnotoUtils.toPageList( this.padSession.getPages( form ) );
	}

	@Override
	public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
	{
		this.authenticate( auth );
		return this.formSession.nextId( );
	}

	@Override
	public byte[ ] getObject( MediaDTO key ) throws ApplicationException
	{
		return this.mediaSession.getObject( key.getId( ) );
	}

	@Override
	public void addToPage( AuthenticationDTO auth, PadDTO padDTO, String pageAddress, List<AnotoPageFieldDTO> fields ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoPage page = this.padSession.getPage( new AnotoPagePK( pageAddress, padDTO.getForm( ).getId( ), padDTO.getId( ) ) );
		if( page == null ) {
			return;
		}
		this.padSession.add( page, fields );
	}

	@Override
	public MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO formDto, MediaDTO pad ) throws ApplicationException
	{
		this.authenticate( auth );
		/*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		Media media = this.mediaSession.get( pad.getId( ) );
		return this.formSession.removePadFile( form, media ).toDTO( );
	}

	@Override
	public void addMedias( AuthenticationDTO auth, FormDTO formDto, MediaDTO[ ] medias ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		for( MediaDTO media : medias ) {
			Media newMedia = this.mediaSession.add( MediaUtil.createEntity( media ) );
			if( this.formMediaSession.get( form, newMedia ) == null ) {
				this.formMediaSession.add( form, newMedia );
			}
		}
	}

	@Override
	public void removeMedias( AuthenticationDTO auth, FormDTO formDto, MediaDTO[ ] medias ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		for( MediaDTO media2 : medias ) {
			Media media = this.mediaSession.get( media2.getId( ) );
			if( media != null ) {
				this.formMediaSession.delete( form, media );
			}
		}
	}

	@Override
	public List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
	{
		this.authenticate( auth );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		List<FormMedia> formMedias = this.formMediaSession.get( form );
		return AnotoUtils.toMediaListFromFormMedia( formMedias );
	}

	@Override
	public ListUserDTO getCompany( AuthenticationDTO auth, FormDTO formDto, Integer clientId ) throws ApplicationException
	{
		Company clientCompany = this.getClient( auth, clientId );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		if( clientCompany == null ) {
			return null;
		}
		return UserUtil.copy( clientCompany );
	}

	@Override
	public List<ListUserDTO> getCompanies( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
	{
		this.authenticate( auth );
		List<AnotoFormUser> formUsers = this.formUserSession.get( form.getId( ) );
		return AnotoFormUtil.toListUserDTO( formUsers );
	}

	@Override
	public void addCompany( AuthenticationDTO auth, FormDTO formDto, ListUserDTO dto ) throws ApplicationException
	{
		Company clientCompany = this.getClient( auth, dto.getId( ) );
		AnotoForm form = this.getExistent( formDto );
		this.belongsToCompany( form.getId( ), auth.getCurrentCompany( ) );
		AnotoFormUser formUser = new AnotoFormUser( );
		formUser.setForm( form );
		formUser.setCompany( clientCompany );
		this.formUserSession.add( formUser );
	}

	@Override
	public void deleteCompany( AuthenticationDTO auth, FormDTO formDto, ListUserDTO dto ) throws ApplicationException
	{
		if( dto.getId( ).equals( auth.getCurrentCompany( ) ) == false ) {
			this.formUserSession.delete( formDto.getId( ), dto.getId( ) );
		}
	}

	private Company getClient( AuthenticationDTO auth, Integer clientId ) throws ApplicationException
	{
		this.authenticate( auth );
		Company myCompany = this.companySession.get( auth.getCurrentCompany( ) );
		if( myCompany == null ) {
			return null;
		}
		Company clientCompany = this.companySession.get( clientId );
		if( clientCompany == null ) {
			return null;
		}
		Client client = this.clientSession.get( myCompany, clientCompany );
		if( client != null ) {
			return (Company) client.getClient( );
		}
		else {
			return null;
		}
	}
}
