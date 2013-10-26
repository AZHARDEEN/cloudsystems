package br.com.mcampos.controller.typing;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.typing.iab.facade.IABFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

public class IabProblemsController extends LoggedBaseController
{
    private IABFacade session;
    private Listbox listProblems;

    public IabProblemsController( char c )
    {
        super( c );
    }

    public IabProblemsController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listProblems.setItemRenderer( new IabProblemsListRenderer() );
        listProblems.setModel( new ListModelList( getSession().getProblems() ) );
    }

    @Override
    protected String getPageTitle()
    {
        return "Sistema de Digitação - IAB - Listagem de Problemas";
    }

    public IABFacade getSession()
    {
        if ( session == null )
            session = ( IABFacade )getRemoteSession( IABFacade.class );
        return session;
    }

}
