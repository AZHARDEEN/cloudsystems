package br.com.mcampos.ejb.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.jpa.system.LogProgramException;

public abstract class BaseSessionBean implements BaseSessionInterface
{
	private static final Logger logger = LoggerFactory.getLogger( BaseSessionBean.class.getSimpleName( ) );
	@Resource
	private SessionContext sessionContext;

	@PersistenceContext( unitName = "SystemEJB" )
	private EntityManager em;

	protected EntityManager getEntityManager( )
	{
		return em;
	}

	public SessionContext getSessionContext( )
	{
		return sessionContext;
	}

	@Override
	public void storeException( Exception e )
	{
		try {
			if ( e == null ) {
				return;
			}
			String trace = getStackTrace( e );

			LogProgramException log = new LogProgramException( );
			log.setDescription( trace );
			log.setInsertDate( new Date( ) );
			getEntityManager( ).persist( log );
			logger.error( "Store Program Exception", e );
		}
		catch ( Exception exp ) {
			logger.error( "Erro on Store Program Exception", exp );
		}
	}

	private String getStackTrace( Exception exception )
	{
		StringWriter errors = new StringWriter( );
		exception.printStackTrace( new PrintWriter( errors ) );
		return errors.toString( );
	}

}
