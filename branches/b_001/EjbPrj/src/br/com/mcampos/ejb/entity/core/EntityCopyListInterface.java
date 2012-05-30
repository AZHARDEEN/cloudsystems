package br.com.mcampos.ejb.entity.core;

import java.util.List;

public interface EntityCopyListInterface<DTO, ENTITY> extends EntityCopyInterface<DTO>
{
    List<DTO> copyList( List<ENTITY> source );
}
