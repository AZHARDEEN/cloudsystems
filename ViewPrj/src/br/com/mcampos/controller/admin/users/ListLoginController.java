package br.com.mcampos.controller.admin.users;

import br.com.mcampos.controller.admin.users.model.BaseListModel;
import br.com.mcampos.controller.admin.users.model.LoginPagingListModel;
import br.com.mcampos.controller.admin.users.model.PagingListModel;
import br.com.mcampos.controller.admin.users.renderer.LoginListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;

import br.com.mcampos.dto.user.login.ListLoginDTO;

import br.com.mcampos.dto.user.PersonDTO;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

public class ListLoginController extends BaseUserListController
{
    protected Button cmdDelete;
    
    public ListLoginController( char c )
    {
        super( c );
    }

    public ListLoginController()
    {
        super();
    }

    @Override
    protected BaseListModel getModel( int activePage, int pageSize )
    {
        return new LoginPagingListModel( activePage, pageSize );
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new LoginListRenderer(); 
    }

    @Override
    protected void showInformation( Object obj )
    {
        if ( obj == null )
            return;
        
        ListLoginDTO dto = ( ListLoginDTO ) obj;
        
        setUserStatus ( dto.getUserStatus().getDisplayName() );
        PersonDTO person = getLocator().getPerson( dto.getId() );
        
        if ( person != null ) {
            showPersonInfo( person );
        }
    }
    
    public void onClick$cmdDelete () {
        Set<Listitem> items;
        Integer []logins;
        int nIndex = 0;
        ListLoginDTO dto;
            
        items = getDataList().getSelectedItems();
        if ( items == null || items.size() == 0 )
            return;
        logins = new Integer[ items.size() ];
        for ( Listitem item: items ) {
            dto = ( ListLoginDTO ) item.getValue();
            if ( dto != null )
            logins[ nIndex ++ ] = dto.getId();
        }
        //getLocator().deleteLogins( logins );
        refreshModel( getStartPageNumber() );
    }

}
