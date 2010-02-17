package br.com.mcampos.util.business;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.facade.SystemFacade;

import java.util.Collections;
import java.util.List;

public class SystemLocator extends BusinessDelegate
{
    public SystemLocator()
    {
        super();
    }


    protected SystemFacade getSessionBean()
    {
        return ( SystemFacade )getEJBSession( SystemFacade.class );
    }

    protected SystemFacade get()
    {
        return getSessionBean();
    }
}
