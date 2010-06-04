package br.com.mcampos.controller.admin.security.menu;

import br.com.mcampos.controller.core.BasicCRUDController;
import br.com.mcampos.exception.ApplicationException;

public class MenuTaskController extends BasicCRUDController
{
    public MenuTaskController()
    {
        super();
    }

    public MenuTaskController( char c )
    {
        super( c );
    }

    protected void refresh()
    {
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
    }

    protected void afterDelete( Object currentRecord )
    {
    }

    protected void afterPersist( Object currentRecord )
    {
    }

    protected Object saveRecord( Object currentRecord ) throws ApplicationException
    {
        return null;
    }

    protected void prepareToInsert()
    {
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        return null;
    }

    protected Object getCurrentRecord()
    {
        return null;
    }

    protected Object createNewRecord()
    {
        return null;
    }

    protected void persist( Object e ) throws ApplicationException
    {
    }
}
