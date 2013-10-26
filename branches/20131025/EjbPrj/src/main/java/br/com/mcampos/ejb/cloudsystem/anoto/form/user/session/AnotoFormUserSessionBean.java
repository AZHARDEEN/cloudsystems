package br.com.mcampos.ejb.cloudsystem.anoto.form.user.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.ApplicationRuntimeException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUserPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "AnotoFormUserSession", mappedName = "AnotoFormUserSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class AnotoFormUserSessionBean extends Crud<AnotoFormUserPK, AnotoFormUser> implements AnotoFormUserSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4062495616739594914L;
	@EJB
	private CompanySessionLocal companySession;

	public AnotoFormUserSessionBean( )
	{
	}

	@Override
	public void delete( AnotoFormUserPK key ) throws ApplicationException
	{
		AnotoFormUser entity = this.get( AnotoFormUser.class, key );
		if ( entity != null ) {
			entity.setToDate( new Date( ) );
		}
	}

	@Override
	public AnotoFormUser add( AnotoFormUser entity ) throws ApplicationException
	{
		entity.setFromDate( new Date( ) );
		entity.setSequence( this.nextSequence( entity.getForm( ) ) );
		return super.add( entity );
	}

	@Override
	public AnotoFormUser add( Integer formId, Integer companyId ) throws ApplicationException
	{
		AnotoFormUser entity = new AnotoFormUser( );
		AnotoForm form = this.getEntityManager( ).find( AnotoForm.class, formId );
		Company company = this.companySession.get( companyId );
		entity.setForm( form );
		entity.setCompany( company );
		return this.add( entity );
	}

	private Integer nextSequence( AnotoForm form ) throws ApplicationException
	{
		Integer id = this.nextIntegerId( AnotoFormUser.nextSequence, form );
		if ( SysUtils.isZero( id ) ) {
			id = 1;
		}
		return id;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<AnotoFormUser> get( Integer formId ) throws ApplicationException
	{
		return (List<AnotoFormUser>) this.getResultList( AnotoFormUser.getAll, formId );
	}

	@Override
	public AnotoFormUser get( Integer formId, Integer companyId ) throws ApplicationException
	{
		ArrayList<Object> param = new ArrayList<Object>( 2 );
		param.add( formId );
		param.add( companyId );
		@SuppressWarnings( "unchecked" )
		List<AnotoFormUser> list = (List<AnotoFormUser>) this.getResultList( AnotoFormUser.getUser, param );
		if ( SysUtils.isEmpty( list ) ) {
			return null;
		}
		/*
		 * Just in case!!!1
		 */
		if ( list.size( ) > 1 ) {
			throw new ApplicationRuntimeException( "Erro interno! Existe mais de um registro associado ao formulário" );
		}
		return list.get( 0 );
	}

	@Override
	public void delete( Integer formId, Integer companyId ) throws ApplicationException
	{
		AnotoFormUser user = this.get( formId, companyId );
		if ( user != null ) {
			user.setToDate( new Date( ) );
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<AnotoFormUser> get( Company company ) throws ApplicationException
	{
		return (List<AnotoFormUser>) this.getResultList( AnotoFormUser.getFormUser, company );
	}
}
