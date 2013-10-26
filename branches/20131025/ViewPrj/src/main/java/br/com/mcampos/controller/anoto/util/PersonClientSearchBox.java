package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.facade.AnotoPenFacade;
import br.com.mcampos.util.system.SimpleSearchListBox;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;


public class PersonClientSearchBox extends SimpleSearchListBox
{
    private AnotoPenFacade session;
    private AuthenticationDTO auth;


    public PersonClientSearchBox( AuthenticationDTO auth, Component parent )
    {
        super( "Usuário", "normal", true );
        this.auth = auth;
        setParent( parent );
        _title = "Usuário";
        _listHeader1 = "Nome";
        createBox();
    }


    protected AnotoPenFacade getSession()
    {
        if ( session == null )
            session = ( AnotoPenFacade )getRemoteSession( AnotoPenFacade.class );
        return session;
    }


    @Override
    protected List getList()
    {
        return Collections.emptyList();
    }


    public static Object show( AuthenticationDTO auth, Component parent )
    {
        return new PersonClientSearchBox( auth, parent ).getSelected();
    }
}
