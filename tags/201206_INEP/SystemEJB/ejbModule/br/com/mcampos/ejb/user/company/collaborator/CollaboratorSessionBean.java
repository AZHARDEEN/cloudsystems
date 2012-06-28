package br.com.mcampos.ejb.user.company.collaborator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class CollaboratorSessionBean
 */
@Stateless( name = "CollaboratorSession", mappedName = "CollaboratorSession" )
@LocalBean
public class CollaboratorSessionBean extends SimpleSessionBean<Collaborator> implements CollaboratorSession, CollaboratorSessionLocal
{
	@EJB
	CompanySessionLocal companySession;

	@EJB
	LoginPropertySessionLocal propertySession;

	@Override
	protected Class<Collaborator> getEntityClass( )
	{
		return Collaborator.class;
	}

	@Override
	public Collaborator find( Login login, Integer companyId )
	{
		if ( login == null || companyId == null || login.getPerson( ) == null ) {
			return null;
		}
		Company company = this.companySession.get( companyId );
		if ( company == null ) {
			return null;
		}
		Collaborator c = getByNamedQuery( Collaborator.hasCollaborator, company, login.getPerson( ) );
		if ( c != null ) {
			c.getPerson( ).getAddresses( ).size( );
			c.getPerson( ).getDocuments( ).size( );
			c.getPerson( ).getContacts( ).size( );
		}
		return c;
	}

	@Override
	public List<SimpleDTO> getCompanies( Login auth ) throws ApplicationException
	{
		List<Collaborator> list;
		if ( auth == null || auth.getPerson( ) == null ) {
			return Collections.emptyList( );
		}
		try {
			list = findByNamedQuery( Collaborator.findCompanies, auth.getPerson( ) );
			if ( SysUtils.isEmpty( list ) ) {
				return Collections.emptyList( );
			}
			return toSimpleDTOList( list );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
			return Collections.emptyList( );
		}
	}

	private List<SimpleDTO> toSimpleDTOList( List<Collaborator> list )
	{
		if ( SysUtils.isEmpty( list ) ) {
			return Collections.emptyList( );
		}
		List<SimpleDTO> dtos = new ArrayList<SimpleDTO>( list.size( ) );
		for ( Collaborator item : list )
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
		return this.propertySession.getProperty( collaborator, propertyName );
	}

	@Override
	public void setProperty( Collaborator collaborator, String propertyName, String Value )
	{
		this.propertySession.setProperty( collaborator, propertyName, Value );
	}

	@Override
	public LoginProperty remove( Collaborator collaborator, String propertyName )
	{
		return this.propertySession.remove( collaborator, propertyName );
	}
}
