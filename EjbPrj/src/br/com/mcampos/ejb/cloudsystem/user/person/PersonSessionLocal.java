package br.com.mcampos.ejb.cloudsystem.user.person;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.PersonDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface PersonSessionLocal
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
    
    
    
    Person update( PersonDTO person );
    void delete( Integer id );
    List<PersonDTO> getAll();
    PersonDTO getByDocument(  String document );
    PersonDTO get( Integer userId );
}
