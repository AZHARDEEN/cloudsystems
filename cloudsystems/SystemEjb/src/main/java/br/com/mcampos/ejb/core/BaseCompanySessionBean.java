package br.com.mcampos.ejb.core;

import javax.ejb.EJB;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseCompanySessionBean<T extends BaseCompanyEntity> extends SimpleSessionBean<T>
{
	private static final long serialVersionUID = -5691331878597413478L;

	@EJB
	private CompanySessionLocal companySession;

	protected Company getCompany( PrincipalDTO auth )
	{
		if ( auth == null ) {
			return null;
		}
		return this.companySession.get( auth.getCompanyID( ) );
	}

	protected void set( @NotNull PrincipalDTO auth, @NotNull T entity )
	{
		if ( entity == null ) {
			return;
		}
		entity.setCompany( this.getCompany( auth ) );
	}

	@Override
	public T update( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		this.set( auth, newEntity );
		return super.update( auth, newEntity );
	}

	@Override
	public T add( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		this.set( auth, newEntity );
		return super.add( auth, newEntity );
	}

	@Override
	public int count( PrincipalDTO auth )
	{
		return super.count( auth );
	}

	@Override
	protected String getCountQL( @NotNull PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select count(t) as registros from " + this.getPersistentClass( ).getSimpleName( ) + " as t ";
		if ( SysUtils.isEmpty( whereClause ) ) {
			whereClause = "t.id.companyId = " + auth.getCompanyID( ) + " ";
		}
		else {
			whereClause = whereClause + " and t.id.companyId = " + auth.getCompanyID( ) + " ";
		}
		if ( whereClause != null && whereClause.isEmpty( ) == false ) {
			String aux = whereClause.trim( ).toLowerCase( );
			if ( aux.startsWith( "where" ) ) {
				sqlQuery += " " + whereClause;
			}
			else {
				sqlQuery += " where " + whereClause;
			}
		}
		return sqlQuery;
	}

	@Override
	protected Query getAllQuery( PrincipalDTO auth, String whereClause )
	{
		if ( SysUtils.isEmpty( whereClause ) ) {
			whereClause = "t.id.companyId = " + auth.getCompanyID( );
		}
		else {
			whereClause = whereClause + " AND t.id.companyId = " + auth.getCompanyID( );
		}
		return super.getAllQuery( auth, whereClause );
	}

}
