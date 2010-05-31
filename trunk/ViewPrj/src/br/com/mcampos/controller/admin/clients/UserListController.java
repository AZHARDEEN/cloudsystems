package br.com.mcampos.controller.admin.clients;


import br.com.mcampos.controller.admin.users.BaseUserListController;
import br.com.mcampos.controller.admin.users.model.BaseListModel;
import br.com.mcampos.controller.core.PageBrowseHistory;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class UserListController extends BaseUserListController
{
    protected static final String personZulPage = "/private/user/person.zul";
    protected static final String companyZulPage = "/private/user/company.zul";


    public UserListController()
    {
        super();
    }

    public UserListController( char c )
    {
        super( c );
    }

    protected BaseListModel getModel( int activePage, int pageSize )
    {
        BusinessEntityListModel model = new BusinessEntityListModel( getLoggedInUser() );
        model.loadPage( activePage, pageSize );
        return model;
    }

    protected ListitemRenderer getRenderer()
    {
        return new BusinessEntityListRenderer();
    }

    protected void showInformation( Object obj ) throws ApplicationException
    {
        /*
        ListUserDTO dto = ( ListUserDTO )obj;
        UserDTO user = getUserLocator().getMyCompany( getLoggedInUser(), dto.getId() );
        if ( user != null ) {
            if ( user instanceof PersonDTO )
                showPersonInfo( ( PersonDTO )user );
            else
                showCompanyInfo( ( CompanyDTO )user );
        }
        */
    }

    public void onClick$cmdCreate()
    {
        Component root;
        Map args;

        root = getRootParent();
        args = new HashMap<String, Object>();
        args.put( "who", "businessEntity" );
        args.put( PageBrowseHistory.historyParamName,
                  new PageBrowseHistory( "/private/user/business_entity_list.zul", root.getParent(), execution.getArg() ) );
        gotoPage( "/private/user/company.zul", root != null ? root.getParent() : null, args );
    }

    public void onClick$cmdUpdate()
    {
        Listitem item;
        ListUserDTO dto;

        item = dataList.getSelectedItem();
        if ( item != null ) {
            dto = ( ListUserDTO )item.getValue();
            if ( dto != null ) {
                Component root;
                Map args;

                root = getRootParent();
                args = new HashMap<String, Object>();
                args.put( "who", "businessEntity" );
                args.put( PageBrowseHistory.historyParamName,
                          new PageBrowseHistory( "/private/user/business_entity_list.zul", root.getParent(),
                                                 execution.getArg() ) );
                args.put( "userId", dto.getId() );
                gotoPage( "/private/user/company.zul", root != null ? root.getParent() : null, args );
            }
        }
    }
}
