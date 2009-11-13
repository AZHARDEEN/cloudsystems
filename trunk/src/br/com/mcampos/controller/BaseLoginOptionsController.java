package br.com.mcampos.controller;

import br.com.mcampos.controller.core.BaseController;

import java.util.List;

import org.zkoss.zk.ui.Component;

public class BaseLoginOptionsController extends BaseController
{
    protected static String alternativePath = "/index.zul";

    public BaseLoginOptionsController()
    {
        super();
    }

    public BaseLoginOptionsController( char c )
    {
        super( c );
    }

    public void onClick$cmdForgotPassword()
    {
        if ( rootParent != null )
            gotoPage( "/forgot_password.zul", rootParent.getParent() );
        else
            gotoPage( "/forgot_password.zul" );
    }

}
