package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.facade.PenPageUserFacade;
import br.com.mcampos.util.system.SimpleSearchListBox;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;


public class PenUserSearchBox extends SimpleSearchListBox
{
    private AuthenticationDTO auth;
    private PenPageUserFacade session;
    private FormDTO form;

    public PenUserSearchBox( AuthenticationDTO auth, Component parent, FormDTO form )
    {
        super( "Colaboradores", "normal", true );
        this.auth = auth;
        this.form = form;
        setParent( parent );
        _title = "Colaboradores";
        _listHeader1 = "Nome";
        createBox();
    }

    protected List getList()
    {
        try {
            return getSession().getCollaborators( auth, form );
        }
        catch ( Exception e ) {
            return Collections.emptyList();
        }
    }

    protected PenPageUserFacade getSession()
    {
        if ( session == null )
            session = ( PenPageUserFacade )getRemoteSession( PenPageUserFacade.class );
        return session;
    }

    public static Object show( AuthenticationDTO auth, Component parent, FormDTO form )
    {
        return new PenUserSearchBox( auth, parent, form ).getSelected();
    }

}
