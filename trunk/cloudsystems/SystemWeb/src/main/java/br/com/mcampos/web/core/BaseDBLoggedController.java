package br.com.mcampos.web.core;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.dto.AuthorizedPageOptions;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseDBLoggedController<BEAN extends BaseSessionInterface> extends BaseDBController<BEAN> implements LoggedInterface
{
	private static final long serialVersionUID = 3928960337564242027L;
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseDBLoggedController.class );
	private CollaboratorSession collaboratorSession;
	private AuthorizedPageOptions authorizedPageOptions;

	@Override
	public boolean isLogged( )
	{
		PrincipalDTO p = getPrincipal( );
		return p != null;
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if ( isLogged( ) && isValidAccess( compInfo.getPageDefinition( ).getRequestPath( ) ) ) {
			LOGGER.info( "Access is ok for url " + compInfo.getPageDefinition( ).getRequestPath( ) );
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
		PrincipalDTO c = getPrincipal( );
		if ( c == null ) {
			LOGGER.error( "Collaborator is null" );
			return true;
		}
		if ( SysUtils.isEmpty( path ) ) {
			LOGGER.error( "Access is not valid" );
			return false;
		}
		setAuthorizedPageOptions( getCollaboratorSession( ).verifyAccess( c, path ) );
		if ( getAuthorizedPageOptions( ).isAuthorized( ) == false )
			LOGGER.error( "Invalid Access  violations" );
		return getAuthorizedPageOptions( ).isAuthorized( );
	}

	protected void setCollaborator( Collaborator c )
	{
		if ( c != null ) {
			PrincipalDTO dto;

			dto = getPrincipal( );
			if ( dto == null ) {
				dto = new PrincipalDTO( c.getCompany( ).getId( ), c.getPerson( ).getId( ), c.getPerson( ).getFriendlyName( ) );
				setSessionParameter( currentPrincipal, dto );
			}
			else {
				dto.setCompanyID( c.getCompany( ).getId( ) );
				dto.setName( c.getPerson( ).getFriendlyName( ) );
				dto.setUserId( c.getPerson( ).getId( ) );
				dto.setSequence( c.getId( ).getSequence( ) );
			}
		}
		else
			clearSessionParameter( currentPrincipal );
	}

	public CollaboratorSession getCollaboratorSession( )
	{
		try {
			if ( this.collaboratorSession == null )
				this.collaboratorSession = (CollaboratorSession) ServiceLocator.getInstance( ).getRemoteSession( CollaboratorSession.class,
						ServiceLocator.EJB_NAME[ 0 ] );
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

	protected Map<String, Object> configReportParams( )
	{
		Map<String, Object> params = new HashMap<String, Object>( );

		PrincipalDTO dto = getPrincipal( );
		params.put( "COMPANY_ID", dto.getCompanyID( ) );
		params.put( "COLLABORATOR_ID", dto.getUserId( ) );
		return params;
	}

}
