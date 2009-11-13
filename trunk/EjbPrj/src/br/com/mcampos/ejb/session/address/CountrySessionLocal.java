package br.com.mcampos.ejb.session.address;

import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.ejb.entity.address.Country;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CountrySessionLocal
{
    void add( CountryDTO dto );
    void update( CountryDTO dto );
    void delete( Integer id );
    List<CountryDTO> getAll();
    CountryDTO get( Integer id );
    Integer getIdMaxValue ();
}
