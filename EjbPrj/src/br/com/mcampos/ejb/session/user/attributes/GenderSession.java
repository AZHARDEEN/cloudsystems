package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.GenderDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface GenderSession
{
    void add( GenderDTO dto );
    void update( GenderDTO dto );
    void delete( Integer id );
    List<GenderDTO> getAll();
    GenderDTO get( Integer id);
    Integer getIdMaxValue ();
}
