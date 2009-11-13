package br.com.mcampos.controller.admin.users;

import br.com.mcampos.controller.admin.users.model.BaseListModel;
import br.com.mcampos.controller.admin.users.model.PagingListModel;
import br.com.mcampos.controller.admin.users.renderer.UserListRenderer;


import br.com.mcampos.dto.user.ListUserDTO;

import br.com.mcampos.dto.user.PersonDTO;

import org.zkoss.zul.ListitemRenderer;

public class ListUsersController extends BaseUserListController
{
    
    public ListUsersController( char c )
    {
        super( c );
    }

    public ListUsersController()
    {
        super();
    }

    @Override
    protected BaseListModel getModel( int activePage, int pageSize )
    {
        return new PagingListModel ( activePage, pageSize );
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new UserListRenderer();
    }

    @Override
    protected void showInformation( Object obj )
    {
        ListUserDTO dto = ( ListUserDTO ) obj;
        PersonDTO person = getLocator().getPerson( dto.getId() );
        
        if ( person != null ) {
            showPersonInfo( person );
        }
    }
}
