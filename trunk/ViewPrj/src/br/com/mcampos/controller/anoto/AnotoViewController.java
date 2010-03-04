package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.model.AnotoViewModel;
import br.com.mcampos.controller.anoto.renderer.AnotoViewRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;


public class AnotoViewController extends LoggedBaseController
{

    private Tree tree;
    private AnodeFacade session;


    public AnotoViewController( char c )
    {
        super( c );
    }

    public AnotoViewController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        List<FormDTO> list = getSession().getForms( getLoggedInUser() );
        tree.setTreeitemRenderer( new AnotoViewRenderer() );
        tree.setModel( new AnotoViewModel( getSession(), getLoggedInUser(), list ) );
    }

    protected void setSession( AnodeFacade session )
    {
        this.session = session;
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }
}
