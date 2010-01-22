package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.tables.TableController;
import br.com.mcampos.controller.admin.users.BaseUserListController;
import br.com.mcampos.controller.admin.users.model.BaseListModel;
import br.com.mcampos.controller.core.PageBrowseHistory;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import br.com.mcampos.util.business.UsersLocator;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

public class BusinessEntityController extends BaseUserListController
{
    protected static final String personZulPage = "/private/user/person.zul";
    protected static final String companyZulPage = "/private/user/company.zul";


    public BusinessEntityController()
    {
        super();
    }

    public BusinessEntityController( char c )
    {
        super( c );
    }

    protected BaseListModel getModel( int activePage, int pageSize )
    {
        BusinessEntityListModel model = new BusinessEntityListModel( getLoggedInUser().getUserId() );
        model.loadPage( activePage, pageSize );
        return model;
    }

    protected ListitemRenderer getRenderer()
    {
        return new BusinessEntityListRenderer();
    }

    protected void showInformation( Object obj )
    {
        /*
        ListUserDTO dto = ( ListUserDTO )obj;
        UserDTO user = getLocator().getBusinessEntity( dto.getId(), getLoggedInUser() );

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
                  new PageBrowseHistory ( "/private/user/business_entity_list.zul", root.getParent(), execution.getArg() ) 
                );
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
                          new PageBrowseHistory ( "/private/user/business_entity_list.zul", root.getParent(), execution.getArg() ) 
                        );
                args.put( "userId", dto.getId() );
                gotoPage( "/private/user/company.zul", root != null ? root.getParent() : null, args );
            }
        }
    }
}
