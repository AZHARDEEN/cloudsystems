package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.user.title.Title;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TitleSession
{
    void add( TitleDTO title );
    void update( TitleDTO title );
    void delete( Integer id );
    List<TitleDTO> getAll();
    TitleDTO get( Integer id );
    Integer getIdMaxValue ();
}
