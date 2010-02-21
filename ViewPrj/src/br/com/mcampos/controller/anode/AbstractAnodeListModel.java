package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.anode.FormDTO;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAnodeListModel extends AbstractPagingListModel<FormDTO>
{
    private AnodeFacade session;
    AuthenticationDTO auth;

    public AbstractAnodeListModel( AnodeFacade session, AuthenticationDTO auth )
    {
        setSession( session );
        setAuth( auth );
    }

    public void setSession( AnodeFacade session )
    {
        this.session = session;
    }

    public AnodeFacade getSession()
    {
        return session;
    }

    public void setAuth( AuthenticationDTO auth )
    {
        this.auth = auth;
    }

    public AuthenticationDTO getAuth()
    {
        return auth;
    }
}
