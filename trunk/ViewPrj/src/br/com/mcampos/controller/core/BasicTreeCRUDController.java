package br.com.mcampos.controller.core;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.BusinessDelegate;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

public abstract class BasicTreeCRUDController extends BasicCRUDController
{
    protected Tree treeList;


    protected abstract Object getSingleRecord( Treeitem selectedItem ) throws ApplicationException;

    protected abstract void showRecord( Object record );

    public BasicTreeCRUDController()
    {
        super();
    }

    public BasicTreeCRUDController( char c )
    {
        super( c );
    }

    protected void delete( BusinessDelegate locator, Object currentRecord )
    {
    }

    protected Object saveRecord( BusinessDelegate locator ) throws ApplicationException
    {
        return null;
    }

    protected Object getCurrentRecord()
    {
        if ( getTreeList().getSelectedCount() == 1 ) {
            return getTreeList().getSelectedItem();
        }
        else
            return null;
    }

    protected void insertItem( Object e )
    {
    }

    protected void updateItem( Object e, Boolean bNew )
    {
    }

    public void onSelect$treeList()
    {
        Object record;

        if ( getTreeList().getSelectedCount() == 1 ) {
            try {
                record = getSingleRecord( getTreeList().getSelectedItem() );
                if ( record != null )
                    showRecord( record );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Obter Informações do Registro" );
            }
        }
    }

    public void setTreeList( Tree treeList )
    {
        this.treeList = treeList;
    }

    public Tree getTreeList()
    {
        return treeList;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        if ( getTreeList() == null )
            showErrorMessage( "A tree list está vazia ou nula", "Erro Interno" );
    }
}
