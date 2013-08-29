package br.com.mcampos.ejb.user.company.collaborator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.AuthorizedPageOptions;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSessionLocal;
import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.menu.MenuFacadeLocal;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.type.CollaboratorType;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;

/**
 * Session Bean implementation class CollaboratorSessionBean
 */
@Stateless( name = "CollaboratorSession", mappedName = "CollaboratorSession" )
@LocalBean
public class CollaboratorSessionBean extends SimpleSessionBean<Collaborator> implements CollaboratorSession, CollaboratorSessionLocal
{

	private static final Logger logger = LoggerFactory.getLogger( CollaboratorSessionBean.class.getSimpleName( ) );

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private LoginPropertySessionLocal propertySession;

	@EJB
	private LoginSessionLocal loginSession;

	@EJB
	private MenuFacadeLocal menuSession;

	@Override
	protected Class<Collaborator> getEntityClass( )
	{
		return Collaborator.class;
	}

	@Override
	public Collaborator find( PrincipalDTO auth )
	{
		if( auth == null )
			return null;
		Login login = loginSession.get( auth.getUserId( ) );

		if( login == null ) {
			return null;
		}
		Company company = companySession.get( auth.getCompanyID( ) );
		if( company == null ) {
			return null;
		}
		Collaborator c = getByNamedQuery( Collaborator.hasCollaborator, company, login.getPerson( ) );
		if( c != null ) {
			c.getPerson( ).getAddresses( ).size( );
			c.getPerson( ).getDocuments( ).size( );
			c.getPerson( ).getContacts( ).size( );
		}
		return c;
	}

	@Override
	public List<SimpleDTO> getCompanies( PrincipalDTO auth ) throws ApplicationException
	{
		List<Collaborator> list;
		if( auth == null || auth.getUserId( ) == null ) {
			return Collections.emptyList( );
		}
		try {
			Login login = loginSession.get( auth.getUserId( ) );
			if( login == null )
				return null;
			list = findByNamedQuery( Collaborator.findCompanies, login.getPerson( ) );
			if( SysUtils.isEmpty( list ) ) {
				return Collections.emptyList( );
			}
			return toSimpleDTOList( list );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
			return Collections.emptyList( );
		}
	}

	private List<SimpleDTO> toSimpleDTOList( List<Collaborator> list )
	{
		if( SysUtils.isEmpty( list ) ) {
			return Collections.emptyList( );
		}
		List<SimpleDTO> dtos = new ArrayList<SimpleDTO>( list.size( ) );
		for( Collaborator item : list )
		{
			String name = SysUtils.isEmpty( item.getCompany( ).getNickName( ) ) ? item.getCompany( ).getName( ) : item.getCompany( )
					.getNickName( );
			dtos.add( new SimpleDTO( item.getCompany( ).getId( ), name ) );
		}
		return dtos;
	}

	@Override
	public LoginProperty getProperty( Collaborator collaborator, String propertyName )
	{
		return propertySession.getProperty( collaborator, propertyName );
	}

	@Override
	public void setProperty( Collaborator collaborator, String propertyName, String Value )
	{
		propertySession.setProperty( collaborator, propertyName, Value );
	}

	@Override
	public LoginProperty remove( Collaborator collaborator, String propertyName )
	{
		return propertySession.remove( collaborator, propertyName );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession#verifyAccess
	 * (br.com.mcampos.ejb.user.company.collaborator.Collaborator,
	 * java.lang.String)
	 */
	@Override
	public AuthorizedPageOptions verifyAccess( PrincipalDTO c, String mnuUrl )
	{
		Login login = loginSession.get( c.getUserId( ) );
		Menu menu = menuSession.get( mnuUrl );
		AuthorizedPageOptions auth = new AuthorizedPageOptions( );
		if( menu == null ) {
			/*
			 * this is, maybe, some kind of resource or template.
			 */
			logger.info( "Menu is null for url - " + mnuUrl );
			auth.setAuthorized( true );
		}
		try {
			List<Menu> menus = getMenus( c );
			if( SysUtils.isEmpty( menus ) ) {
				logger.error( "User: " + login.getPerson( ).getName( ) + " is not - Authorized for " + mnuUrl );
				auth.setAuthorized( false );
			}
			else if( menus.contains( menu ) == false ) {
				logger.error( "User: " + login.getPerson( ).getName( ) + " is not - Authorized for " + mnuUrl );
				auth.setAuthorized( false );
			}
			else {
				auth.setAuthorized( true );
			}
		}
		catch( ApplicationException e ) {
			e.printStackTrace( );
			auth.setAuthorized( false );
		}
		return auth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession#getMenus
	 * (br.com.mcampos.ejb.user.company.collaborator.Collaborator)
	 */
	@Override
	public List<Menu> getMenus( PrincipalDTO c ) throws ApplicationException
	{
		if( c == null )
			return Collections.emptyList( );
		Collaborator collaborator = find( c );
		return menuSession.getMenus( collaborator );
	}

	@Override
	public Collaborator merge( Collaborator newEntity )
	{
		if( newEntity.getId( ).getSequence( ) == null || newEntity.getId( ).getSequence( ).equals( 0 ) ) {
			newEntity.getId( ).setSequence( getNextId( Collaborator.maxSequence, newEntity.getCompany( ) ) );
		}
		return super.merge( newEntity );
	}

	public Collaborator add( Login login, Integer companyId )
	{
		Collaborator c = new Collaborator( );
		c.setPerson( login.getPerson( ) );
		c.setCompany( companySession.get( companyId ) );
		c.setCollaboratorType( getEntityManager( ).find( CollaboratorType.class, 2 ) );
		c.setCpsIdIn( 5 );
		c.setFromDate( new Date( ) );
		return merge( c );
	}
}
