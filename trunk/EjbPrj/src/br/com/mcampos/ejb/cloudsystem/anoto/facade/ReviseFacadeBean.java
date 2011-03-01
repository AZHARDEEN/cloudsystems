package br.com.mcampos.ejb.cloudsystem.anoto.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.session.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "ReviseFacade", mappedName = "CloudSystems-EjbPrj-ReviseFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ReviseFacadeBean extends AbstractSecurity implements ReviseFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2109005480787279063L;

	protected static final int SystemMessageTypeId = 27;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private PgcFieldSessionLocal pgcFieldSession;

	@EJB
	private PgcPageSessionLocal pgcPageSession;

	@EJB
	private PageFieldSessionLocal anotoPageFieldSession;

	@EJB
	private MediaSessionLocal mediaSession;

	@Override
	protected EntityManager getEntityManager( )
	{
		return em;
	}

	@Override
	public Integer getMessageTypeId( )
	{
		return SystemMessageTypeId;
	}

	@Override
	public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
	{
		authenticate( auth );
		PgcPage pgcPage = pgcPageSession.get( new PgcPagePK( page ) );
		return AnotoUtils.toPgcFieldList( getPageFields( pgcPage ) );
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private List<PgcField> getPageFields( PgcPage pgcPage ) throws ApplicationException
	{
		if ( pgcPage != null ) {
			if ( pgcPage.getRevisionStatus( ).getId( ).equals( 1 ) )
				pgcPageSession.setRevisedStatus( pgcPage, 2 );
			return pgcFieldSession.getAll( pgcPage );
		}
		else
			return Collections.emptyList( );
	}

	@SuppressWarnings( "unused" )
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private List<PgcField> getBookFields( PgcPage pgcPage ) throws ApplicationException
	{
		if ( pgcPage != null ) {
			if ( pgcPage.getRevisionStatus( ).getId( ).equals( 1 ) )
				pgcPageSession.setRevisedStatus( pgcPage, 2 );
			return pgcFieldSession.getAllBook( pgcPage );
		}
		else
			return Collections.emptyList( );
	}

	@Override
	public void setStatus( AuthenticationDTO auth, PgcPageDTO page, Integer status ) throws ApplicationException
	{
		authenticate( auth );
		PgcPage pgcPage = pgcPageSession.get( new PgcPagePK( page ) );
		if ( pgcPage != null ) {
			if ( pgcPage.getRevisionStatus( ).getId( ).equals( status ) )
				throwRuntimeException( 1 );
			pgcPageSession.setRevisedStatus( pgcPage, status );
			if ( status.equals( RevisionStatus.statusVerified ) )
				updateForm( pgcPage );
		}
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void updateForm( PgcPage pgcPage ) throws ApplicationException
	{
		List<PgcPage> olderPages = null;
		int book = -1;
		if ( pgcPage.getAnotoPage( ).getPad( ).getForm( ).getConcatenatePgc( ).equals( true ) ) {
			olderPages = findPageByFieldKeys( pgcPage );
			if ( SysUtils.isEmpty( olderPages ) == false ) {
				List<PgcField> newFields = pgcFieldSession.getAllBook( pgcPage );
				List<PgcField> oldFields;
				for ( PgcPage oldPage : olderPages ) {
					if ( oldPage.getBookId( ).equals( book ) )
						continue;
					book = oldPage.getBookId( );
					oldFields = pgcFieldSession.getAllBook( oldPage );
					try {
						for ( int index = 0; index < oldFields.size( ); index++ ) {
							PgcField newField = newFields.get( index );
							if ( newField.getType( ).equals( FieldType.typeBoolean ) && newField.getHasPenstrokes( ) ) {
								continue;
							}
							else if ( SysUtils.isEmpty( newField.getValue( ) ) == false ) {
								continue;
							}
							PgcField oldField = oldFields.get( index );
							if ( ( oldField.getHasPenstrokes( ) || SysUtils.isEmpty( oldField.getValue( ) ) == false ) && oldField.getType( ).equals( newField.getType( ) ) ) {
								newField.setIcrText( oldField.getIcrText( ) );
								if ( newField.getHasPenstrokes( ) == false )
									newField.setHasPenstrokes( oldField.getHasPenstrokes( ) );
								newField.setMedia( oldField.getMedia( ) );
								newField.setRevisedText( oldField.getRevisedText( ) );
								pgcFieldSession.update( newField );
							}
						}
					}
					catch ( ArrayIndexOutOfBoundsException e ) {
						return;
					}
				}
			}
		}
	}

	@Override
	public byte[ ] getObject( MediaDTO key ) throws ApplicationException
	{
		return mediaSession.getObject( key.getId( ) );
	}

	@Override
	public void update( AuthenticationDTO auth, PgcFieldDTO dto ) throws ApplicationException
	{
		authenticate( auth );
		PgcField field = pgcFieldSession.get( new PgcFieldPK( dto ) );
		if ( field != null ) {
			PgcFieldUtil.update( field, dto );
			pgcFieldSession.update( field );
		}
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private List<PgcPage> findPageByFieldKeys( PgcPage pgcPage ) throws ApplicationException
	{
		if ( !pgcPage.getRevisionStatus( ).getId( ).equals( RevisionStatus.statusVerified ) )
			return Collections.emptyList( );
		List<AnotoPageField> pkFields = anotoPageFieldSession.getPKFields( pgcPage.getAnotoPage( ).getPad( ).getForm( ) );
		StringBuffer filter = preparePKFields( pgcPage, pkFields );
		if ( filter == null )
			return Collections.emptyList( );
		String sql = getAnotherPageByPkFieldSQL( pgcPage, filter );
		try {
			Query query = getEntityManager( ).createNativeQuery( sql, PgcPage.class );
			@SuppressWarnings( "unchecked" )
			List<PgcPage> pages = query.getResultList( );
			return pages;
		}
		catch ( Exception e ) {
			return Collections.emptyList( );
		}
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private StringBuffer preparePKFields( PgcPage pgcPage, List<AnotoPageField> pkFields ) throws ApplicationException
	{
		if ( pgcPage == null )
			return null;
		if ( SysUtils.isEmpty( pkFields ) )
			return null;
		StringBuffer filter = new StringBuffer( );
		for ( AnotoPageField field : pkFields ) {
			PgcField pgcField = pgcFieldSession.get( new PgcFieldPK( pgcPage, field.getName( ) ) );
			if ( pgcField == null )
				return null;
			if ( filter.length( ) > 0 )
				filter.append( " AND " );
			filter.append( String.format( " ( pgc_field.pfl_name_ch = '%s' \n" + "and pgc_field.pfl_has_penstrokes_bt = true \n" + "and coalesce ( pgc_field.pfl_icr_tx, pgc_field.pfl_revised_tx ) = '%s' ) \n", pgcField.getName( ), pgcField.getValue( ) ) );
		}
		return filter;
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private String getAnotherPageByPkFieldSQL( PgcPage pgcPage, StringBuffer filter )
	{
		String sql;

		sql = String.format( "select pgc_page.* from pgc_page where \n" + "exists ( select * from pgc_field where pgc_field.pfl_has_penstrokes_bt = true  \n" + "and ( %s ) \n" + "and pgc_field.pgc_id_in = pgc_page.pgc_id_in \n"
				+ "and pgc_field.ppg_book_id = pgc_page.ppg_book_id \n" + "and pgc_field.ppg_page_id = pgc_page.ppg_page_id \n" + ") \n" + "and pgc_page.pgc_id_in <> %d \n" + "and pgc_page.rst_id_in = 3 \n"
				+ "order by pgc_id_in desc, ppg_book_id, ppg_page_id", filter.toString( ), pgcPage.getPgc( ).getId( ) );
		return sql;
	}
}
