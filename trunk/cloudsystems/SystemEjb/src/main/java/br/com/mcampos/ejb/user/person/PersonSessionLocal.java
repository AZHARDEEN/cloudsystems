package br.com.mcampos.ejb.user.person;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;

@Local
public interface PersonSessionLocal extends BaseCrudSessionInterface<Person>
{
	Person getByDocument( String document );

	List<UserDocument> searchByDocument( Integer documentType, String lookFor );

	List<UserDocument> searchByCPF( String lookFor );

	List<UserDocument> searchByEmail( String lookFor );

}
