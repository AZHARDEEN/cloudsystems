package br.com.mcampos.ejb.cloudsystem.anoto.form;

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

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.ApplicationRuntimeException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.AnotoUtils;
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
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "AnodeFormSession", mappedName = "AnodeFormSession" )
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

	public AnotoFormSessionBean( )
	{
	}

	@Override
	public void delete( Integer key ) throws ApplicationException
	{
		this.delete( AnotoForm.class, key );
		this.getEntityManager( ).flush( );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoForm get( Integer key ) throws ApplicationException
	{
		return this.get( AnotoForm.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoForm> getAll( Company company ) throws ApplicationException
	{
		List<AnotoFormUser> list = this.formUserSession.get( company );
		return AnotoFormUserUtil.toListAnotoForm( list );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer nextId( ) throws ApplicationException
	{
		return this.nextIntegerId( "Form.nextId" );
	}

	@Override
	public AnotoForm add( AnotoForm entity, Company company ) throws ApplicationException
	{
		entity.setInsertDate( new Date( ) );
		entity = super.add( entity );
		if ( entity != null && company != null ) {
			AnotoFormUser formUser = new AnotoFormUser( );
			formUser.setForm( entity );
			formUser.setCompany( company );
			this.formUserSession.add( formUser );
		}
		return entity;
	}

	@Override
	public Pad addPadFile( AnotoForm form, Media media, List<String> pages, Boolean bUnique ) throws ApplicationException
	{
		form = this.get( form.getId( ) );
		media = this.mediaSession.get( media.getId( ) );
		Pad newEntity = new Pad( form, media );
		newEntity.setUnique( bUnique );
		newEntity.setInsertDate( new Date( ) );
		newEntity = this.padSession.add( newEntity );
		this.loadPadFile( newEntity, pages );
		return newEntity;
	}

	@Override
	public FormMedia addFile( AnotoForm form, Media media ) throws ApplicationException
	{
		form = this.getEntityManager( ).merge( form );
		media = this.getEntityManager( ).merge( media );

		FormMedia newEntity = new FormMedia( form, media );
		this.getEntityManager( ).persist( newEntity );
		return newEntity;
	}

	@Override
	public void removeFile( AnotoForm form, Media media ) throws ApplicationException
	{
		FormMediaPK pk = new FormMediaPK( form, media );

		FormMedia newEntity = this.getEntityManager( ).find( FormMedia.class, pk );
		if ( newEntity != null ) {
			this.getEntityManager( ).remove( newEntity );
		}
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<FormMedia> getFiles( AnotoForm form ) throws ApplicationException
	{
		@SuppressWarnings( "unchecked" )
		List<FormMedia> list = (List<FormMedia>) this.getResultList( FormMedia.formGetFiles, form.getId( ) );
		return list;
	}

	protected void loadPadFile( Pad pad, List<String> pages ) throws ApplicationException
	{
		AnotoPage page;
		try {
			for ( String address : pages ) {
				page = new AnotoPage( pad, address );
				this.anotoPageSession.add( page );
			}
		}
		catch ( Exception e ) {
			throw new EJBException( "Erro ao carregar o arquivo PAD", e );
		}
	}

	@Override
	public Media removePadFile( AnotoForm form, Media pad ) throws ApplicationException
	{
		PadPK padKey = new PadPK( form.getId( ), pad.getId( ) );
		Pad removeEntity = this.getEntityManager( ).find( Pad.class, padKey );
		this.getEntityManager( ).remove( removeEntity );
		return pad;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<Pad> getPads( AnotoForm form ) throws ApplicationException
	{
		@SuppressWarnings( "unchecked" )
		List<Pad> padList = (List<Pad>) this.getResultList( Pad.padFindAllNamedQuery, form.getApplication( ) );
		return padList;
	}

	@Override
	public AnotoForm update( AnotoForm entity ) throws ApplicationException
	{
		AnotoForm form = this.get( entity.getId( ) );
		entity.setInsertDate( form.getInsertDate( ) );
		return super.update( entity );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPen> getAvailablePens( AnotoForm form ) throws ApplicationException
	{
		Query query;
		List<AnotoPen> list;

		try {
			query = this.getEntityManager( ).createNamedQuery( AnotoPen.formAvailablePens );
			query.setParameter( 1, form.getId( ) );
			list = query.getResultList( );
		}
		catch ( NoResultException e ) {
			e = null;
			list = Collections.emptyList( );
		}
		return list;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPen> getPens( AnotoForm form ) throws ApplicationException
	{
		@SuppressWarnings( "unchecked" )
		List<AnotoPenPage> list = (List<AnotoPenPage>) this.getResultList( AnotoPenPage.formPensQueryName, form );
		return AnotoUtils.getPenListFromPenPage( list, true );
	}

	@Override
	public void add( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
	{
		@SuppressWarnings( "unchecked" )
		List<AnotoPage> list = (List<AnotoPage>) this.getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
		if ( SysUtils.isEmpty( list ) ) {
			return;
		}
		for ( AnotoPen pen : pens ) {
			for ( AnotoPage page : list ) {
				AnotoPenPage penPage = this.penPageSession.get( pen, page.getPageAddress( ) );
				if ( penPage != null ) {
					throw new ApplicationRuntimeException( "A caneta encontra associada a outra malha com mesmo IP" );
				}
				this.penPageSession.add( page, pen );
			}
		}
	}

	protected boolean existsPgcPenPage( AnotoPenPage penPage ) throws ApplicationException
	{
		List<?> list = this.getResultList( PgcPenPage.getAllPgcQueryName, penPage );
		return SysUtils.isEmpty( list ) ? false : true;
	}

	@Override
	public void remove( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
	{
		@SuppressWarnings( "unchecked" )
		List<AnotoPage> list = (List<AnotoPage>) this.getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
		if ( SysUtils.isEmpty( list ) ) {
			return;
		}
		for ( AnotoPen pen : pens ) {
			for ( AnotoPage page : list ) {
				this.penPageSession.delete( page, pen );
			}
		}
	}
}
