package br.com.mcampos.ejb.security.log;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jasypt.util.text.BasicTextEncryptor;

import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.entity.security.AccessLog;
import br.com.mcampos.entity.security.Login;
import br.com.mcampos.entity.system.SystemParameters;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Stateless( name = "AccessLogSession", mappedName = "AccessLogSession" )
public class AccessLogSessionBean extends BaseSessionBean<AccessLog> implements AccessLogSession, AccessLogSessionLocal
{
	@EJB
	SystemParameterSessionLocal paramSession;

	@Override
	protected void setParameters( Query query )
	{
	}

	@Override
	protected Class<AccessLog> getEntityClass( )
	{
		return AccessLog.class;
	}

	@Override
	protected Query getAllQuery( PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select t from " + getPersistentClass( ).getSimpleName( );
		if ( whereClause != null && whereClause.isEmpty( ) == false ) {
			whereClause = whereClause.trim( ).toLowerCase( );
			if ( whereClause.startsWith( "where" ) ) {
				sqlQuery += " " + whereClause;
			}
			else {
				sqlQuery += " where " + whereClause;
			}
		}
		sqlQuery += " as t order by t.id desc";
		Query query = getEntityManager( ).createQuery( sqlQuery );
		return query;
	}

	@Override
	public AccessLog merge( AccessLog log )
	{
		if ( SysUtils.isEmpty( log.getSessionId( ) ) == false ) {
			BasicTextEncryptor encrypt = new BasicTextEncryptor( );
			String value = getParamSession( ).getStringValue( SystemParameters.encprytPassword );
			encrypt.setPassword( value );
			log.setToken( encrypt.encrypt( log.getSessionId( ) ) );
		}
		return super.merge( log );
	}

	private SystemParameterSessionLocal getParamSession( )
	{
		return paramSession;
	}

	@Override
	public AccessLog getLastLogin( Login login )
	{
		if ( login == null || login.getId( ) == null ) {
			return null;
		}

		Query query = getEntityManager( ).createNamedQuery( AccessLog.getLastLogin );
		query.setParameter( "user1", login );
		query.setParameter( "user", login );

		try {
			return (AccessLog) query.getSingleResult( );
		}
		catch ( Exception e ) {
			e = null;
			return null;
		}
	}
}
