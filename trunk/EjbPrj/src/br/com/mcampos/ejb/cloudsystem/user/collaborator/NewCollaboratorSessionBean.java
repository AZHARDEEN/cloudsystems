package br.com.mcampos.ejb.cloudsystem.user.collaborator;


import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.CollaboratorPK;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "NewCollaboratorSession", mappedName = "CloudSystems-EjbPrj-NewCollaboratorSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class NewCollaboratorSessionBean extends Crud<CollaboratorPK, Collaborator> implements NewCollaboratorSessionLocal
{
	@EJB
	private NewPersonSessionLocal personSession;

	public NewCollaboratorSessionBean()
	{
	}

	public void delete( CollaboratorPK key ) throws ApplicationException
	{
		delete( Collaborator.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Collaborator get( CollaboratorPK key ) throws ApplicationException
	{
		return get( Collaborator.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<Collaborator> get( Person person ) throws ApplicationException
	{
		if ( person == null )
			return Collections.emptyList();
		return ( List<Collaborator> )getResultList( Collaborator.findCompanies, person.getId() );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Collaborator get( Integer userId ) throws ApplicationException
	{
		Person person;
		person = personSession.get( userId );
		if ( person == null )
			return null;
		List<Collaborator> list = get( person );
		if ( SysUtils.isEmpty( list ) )
			return null;
		/*
	     * O colaborador possui vínculo ativo com mais de uma empresa
	     */
		if ( list.size() > 1 ) {
			/*
	         * TODO: vinculo com mais de uma empresa
	         */
			return null;
		}
		return list.get( 0 );
	}

}
