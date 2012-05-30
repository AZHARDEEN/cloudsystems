package br.com.mcampos.ejb.user.company.collaborator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class CollaboratorSessionBean
 */
@Stateless(name = "CollaboratorSession", mappedName = "CollaboratorSession")
@LocalBean
public class CollaboratorSessionBean extends SimpleSessionBean<Collaborator> implements CollaboratorSession, CollaboratorSessionLocal {

	@EJB
	PersonSessionLocal personSession;

	@EJB
	CompanySessionLocal companySession;

	@Override
	protected Class<Collaborator> getEntityClass( )
	{
		return Collaborator.class;
	}

	@Override
	public Collaborator find( Authentication auth )
	{
		Person person = getPerson( auth );
		if ( person == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyId( ) );
		if ( company == null) {
			return null;
		}
		return getByNamedQuery( Collaborator.hasCollaborator, company, person );
	}

	@Override
	public List<SimpleDTO> getCompanies( Authentication auth ) throws ApplicationException
	{
		Person person = getPerson( auth );
		try {
			List<Collaborator> list = findByNamedQuery( Collaborator.findCompanies, person );
			if ( SysUtils.isEmpty( list )) {
				return Collections.emptyList( );
			}
			return toSimpleDTOList (list);
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
			return Collections.emptyList( );
		}
	}


	private List<SimpleDTO> toSimpleDTOList ( List<Collaborator> list)
	{
		if ( SysUtils.isEmpty( list )) {
			return Collections.emptyList( );
		}
		List<SimpleDTO> dtos = new ArrayList<SimpleDTO>( list.size( ) );
		for ( Collaborator item : list )
		{
			String name = SysUtils.isEmpty( item.getCompany( ).getNickName( ) ) ? item.getCompany( ).getName( ) : item.getCompany( ).getNickName( );
			dtos.add( new SimpleDTO ( item.getCompany( ).getId( ), name ) );
		}
		return dtos;
	}


	private Person getPerson ( Authentication auth )
	{
		Person person = this.personSession.get(  auth.getUserId( ) );
		return person;
	}


}
