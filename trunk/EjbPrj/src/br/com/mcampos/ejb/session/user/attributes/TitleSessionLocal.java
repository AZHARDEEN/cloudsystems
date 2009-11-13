package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.entity.user.attributes.Title;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TitleSessionLocal
{
    void add( TitleDTO title );
    void update( TitleDTO title );
    void delete( Integer id );
    List<TitleDTO> getAll();
    TitleDTO get( Integer id );
    Integer getIdMaxValue ();
}
