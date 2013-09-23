package br.com.mcampos.ejb.cloudsystem;

import java.util.List;

public interface EntityCopyListInterface<DTO, ENTITY> extends EntityCopyInterface<DTO>
{
    List<DTO> copyList( List<ENTITY> source );
}
