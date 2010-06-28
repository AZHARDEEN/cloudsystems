package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
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
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
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


@Stateless( name = "AnotoFormFacade", mappedName = "CloudSystems-EjbPrj-AnotoFormFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AnotoFormFacadeBean extends AbstractSecurity implements AnotoFormFacade
{
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


    public AnotoFormFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }


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

    public void addPens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoPen> entities = loadPenEntityList( pens );
        formSession.add( getExistent( form ), entities );
    }


    protected List<AnotoPen> loadPenEntityList( List<PenDTO> pens ) throws ApplicationException
    {
        List<AnotoPen> entities = new ArrayList<AnotoPen>( pens.size() );
        for ( PenDTO pen : pens ) {
            entities.add( penSession.get( pen.getId() ) );
        }
        return entities;
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
        formSession.remove( getExistent( form ), entities );
    }

    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null )
            throwCommomException( 3 );
        AnotoForm form = formSession.get( entity.getId() );
        if ( form == null )
            throwException( 4 );
        formSession.delete( entity.getId() );
    }

    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        if ( entity == null || SysUtils.isZero( entity.getId() ) )
            throwCommomException( 3 );
        AnotoForm form = formSession.get( entity.getId() );
        return ( form != null ? form.toDTO() : null );
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toFormList( formSession.getAll() );
    }

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = getExistent( entity );
        AnotoFormUtil.update( form, entity );
        return formSession.update( form ).toDTO();
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
        Media media = mediaSession.add( MediaUtil.createEntity( pad ) );
        Pad padentity = formSession.addPadFile( form, media, pages );
        return padentity.toDTO();
    }

    public MediaDTO removePad( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException
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

    public List<PadDTO> getPads( FormDTO form ) throws ApplicationException
    {
        AnotoForm entity = formSession.get( form.getId() );
        return AnotoUtils.toPadList( formSession.getPads( entity ) );
    }

    public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPenList( formSession.getAvailablePens( getExistent( form ) ) );
    }

    public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        return AnotoUtils.toPenList( formSession.getPens( getExistent( form ) ) );
    }

    public MediaDTO addFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media entity = mediaSession.add( MediaUtil.createEntity( media ) );
        AnotoForm anotoForm = formSession.get( form.getId() );
        return formSession.addFile( anotoForm, entity ).getMedia().toDTO();
    }


    public void removeFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media entity = mediaSession.add( MediaUtil.createEntity( media ) );
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

    public List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm entity = formSession.get( form.getId() );

        return AnotoUtils.toPageList( padSession.getPages( entity ) );
    }

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.nextId();
    }

    public byte[] getObject( MediaDTO key ) throws ApplicationException
    {
        return mediaSession.getObject( key.getId() );
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


    public void addMedias( AuthenticationDTO auth, FormDTO form, MediaDTO[] medias ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm formEntity = formSession.get( form.getId() );
        if ( formEntity == null )
            throwException( 1 );
        for ( int nIndex = 0; nIndex < medias.length; nIndex++ ) {
            Media newMedia = mediaSession.add( MediaUtil.createEntity( medias[ nIndex ] ) );
            if ( formMediaSession.get( formEntity, newMedia ) == null )
                formMediaSession.add( formEntity, newMedia );
        }
        getEntityManager().refresh( formEntity );
    }

    public void removeMedias( AuthenticationDTO auth, FormDTO form, MediaDTO[] medias ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm formEntity = formSession.get( form.getId() );
        if ( formEntity == null )
            throwException( 1 );
        for ( int nIndex = 0; nIndex < medias.length; nIndex++ ) {
            Media media = mediaSession.get( medias[ nIndex ].getId() );
            if ( media != null ) {
                formMediaSession.delete( formEntity, media );
            }
        }
        getEntityManager().refresh( formEntity );
    }


    public List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm formEntity = formSession.get( form.getId() );
        if ( formEntity == null )
            throwException( 1 );
        List<FormMedia> formMedias = formMediaSession.get( formEntity );
        return AnotoUtils.toMediaListFromFormMedia( formMedias );
    }

    public ListUserDTO getCompany( AuthenticationDTO auth, FormDTO form, Integer clientId ) throws ApplicationException
    {
        Company clientCompany = getClient( auth, clientId );
        if ( clientCompany == null )
            return null;
        return UserUtil.copy( clientCompany );
    }

    public List<ListUserDTO> getCompanies( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        List<AnotoFormUser> formUsers = formUserSession.get( form.getId() );
        return AnotoFormUtil.toListUserDTO( formUsers );
    }

    public void addCompany( AuthenticationDTO auth, FormDTO form, ListUserDTO dto ) throws ApplicationException
    {
        Company clientCompany = getClient( auth, dto.getId() );
        AnotoFormUser formUser = new AnotoFormUser();
        formUser.setForm( formSession.get( form.getId() ) );
        formUser.setCompany( clientCompany );
        formUserSession.add( formUser );
    }

    public void deleteCompany( AuthenticationDTO auth, FormDTO formDto, ListUserDTO dto ) throws ApplicationException
    {
        Company clientCompany = getClient( auth, dto.getId() );
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
        return ( Company )client.getClient();
    }
}


