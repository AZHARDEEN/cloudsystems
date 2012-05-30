package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMediaPK;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.AnotoFormUserUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.session.AnotoFormUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.PadSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.session.AnotoPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.session.PenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.exception.ApplicationRuntimeException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "AnodeFormSession", mappedName = "CloudSystems-EjbPrj-AnodeFormSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AnotoFormSessionBean extends Crud<Integer, AnotoForm> implements AnotoFormSessionLocal
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1935312146768416708L;

	@EJB
    private AnotoFormUserSessionLocal formUserSession;

    @EJB
    private PadSessionLocal padSession;

    @EJB
    private MediaSessionLocal mediaSession;

    @EJB
    private AnotoPageSessionLocal anotoPageSession;

    @EJB
    private PenPageSessionLocal penPageSession;


    public AnotoFormSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( AnotoForm.class, key );
        getEntityManager().flush();
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AnotoForm get( Integer key ) throws ApplicationException
    {
        return get( AnotoForm.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoForm> getAll( Company company ) throws ApplicationException
    {
        List<AnotoFormUser> list = formUserSession.get( company );
        return AnotoFormUserUtil.toListAnotoForm( list );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( "Form.nextId" );
    }

    @Override
    public AnotoForm add( AnotoForm entity, Company company ) throws ApplicationException
    {
        entity.setInsertDate( new Date() );
        entity = super.add( entity );
        if ( entity != null && company != null ) {
            AnotoFormUser formUser = new AnotoFormUser();
            formUser.setForm( entity );
            formUser.setCompany( company );
            formUserSession.add( formUser );
        }
        return entity;
    }

    public Pad addPadFile( AnotoForm form, Media media, List<String> pages, Boolean bUnique ) throws ApplicationException
    {
        form = get( form.getId() );
        media = mediaSession.get( media.getId() );
        Pad newEntity = new Pad( form, media );
        newEntity.setUnique( bUnique );
        newEntity.setInsertDate( new Date() );
        newEntity = padSession.add( newEntity );
        loadPadFile( newEntity, pages );
        return newEntity;
    }


    public FormMedia addFile( AnotoForm form, Media media ) throws ApplicationException
    {
        form = getEntityManager().merge( form );
        media = getEntityManager().merge( media );

        FormMedia newEntity = new FormMedia( form, media );
        getEntityManager().persist( newEntity );
        return newEntity;
    }


    public void removeFile( AnotoForm form, Media media ) throws ApplicationException
    {
        FormMediaPK pk = new FormMediaPK( form, media );

        FormMedia newEntity = getEntityManager().find( FormMedia.class, pk );
        if ( newEntity != null )
            getEntityManager().remove( newEntity );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FormMedia> getFiles( AnotoForm form ) throws ApplicationException
    {
        @SuppressWarnings("unchecked")
		List<FormMedia> list = ( List<FormMedia> )getResultList( FormMedia.formGetFiles, form.getId() );
        return list;
    }


    protected void loadPadFile( Pad pad, List<String> pages ) throws ApplicationException
    {
        AnotoPage page;
        try {
            for ( String address : pages ) {
                page = new AnotoPage( pad, address );
                anotoPageSession.add( page );
            }
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao carregar o arquivo PAD", e );
        }
    }


    public Media removePadFile( AnotoForm form, Media pad ) throws ApplicationException
    {
        PadPK padKey = new PadPK( form.getId(), pad.getId() );
        Pad removeEntity = getEntityManager().find( Pad.class, padKey );
        getEntityManager().remove( removeEntity );
        return pad;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Pad> getPads( AnotoForm form ) throws ApplicationException
    {
        @SuppressWarnings("unchecked")
		List<Pad> padList = ( List<Pad> )getResultList( Pad.padFindAllNamedQuery, form.getApplication() );
        return padList;
    }

    @Override
    public AnotoForm update( AnotoForm entity ) throws ApplicationException
    {
        AnotoForm form = get( entity.getId() );
        entity.setInsertDate( form.getInsertDate() );
        return super.update( entity );
    }


    @SuppressWarnings("unchecked")
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getAvailablePens( AnotoForm form ) throws ApplicationException
    {
        Query query;
        List<AnotoPen> list;

        try {
            query = getEntityManager().createNamedQuery( AnotoPen.formAvailablePens );
            query.setParameter( 1, form.getId() );
            list = ( List<AnotoPen> )query.getResultList();
        }
        catch ( NoResultException e ) {
            e = null;
            list = Collections.emptyList();
        }
        return list;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getPens( AnotoForm form ) throws ApplicationException
    {
        @SuppressWarnings("unchecked")
		List<AnotoPenPage> list = ( List<AnotoPenPage> )getResultList( AnotoPenPage.formPensQueryName, form );
        return AnotoUtils.getPenListFromPenPage( list, true );
    }


    public void add( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
    {
        @SuppressWarnings("unchecked")
		List<AnotoPage> list = ( List<AnotoPage> )getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( AnotoPen pen : pens ) {
            for ( AnotoPage page : list ) {
                AnotoPenPage penPage = penPageSession.get( pen, page.getPageAddress() );
                if ( penPage != null ) {
                    throw new ApplicationRuntimeException( "A caneta encontra associada a outra malha com mesmo IP" );
                }
                penPageSession.add( page, pen );
            }
        }
    }

    protected boolean existsPgcPenPage( AnotoPenPage penPage ) throws ApplicationException
    {
        List<?> list = getResultList( PgcPenPage.getAllPgcQueryName, penPage );
        return SysUtils.isEmpty( list ) ? false : true;
    }


    public void remove( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
    {
        @SuppressWarnings("unchecked")
		List<AnotoPage> list = ( List<AnotoPage> )getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( AnotoPen pen : pens ) {
            for ( AnotoPage page : list ) {
                penPageSession.delete( page, pen );
            }
        }
    }
}
