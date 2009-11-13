package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class MenuController extends TableController
{
    public MenuController()
    {
        super();
    }

    public MenuController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return Collections.emptyList();
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
    }

    protected Object getSingleRecord( Object id )
    {
        return null;
    }

    protected void showRecord( Object record )
    {
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        return null;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item )
    {
    }
}
