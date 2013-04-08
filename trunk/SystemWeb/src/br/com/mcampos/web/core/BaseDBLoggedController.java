package br.com.mcampos.web.core;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.dto.AuthorizedPageOptions;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class BaseDBLoggedController<BEAN> extends BaseDBController<BEAN> implements LoggedInterface
{
	private static final long serialVersionUID = 3928960337564242027L;
	private static final Logger logger = LoggerFactory.getLogger( BaseDBLoggedController.class );
	private CollaboratorSession collaboratorSession;
	private AuthorizedPageOptions authorizedPageOptions;

	@Override
	public boolean isLogged( )
	{
		Login login = getLoggedUser( );
		return login != null;
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if ( isLogged( ) && isValidAccess( compInfo.getPageDefinition( ).getRequestPath( ) ) ) {
			logger.error( "Access is ok for url " + compInfo.getPageDefinition( ).getRequestPath( ) );
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			Sessions.getCurrent( ).invalidate( );
			redirect( "/index.zul" );
			return null;
		}
	}

	protected boolean isValidAccess( String path )
	{
		Collaborator c = getCurrentCollaborator( );
		if ( c == null ) {
			logger.error( "Collaborator is null" );
			return true;
		}
		if ( SysUtils.isEmpty( path ) ) {
			logger.error( "Access is not valid" );
			return false;
		}
		logger.error( "verifing access" );
		setAuthorizedPageOptions( getCollaboratorSession( ).verifyAccess( c, path ) );
		if ( getAuthorizedPageOptions( ).isAuthorized( ) == false ) {
			logger.error( "Invalid Access  violations" );
		}
		return getAuthorizedPageOptions( ).isAuthorized( );
	}

	@Override
	public Login getLoggedUser( )
	{
		return (Login) getSessionParameter( userSessionParamName );
	}

	@Override
	public Collaborator getCurrentCollaborator( )
	{
		Collaborator c = (Collaborator) getSessionParameter( currentCollaborator );
		Login l = getLoggedUser( );
		if ( c == null || c.getPerson( ).equals( l.getPerson( ) ) == false ) {
			return null;
		}
		return c;
	}

	protected void setCollaborator( Collaborator c )
	{
		setSessionParameter( currentCollaborator, c );
	}

	public CollaboratorSession getCollaboratorSession( )
	{
		try {
			if ( this.collaboratorSession == null ) {
				this.collaboratorSession = (CollaboratorSession) ServiceLocator.getInstance( ).getRemoteSession( CollaboratorSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.collaboratorSession;
	}

	/**
	 * @return the authorizedPageOptions
	 */
	protected AuthorizedPageOptions getAuthorizedPageOptions( )
	{
		return this.authorizedPageOptions;
	}

	/**
	 * @param authorizedPageOptions
	 *            the authorizedPageOptions to set
	 */
	protected void setAuthorizedPageOptions( AuthorizedPageOptions authorizedPageOptions )
	{
		this.authorizedPageOptions = authorizedPageOptions;
	}

}
