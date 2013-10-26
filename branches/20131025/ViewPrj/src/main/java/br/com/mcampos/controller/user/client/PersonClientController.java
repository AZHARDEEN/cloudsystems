package br.com.mcampos.controller.user.client;


import br.com.mcampos.dto.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;


public class PersonClientController extends ClientBaseController
{

    @SuppressWarnings( "compatibility:-2390854570248635172" )
    private static final long serialVersionUID = -3893126355676043221L;
    private Label labelPersonClientTitle;
    private static final String addClientPage = "/private/user/client/persist_person.zul";
    private static final String updateClientPage = "/private/user/client/update_person.zul";


    public PersonClientController( char c )
    {
        super( c );
    }

    public PersonClientController()
    {
        super();
    }

    protected List getList() throws ApplicationException
    {
        return getSession().getPeople( getLoggedInUser() );
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelPersonClientTitle );
    }

    public void onClick$cmdCreate()
    {
        gotoPage( addClientPage, getRootParent().getParent() );
    }

    protected void update()
    {
        gotoPage( updateClientPage, getRootParent().getParent() );
    }
}
