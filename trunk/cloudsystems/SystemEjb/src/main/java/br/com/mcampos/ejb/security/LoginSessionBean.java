package br.com.mcampos.ejb.security;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.BasicPasswordEncryptor;

import br.com.mcampos.dto.MailDTO;
import br.com.mcampos.dto.core.CredentialDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.email.part.EmailPartSessionLocal;
import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.ejb.security.lastusedpwd.LastUsedPasswordSessionLocal;
import br.com.mcampos.ejb.security.log.AccessLogSessionLocal;
import br.com.mcampos.ejb.security.log.AccessLogTypeSessionLocal;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.jpa.security.AccessLog;
import br.com.mcampos.jpa.security.AccessLogType;
import br.com.mcampos.jpa.security.LastUsedPassword;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.security.UserStatus;
import br.com.mcampos.jpa.system.EMail;
import br.com.mcampos.jpa.system.SystemParameters;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "LoginSession", mappedName = "LoginSession" )
public class LoginSessionBean extends SimpleSessionBean<Login> implements LoginSession, LoginSessionLocal
{
	@EJB
	private SystemParameterSessionLocal paramSession;

	@EJB
	private AccessLogTypeSessionLocal logTypeSession;

	@EJB
	private AccessLogSessionLocal logSession;

	@EJB
	private EmailPartSessionLocal emailPartSession;

	@EJB
	private UserStatusSessionLocal statusSession;

	@EJB
	private LastUsedPasswordSessionLocal lupSession;

	@EJB
	private UserDocumentSessionLocal userDocument;

	@Override
	protected Class<Login> getEntityClass( )
	{
		return Login.class;
	}

	public Login get( Users usr )
	{
		if ( usr == null ) {
			return null;
		}
		return get( usr.getId( ) );
	}

	@Override
	public Login getByDocument( String document )
	{
		Query query = getEntityManager( ).createNamedQuery( UserDocument.findDocument );
		query.setParameter( 1, document );
		UserDocument doc;

		try {
			doc = (UserDocument) query.getSingleResult( );
			Login login = get( doc.getUser( ) );
			return login;
		}
		catch ( Exception e ) {
			doc = null;
			return null;
		}
	}

	@Override
	public Login loginByDocument( CredentialDTO credential )
	{
		if ( credential == null ) {
			return null;
		}
		if ( SysUtils.isEmpty( credential.getIdentification( ) ) || SysUtils.isEmpty( credential.getPassword( ) ) ) {
			return null;
		}

		try {
			Login login = getByDocument( credential.getIdentification( ) );
			if ( login != null && verifyPassword( login, credential ) == false ) {
				return null;
			}
			log( login, getLogTypeSession( ).get( AccessLogType.accessLogTypeNormalLogin ), credential );
			return login;
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			return null;
		}
	}

	private boolean verifyPassword( Login login, CredentialDTO credential )
	{
		if ( login == null || SysUtils.isEmpty( credential.getPassword( ) ) ) {
			return false;
		}
		if ( login.getTryCount( ) > getParamSession( ).getMaxTryCount( ) ) {
			return false;
		}
		if ( verifyPassword( login, credential.getPassword( ) ) == false )
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean verifyPassword( Login login, String password )
	{
		if ( login == null || SysUtils.isEmpty( password ) ) {
			return false;
		}
		int tryCount = login.getTryCount( );
		int maxTryCount = getProperty( ).getInt( SystemParameters.maxLoginTryCount );
		if ( maxTryCount == 0 )
		{
			maxTryCount = 5;
			getProperty( ).setInt( SystemParameters.maxLoginTryCount, "Número Máximo de Tentativas de Login", maxTryCount );
		}
		if ( tryCount > maxTryCount ) {
			return false;
		}
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor( );
		if ( passwordEncryptor.checkPassword( password, login.getPassword( ) ) == false ) {
			login.incrementTryCount( );
			tryCount = login.getTryCount( );
			if ( tryCount > maxTryCount ) {
				login.setStatus( statusSession.get( UserStatus.statusMaxLoginTryCount ) );
			}
			return false;
		}
		login.setTryCount( 0 );
		return true;
	}

	@Override
	public boolean verifyPassword( Integer userId, String password )
	{
		Login login = get( userId );
		return verifyPassword( login, password );
	}

	private boolean log( Login login, AccessLogType type, CredentialDTO credential )
	{
		if ( login == null || type == null ) {
			return false;
		}
		AccessLog log = new AccessLog( credential );

		log.setType( type );
		log.setUser( login );
		log.setLoginDate( new Timestamp( ( new Date( ) ).getTime( ) ) );

		log = getLogSession( ).merge( log );
		return true;
	}

	private SystemParameterSessionLocal getParamSession( )
	{
		return paramSession;
	}

	private AccessLogTypeSessionLocal getLogTypeSession( )
	{
		return logTypeSession;
	}

	private AccessLogSessionLocal getLogSession( )
	{
		return logSession;
	}

	@Override
	public Login get( Serializable key )
	{
		return getLastAccess( super.get( key ) );
	}

	private Login getLastAccess( Login login )
	{
		if ( login != null ) {
			AccessLog lastLogin = getLogSession( ).getLastLogin( login );

			login.setLastLogin( lastLogin );
		}
		return login;
	}

	private Collection<Login> getLastAccess( Collection<Login> list )
	{
		if ( SysUtils.isEmpty( list ) == false ) {
			for ( Login login : list ) {
				getLastAccess( login );
			}
		}
		return list;
	}

	@Override
	public Collection<Login> getAll( @NotNull PrincipalDTO auth )
	{
		return getLastAccess( super.getAll( auth ) );
	}

	@Override
	public Collection<Login> getAll( @NotNull PrincipalDTO auth, String whereClause, DBPaging page )
	{
		return getLastAccess( super.getAll( auth, whereClause, page ) );
	}

	@Override
	public Boolean changePassword( Integer id, CredentialDTO credential, String oldPasswor, String newPassword )
	{
		if ( id == null ) {
			return false;
		}
		Login entity = get( id );
		if ( entity == null ) {
			return false;
		}
		if ( verifyPassword( entity, oldPasswor ) == false || isPasswordUsed( entity, newPassword ) ) {
			return false;
		}
		changePassword( entity, credential, newPassword );
		return true;
	}

	private void changePassword( Login entity, CredentialDTO credential, String newPassword )
	{
		archivePassword( entity );
		entity.setPassword( encryptPassword( newPassword ) );
		archivePassword( entity );
		entity.setTryCount( 0 );
		setPasswordExpirationDate( entity );
		entity.setStatus( statusSession.get( UserStatus.statusOk ) );
		log( entity, getLogTypeSession( ).get( AccessLogType.accessLogTypeNormalLogin ), credential );
		entity.setToken( newPassword );
		sendMail( EMail.templatePasswordChanged, entity );
		entity.setToken( null );
	}

	private String encryptPassword( String password )
	{
		try {
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor( );
			String encryptedPassword = passwordEncryptor.encryptPassword( password );
			return encryptedPassword;
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	@Override
	public String getProperty( String id )
	{
		return getProperty( ).get( id );
	}

	@Override
	public Boolean sendValidationEmail( Integer userId )
	{
		Login login = get( userId );
		if ( login == null || login.getStatus( ).getId( ).equals( UserStatus.statusEmailNotValidated ) == false ) {
			return false;
		}
		return sendMail( EMail.templateValidationEmail, login );
	}

	private Boolean sendMail( Integer templateId, Object... params )
	{
		MailDTO dto = getEmailPartSession( ).getTemplate( templateId );
		if ( dto == null ) {
			return false;
		}
		for ( Object param : params ) {
			if ( ( param instanceof Login ) == false ) {
				continue;
			}
			Login login = (Login) param;
			dto.setBody( translateTokens( dto.getBody( ), login, login.getToken( ) ) );
			for ( UserDocument item : login.getPerson( ).getDocuments( ) ) {
				if ( item.getType( ).getId( ).equals( UserDocument.typeEmail ) ) {
					dto.addRecipient( item.getCode( ) );
				}
			}
			try {
				return getEmailPartSession( ).sendMail( dto );
			}
			catch ( Exception e ) {
				e.printStackTrace( );
			}
		}
		return false;
	}

	public EmailPartSessionLocal getEmailPartSession( )
	{
		return emailPartSession;
	}

	@Override
	public Boolean add( Person person, String password )
	{
		Login login;

		if ( person == null || password == null ) {
			return false;
		}
		login = get( person.getId( ) );
		if ( login != null ) {
			return false;
		}
		login = new Login( );
		/*
		 * Nunca esquecer de vincular os objetos. Quando um objeto é instanciado
		 * ele mantem suas características. Neste exemplo: foi instanciado uma
		 * pessoa, que neste momento não possuia login. Ainda que o login seja
		 * inserido no banco de dados, fica a situação inicial - sem login.
		 */
		login.setPerson( person );
		login.setPassword( encryptPassword( password ) );
		login.setToken( RandomStringUtils.random( 8, true, true ));
		setPasswordExpirationDate( login );
		login.setStatus( statusSession.get( UserStatus.statusEmailNotValidated ) );
		login = merge( login );

		sendMail( EMail.templateValidationEmail, login );
		return true;

	}

	private void setPasswordExpirationDate( Login login )
	{
		Integer days;
		Calendar now;

		try {
			days = getProperty( ).getInt( SystemParameters.passwordValidDays );
			if ( days == 0 ) {
				days = 90;
				getProperty( ).setInt( SystemParameters.passwordValidDays, "Validade da Senha", days );
			}
		}
		catch ( Exception e ) {
			days = 90;
		}
		now = Calendar.getInstance( );
		now.add( Calendar.DATE, days );
		login.setExpDate( new Timestamp( now.getTime( ).getTime( ) + days ) );
	}

	private String translateTokens( String msg, Login login, String flatPassword )
	{
		msg = msg.replaceAll( "<<@@LOGIN_NAME@@>>", login.getPerson( ).getName( ) );
		for ( UserDocument item : login.getPerson( ).getDocuments( ) ) {
			if ( item.getType( ).getId( ).equals( UserDocument.typeEmail ) ) {
				msg = msg.replaceAll( "<<@@EMAIL@@>>", item.getCode( ) );
				break;
			}
		}
		if ( SysUtils.isEmpty( login.getToken( ) ) == false ) {
			msg = msg.replaceAll( "<<@@TOKEN@@>>", login.getToken( ) );
		}
		if ( flatPassword != null ) {
			msg = msg.replace( "<<@@PASSWORD@@>>", flatPassword );
		}
		return msg;
	}

	@Override
	public Login getByToken( String token )
	{
		try {
			return getByNamedQuery( Login.getByToken, token );
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	@Override
	public boolean validateToken( String token, String password )
	{
		if ( SysUtils.isEmpty( token ) || SysUtils.isEmpty( password ) ) {
			return false;
		}
		Login login = getByToken( token );
		if ( login == null ) {
			return false;
		}
		if ( verifyPassword( login, password ) == false ) {
			return false;
		}
		/*
		 * Clear token
		 */
		login.setToken( null );
		login.setStatus( statusSession.get( UserStatus.statusFullfillRecord ) );
		sendMail( EMail.templateValidationEmail, login );
		return true;
	}

	private Boolean isPasswordUsed( Login login, String newPassword )
	{
		BasicPasswordEncryptor passwordEncryptor;

		try {
			List<LastUsedPassword> list;

			list = lupSession.findByNamedQuery( LastUsedPassword.findAllByLogin, new DBPaging( 0, 50 ), login );
			passwordEncryptor = new BasicPasswordEncryptor( );
			for ( LastUsedPassword password : list ) {
				if ( passwordEncryptor.checkPassword( newPassword, password.getId( ).getPassword( ) ) ) {
					return true;
				}
			}
		}
		catch ( NoResultException e ) {
			return false;
		}
		return false;
	}

	@Override
	public Boolean isPasswordUsed( Integer id, String newPassword )
	{
		Login login = get( id );
		return isPasswordUsed( login, newPassword );
	}

	private void archivePassword( Login login )
	{
		LastUsedPassword lastUsedPassword;

		lastUsedPassword = lupSession.get( login );
		if ( lastUsedPassword == null ) {
			lupSession.closeAllUsedPassword( login );
			lastUsedPassword = new LastUsedPassword( login );
			lastUsedPassword.setFromDate( new Date( ) );
			lupSession.persist( lastUsedPassword );
		}
		else {
			lastUsedPassword.setToDate( new Date( ) );
			lupSession.merge( lastUsedPassword );
		}
	}

	@Override
	public void logout( Integer id, CredentialDTO credential )
	{
		if ( id == null )
			throw new InvalidParameterException( this.getClass( ).getSimpleName( ) + " - Login could not be null" );
		AccessLogType type = getLogTypeSession( ).get( AccessLogType.accessLogTypeLogout );
		log( get( id ), type, credential );
	}

	@Override
	public List<Login> search( String searchField, String lookFor )
	{
		List<Login> logins = Collections.emptyList( );
		if ( SysUtils.isEmpty( searchField ) || SysUtils.isEmpty( lookFor ) )
			return Collections.emptyList( );
		if ( searchField.equalsIgnoreCase( "id" ) ) {
			Login login = get( SysUtils.parseInteger( lookFor ) );
			if ( login == null )
				return Collections.emptyList( );
			logins = new ArrayList<Login>( );
			logins.add( login );
		}
		else if ( searchField.equalsIgnoreCase( "name" ) ) {
			return searchByName( lookFor );
		}
		else if ( searchField.equalsIgnoreCase( "email" ) ) {
			return searchByEmail( lookFor );
		}
		else if ( searchField.equalsIgnoreCase( "cpf" ) ) {
			return searchByCPF( lookFor );
		}
		return logins;
	}

	private List<Login> searchByName( String lookFor )
	{
		String sql = "select o from Login o where o.person.name ";

		if ( lookFor.contains( "*" ) || lookFor.contains( "%" ) || lookFor.contains( "_" ) || lookFor.contains( "?" ) ) {
			sql = sql + "like ";
			if ( lookFor.contains( "*" ) )
				lookFor = lookFor.replaceAll( "\\*", "\\%" );
			if ( lookFor.contains( "?" ) )
				lookFor = lookFor.replaceAll( "\\?", "_" );
		}
		else {
			sql = sql + " = ";
		}
		sql = sql + "upper ( ?1 ) order by o.person.name";
		return findByQuery( sql, new DBPaging( 0, 40 ), lookFor );
	}

	private List<Login> searchByEmail( String lookFor )
	{
		if ( SysUtils.isEmpty( lookFor ) )
			return Collections.emptyList( );
		lookFor = lookFor.toLowerCase( );
		String sql = "select o from Login o where o.person in (" +
				getUserDocumentSQL( lookFor ) + ")";
		sql = sql + "order by o.person.name";
		return findByQuery( sql, new DBPaging( 0, 40 ), UserDocument.typeEmail, lookFor );
	}

	private List<Login> searchByCPF( String lookFor )
	{
		if ( SysUtils.isEmpty( lookFor ) )
			return Collections.emptyList( );
		lookFor = lookFor.replaceAll( "\\.", "" );
		lookFor = lookFor.replaceAll( "-", "" );
		String sql = "select o from Login o where o.person in (" +
				getUserDocumentSQL( lookFor ) + ")";
		sql = sql + "order by o.person.name";
		return findByQuery( sql, new DBPaging( 0, 40 ), UserDocument.typeCPF, lookFor );
	}

	private String getUserDocumentSQL( String lookFor )
	{
		if ( SysUtils.isEmpty( lookFor ) )
			return null;
		String sql = "select distinct o.user from UserDocument o where o.type.id = ?1 and o.code ";

		if ( lookFor.contains( "*" ) || lookFor.contains( "%" ) || lookFor.contains( "_" ) || lookFor.contains( "?" ) ) {
			sql = sql + "like ";
			if ( lookFor.contains( "*" ) )
				lookFor = lookFor.replaceAll( "\\*", "\\%" );
			if ( lookFor.contains( "?" ) )
				lookFor = lookFor.replaceAll( "\\?", "_" );
		}
		else {
			sql = sql + " = ";
		}
		sql = sql + " ( ?2 )";
		return sql;
	}

	@Override
	public Login resetLogin( PrincipalDTO admin, Login toReset, CredentialDTO credential )
	{
		if ( admin == null || toReset == null )
			return toReset;
		Login targetLogin = get( toReset.getId( ) );
		if ( targetLogin == null )
			return toReset;
		targetLogin.setStatus( statusSession.get( ( UserStatus.statusOk ) ) );
		targetLogin.setTryCount( 0 );
		setPasswordExpirationDate( targetLogin );
		String newPwd = RandomStringUtils.random( 8, true, true );
		changePassword( targetLogin, credential, newPwd );
		return targetLogin;
	}

	@Override
	public String randomPassword( int count )
	{
		return RandomStringUtils.randomAscii( count );
	}

}
