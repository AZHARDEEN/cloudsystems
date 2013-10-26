package br.com.mcampos.ejb.core;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.jpa.system.LogProgramException;

/**
 * Brief Esta é a classe base de todos os SessionBean no java ou seja, todas os SessionBeans devem derivar desta interface. O maior motivo para que todas
 * as classes derivem desta é ter um ponto único de criação do persistence context, pois, se for necessário alterar o nome do PU, isto deve ser feito
 * somente neste ponto.
 */

public abstract class BaseSessionBean implements BaseSessionInterface, Serializable
{
	private static final long serialVersionUID = -5194649999923317181L;
	private static final Logger logger = LoggerFactory.getLogger( BaseSessionBean.class.getSimpleName( ) );
	@Resource
	private SessionContext sessionContext; // **< Contexto de sessão do EJB. Até hoje eu ainda não usei

	@PersistenceContext( unitName = "SystemEJB" )
	private EntityManager em;// **< Entity Manager. Esta variável é fundamental para todo o sistema

	/**
	 * Brief Principal funão desta classe. O entity manager é a unidade básica de transação do EJB
	 * 
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager( )
	{
		return this.em;
	}

	/**
	 * Brief esta função retorna o contexto da sessão (Em termos do EJB 3.1) e é diferente do contexto da sessão http
	 * 
	 * @return SessionContext
	 */
	public SessionContext getSessionContext( )
	{
		return this.sessionContext;
	}

	/**
	 * Brief Esta função é usada para armazenar qualquer problema (Exceção) no banco de dados para depuração posterior. Teoricamente, toda e qualquer
	 * exceção deverá ser catalogada no banco de dados. Claro que sempre haverá situações onde isso não acontecerá. Mas deverá ser mapeado e incluído sua
	 * devida captura.
	 * 
	 * @param e
	 *            Exceção capiturada
	 * @return nada
	 */
	@Override
	@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
	public void storeException( Exception e )
	{
		try {
			if ( e == null ) {
				return;
			}
			String trace = this.getStackTrace( e );

			LogProgramException log = new LogProgramException( );
			log.setDescription( trace );
			log.setInsertDate( new Date( ) );
			this.getEntityManager( ).persist( log );
			logger.error( "Store Program Exception", e );
		}
		catch ( Exception exp ) {
			logger.error( "Erro on Store Program Exception", exp );
		}
	}

	/**
	 * Brief Obtem a pilha da exceção, no estilo printstacktrace
	 * 
	 * @param exception
	 *            Exceção capiturada
	 * @return String contendo a pilha
	 */
	private String getStackTrace( Exception exception )
	{
		StringWriter errors = new StringWriter( );
		exception.printStackTrace( new PrintWriter( errors ) );
		return errors.toString( );
	}

}
