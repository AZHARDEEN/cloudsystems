package br.com.mcampos.ejb.cloudsystem.anoto.form;


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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoFormFacade", mappedName = "AnotoFormFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnotoFormFacadeBean extends AbstractSecurity implements AnotoFormFacade
{
    /**
     *
     */
    @SuppressWarnings( "compatibility:4186745611920073784" )
    private static final long serialVersionUID = 563473291692369258L;

    public static final Integer messageId = 9;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private AnotoFormSessionLocal formSession;
    @EJB
    private AnodePenSessionLocal penSession;
    @EJB
    private MediaSessionLocal mediaSession;
    @EJB
    private PGCSessionLocal pgcSession;
    @EJB
    private PadSessionLocal padSession;
    @EJB
    private FormMediaSessionLocal formMediaSession;
    @EJB
    private CompanySessionLocal companySession;
    @EJB
    private AnotoFormUserSessionLocal formUserSession;
    @EJB
    private ClientSessionLocal clientSession;
    @EJB
    private PgcPageSessionLocal pgcPageSession;
    @EJB
    private PgcFieldSessionLocal pgcFieldSession;

    public AnotoFormFacadeBean()
    {
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    @Override
    public Integer getMessageTypeId()
    {
        return messageId;
    }

    @Override
    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null )
            throwCommomException( 3 );
        Company company = companySession.get( auth.getCurrentCompany() );
        return formSession.add( AnotoFormUtil.createEntity( entity ), company ).toDTO();

    }

    private AnotoForm getExistent( FormDTO dto ) throws ApplicationException
    {
        AnotoForm form = formSession.get( dto.getId() );
        if ( form == null )
            throwException( 6 );
        return form;
    }

    @Override
    public void addPens( AuthenticationDTO auth, FormDTO formDto, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPen> entities = loadPenEntityList( pens );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        formSession.add( form, entities );
    }

    private void belongsToCompany( Integer formId, Integer companyId ) throws ApplicationException
    {
        AnotoFormUser formUser = formUserSession.get( formId, companyId );
        if ( formUser == null )
            throwRuntimeException( 7 );
    }

    protected List<AnotoPen> loadPenEntityList( List<PenDTO> pens ) throws ApplicationException
    {
        List<AnotoPen> entities = new ArrayList<AnotoPen>( pens.size() );
        for ( PenDTO pen : pens ) {
            entities.add( penSession.get( pen.getId() ) );
        }
        return entities;
    }

    @Override
    public void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        List<AnotoPen> entities = loadPenEntityList( pens );
        for ( AnotoPen pen : entities ) {
            List<?> list = pgcSession.getAll( pen );
            if ( SysUtils.isEmpty( list ) == false )
                throwRuntimeException( 1 );
        }
        formSession.remove( getExistent( form ), entities );
    }

    @Override
    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( entity );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        formSession.delete( entity.getId() );
    }

    @Override
    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( entity );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        return ( form != null ? form.toDTO() : null );
    }

    @Override
    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        return AnotoUtils.toFormList( formSession.getAll( company ) );
    }

    @Override
    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( entity );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        AnotoFormUtil.update( form, entity );
        return formSession.update( form ).toDTO();
    }

    @Override
    public PadDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad, List<String> pages, Boolean bUnique ) throws ApplicationException
    {
        authenticate( auth );
        /*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
        AnotoForm form = getExistent( entity );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        pad.setFormat( "pad" );
        Media media = mediaSession.add( MediaUtil.createEntity( pad ) );
        Pad padentity = formSession.addPadFile( form, media, pages, bUnique );
        return padentity.toDTO();
    }

    @Override
    public MediaDTO removePad( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        /*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
        AnotoForm form = getExistent( entity );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        Media media = mediaSession.get( pad.getId() );
        return formSession.removePadFile( form, media ).toDTO();
    }

    @Override
    public List<PadDTO> getPads( FormDTO form ) throws ApplicationException
    {
        AnotoForm entity = formSession.get( form.getId() );
        return AnotoUtils.toPadList( formSession.getPads( entity ) );
    }

    @Override
    public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        return AnotoUtils.toPenList( formSession.getAvailablePens( form ) );
    }

    @Override
    public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        return AnotoUtils.toPenList( formSession.getPens( form ) );
    }

    @Override
    public MediaDTO addFile( AuthenticationDTO auth, FormDTO formDto, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        Media entity = mediaSession.add( MediaUtil.createEntity( media ) );
        return formSession.addFile( form, entity ).getMedia().toDTO();
    }

    @Override
    public void removeFile( AuthenticationDTO auth, FormDTO formDto, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        Media entity = mediaSession.add( MediaUtil.createEntity( media ) );
        formSession.removeFile( form, entity );
    }

    @Override
    public List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        List<FormMedia> list = formSession.getFiles( form );
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<Media> medias = new ArrayList<Media>( list.size() );
        for ( FormMedia fm : list ) {
            medias.add( fm.getMedia() );
        }
        return AnotoUtils.toMediaList( medias );
    }

    public List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        return AnotoUtils.toPageList( padSession.getPages( form ) );
    }

    @Override
    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.nextId();
    }

    @Override
    public byte[] getObject( MediaDTO key ) throws ApplicationException
    {
        return mediaSession.getObject( key.getId() );
    }

    @Override
    public void addToPage( AuthenticationDTO auth, PadDTO padDTO, String pageAddress, List<AnotoPageFieldDTO> fields ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPage page = padSession.getPage( new AnotoPagePK( pageAddress, padDTO.getForm().getId(), padDTO.getId() ) );
        if ( page == null )
            return;
        padSession.add( page, fields );
    }

    @Override
    public MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO formDto, MediaDTO pad ) throws ApplicationException
    {
        authenticate( auth );
        /*
		 * As etapas para adicionar um pad: 1) Verificar a existência do
		 * formulário. 2) Inserir a mídia. 3) Vincular o formulário à midia
		 */
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        Media media = mediaSession.get( pad.getId() );
        return formSession.removePadFile( form, media ).toDTO();
    }

    @Override
    public void addMedias( AuthenticationDTO auth, FormDTO formDto, MediaDTO[] medias ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        for ( int nIndex = 0; nIndex < medias.length; nIndex++ ) {
            Media newMedia = mediaSession.add( MediaUtil.createEntity( medias[ nIndex ] ) );
            if ( formMediaSession.get( form, newMedia ) == null )
                formMediaSession.add( form, newMedia );
        }
    }

    @Override
    public void removeMedias( AuthenticationDTO auth, FormDTO formDto, MediaDTO[] medias ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        for ( int nIndex = 0; nIndex < medias.length; nIndex++ ) {
            Media media = mediaSession.get( medias[ nIndex ].getId() );
            if ( media != null ) {
                formMediaSession.delete( form, media );
            }
        }
    }

    @Override
    public List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO formDto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        List<FormMedia> formMedias = formMediaSession.get( form );
        return AnotoUtils.toMediaListFromFormMedia( formMedias );
    }

    @Override
    public ListUserDTO getCompany( AuthenticationDTO auth, FormDTO formDto, Integer clientId ) throws ApplicationException
    {
        Company clientCompany = getClient( auth, clientId );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        if ( clientCompany == null )
            return null;
        return UserUtil.copy( clientCompany );
    }

    @Override
    public List<ListUserDTO> getCompanies( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoFormUser> formUsers = formUserSession.get( form.getId() );
        return AnotoFormUtil.toListUserDTO( formUsers );
    }

    @Override
    public void addCompany( AuthenticationDTO auth, FormDTO formDto, ListUserDTO dto ) throws ApplicationException
    {
        Company clientCompany = getClient( auth, dto.getId() );
        AnotoForm form = getExistent( formDto );
        belongsToCompany( form.getId(), auth.getCurrentCompany() );
        AnotoFormUser formUser = new AnotoFormUser();
        formUser.setForm( form );
        formUser.setCompany( clientCompany );
        formUserSession.add( formUser );
    }

    @Override
    public void deleteCompany( AuthenticationDTO auth, FormDTO formDto, ListUserDTO dto ) throws ApplicationException
    {
        if ( dto.getId().equals( auth.getCurrentCompany() ) == false )
            formUserSession.delete( formDto.getId(), dto.getId() );
    }

    private Company getClient( AuthenticationDTO auth, Integer clientId ) throws ApplicationException
    {
        authenticate( auth );
        Company myCompany = companySession.get( auth.getCurrentCompany() );
        if ( myCompany == null )
            return null;
        Company clientCompany = companySession.get( clientId );
        if ( clientCompany == null )
            return null;
        Client client = clientSession.get( myCompany, clientCompany );
        if ( client != null )
            return ( Company )client.getClient();
        else
            return null;
    }
}
