package br.com.mcampos.ejb.inep.packs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepPackage> implements InepPackageSession,
InepPackageSessionLocal
{
	@Override
	protected Class<InepPackage> getEntityClass( )
	{
		return InepPackage.class;
	}

	@Override
	public Collection<InepPackage> getAll( )
	{
		/*
		 * Deve esconder este metodo, pois o evento (InepPackage) depende da
		 * empresa.
		 */
		return Collections.emptyList( );
	}

	@Override
	public List<InepPackage> getAll( Authentication auth )
	{
		Collaborator c = getCollaboratorSession( ).find( auth );
		if ( c == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepPackage.getAll, c.getCompany( ) );
	}

}
