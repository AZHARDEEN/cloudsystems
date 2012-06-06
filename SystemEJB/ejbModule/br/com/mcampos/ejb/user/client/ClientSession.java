package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.locale.City;
import br.com.mcampos.ejb.locale.state.State;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.person.gender.Gender;
import br.com.mcampos.ejb.user.person.title.Title;

@Remote
public interface ClientSession extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( Collaborator auth, DBPaging paging );

	Long countPerson( Collaborator auth );

	List<Client> getAllCompany( Collaborator auth, DBPaging paging );

	Long countCompany( Collaborator auth );

	public Users getUser( Collaborator auth, String document );

	public Users getUser( Collaborator auth, Integer id );

	List<Title> getTitle( Gender gender );

	List<State> getStates( String countryCode );

	List<City> getCities( State state );

}
