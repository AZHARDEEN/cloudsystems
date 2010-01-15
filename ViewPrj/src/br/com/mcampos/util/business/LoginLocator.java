package br.com.mcampos.util.business;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.ejb.facade.LoginFacadeSession;

public class LoginLocator extends BusinessDelegate
{
    public LoginLocator()
    {
        super();
    }


    protected LoginFacadeSession getSessionBean ()
    {
        return ( LoginFacadeSession ) getEJBSession( LoginFacadeSession.class );
    }

    public void add ( RegisterDTO dto )
    {
        getSessionBean().add( dto );
    }

}
