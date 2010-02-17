package br.com.mcampos.controller.admin.tables.core;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.util.business.SystemLocator;

import java.util.List;

public abstract class SimpleTableLocator extends SystemLocator
{
    public abstract List<SimpleTableDTO> getList( AuthenticationDTO currentUser );

    public SimpleTableLocator()
    {
        super();
    }
}
