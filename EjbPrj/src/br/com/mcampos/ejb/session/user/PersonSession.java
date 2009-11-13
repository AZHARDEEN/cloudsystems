package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.entity.user.Person;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PersonSession
{
    Person add( PersonDTO person );
	Person createPersonForLogin( RegisterDTO dto );
	Person update( PersonDTO person );
    void delete( Integer id );
    List<PersonDTO> getAll();
    PersonDTO getByDocument(  String document );
    PersonDTO get( Integer userId );
}


