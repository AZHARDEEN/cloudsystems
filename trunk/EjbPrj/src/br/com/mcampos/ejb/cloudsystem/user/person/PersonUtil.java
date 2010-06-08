package br.com.mcampos.ejb.cloudsystem.user.person;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.GenderUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.TitleUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.user.Users;

public class PersonUtil extends UserUtil
{
    public PersonUtil()
    {
        super();
    }

    public static Person createEntity( PersonDTO dto )
    {
        if ( dto == null )
            return null;
        return update( new Person(), dto );
    }

    public static Person update( Person person, PersonDTO dto )
    {
        update( ( ( Users )person ), ( ( UserDTO )dto ) );
        person.setCivilState( CivilStateUtil.createEntity( ( dto.getCivilState() ) ) );
        person.setGender( GenderUtil.createEntity( dto.getGender() ) );
        person.setBirthDate( dto.getBirthDate() );
        person.setTitle( TitleUtil.createEntity( dto.getTitle() ) );
        person.setFirstName( dto.getFirstName() );
        person.setMiddleName( dto.getMiddleName() );
        person.setLastName( dto.getLastName() );
        person.setFatherName( dto.getFatherName() );
        person.setMotherName( dto.getMotherName() );
        person.setBornCity( DTOFactory.copy( dto.getBornCity() ) );
        return person;
    }


    public static Person copy( RegisterDTO dto )
    {
        Person person;

        person = new Person();
        person.setName( dto.getName() );
        DTOFactory.copyDocuments( person, dto );
        return person;
    }

    public static PersonDTO copy( Person person )
    {
        PersonDTO dto = new PersonDTO();
        update( ( UserDTO )dto, ( Users )person );
        dto.setBirthDate( person.getBirthDate() );
        dto.setBornCity( DTOFactory.copy( person.getBornCity() ) );
        dto.setFirstName( person.getFirstName() );
        dto.setMiddleName( person.getMiddleName() );
        dto.setLastName( person.getLastName() );
        dto.setFatherName( person.getFatherName() );
        dto.setMotherName( person.getMotherName() );
        dto.setCivilState( CivilStateUtil.copy( person.getCivilState() ) );
        dto.setGender( GenderUtil.copy( person.getGender() ) );
        dto.setTitle( TitleUtil.copy( person.getTitle() ) );
        return dto;
    }
}
