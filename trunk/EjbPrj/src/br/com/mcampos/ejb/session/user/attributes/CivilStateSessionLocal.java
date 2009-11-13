package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.entity.user.attributes.CivilState;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CivilStateSessionLocal
{
    public void add( CivilStateDTO dto );
    void update( CivilStateDTO dto );
    void delete( Integer id );

    List<CivilStateDTO> getAll();
    CivilStateDTO get( Integer id );
    Integer getMaxPKValue ();
}
