package br.com.mcampos.ejb.cloudsystem.user.person.session;


import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PersonSessionLocal extends Serializable
{
    /**
     * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados.
     * @param dto DTO personDTO
     * @return Person EntityBean
     */
    Person add( PersonDTO dto );

    /**
     * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados. Esta funcao
     * recebe como parâmetro uma Entity que não está gerenciado pelo EJB.
     *
     *
     * @param entity EntityBean Não gerenciado (Not Managed)
     * @return Person EntityBean
     */
    Person add( Person entity );


    //Person update( PersonDTO person ) throws ApplicationException;

    void delete( Integer id );

    List<PersonDTO> getAll();

    PersonDTO getByDocument( String document );

    PersonDTO get( Integer userId );
}
