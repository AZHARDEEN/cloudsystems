package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.users.BaseUserListController;
import br.com.mcampos.controller.admin.users.model.BaseListModel;

import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;

import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;

import com.sun.java.swing.plaf.windows.resources.windows;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class ListClientsController extends BaseUserListController
{
    protected static final String personZulPage = "/private/user/person.zul";
    protected static final String companyZulPage = "/private/user/person.zul";
    
    public ListClientsController()
    {
        super();
    }

    public ListClientsController( char c )
    {
        super( c );
    }

    protected BaseListModel getModel( int activePage, int pageSize )
    {
        return new ClientListModel( activePage, pageSize );
    }

    protected ListitemRenderer getRenderer()
    {
        return new ClientsListRenderer();
    }

    protected void showInformation( Object obj )
    {
        ListUserDTO dto = ( ListUserDTO ) obj;
        UserDTO user = getLocator().getUser( dto.getId() );
        
        if ( user != null ) {
            if ( user instanceof PersonDTO )
                showPersonInfo( (PersonDTO) user );
            else
                showCompanyInfo ( (CompanyDTO ) user );
        }
    }
    
    public void onClick$addPerson ()
    {
        gotoPage ( personZulPage + "?who=newClient" );
    }

    public void onClick$addCompany ()
    {
        gotoPage ( companyZulPage + "?who=newClient" );
    }


    public void onClick$updateClient () throws IOException
    {
        Listitem item = getDataList().getSelectedItem();
        ListUserDTO dto;
        
        if ( item == null )
            return;
        dto = (ListUserDTO)item.getValue();
        if ( dto != null )  {
            setParameter( "client_id", dto.getId() );
            if ( dto.getUserType().getId() == 1 )
                gotoPage ( personZulPage + "?who=updateClient" );
            else
                gotoPage( companyZulPage + "?who=updateClient" );
        }
    }
}
