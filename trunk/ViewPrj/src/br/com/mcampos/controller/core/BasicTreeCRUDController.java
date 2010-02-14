package br.com.mcampos.controller.core;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

public abstract class BasicTreeCRUDController<DTO> extends BasicCRUDController<Treeitem> implements TreeitemRenderer
{
    protected Tree treeList;


    protected abstract void showRecord( DTO record ) throws ApplicationException;

    protected abstract void copyTo( DTO dto );

    protected abstract DTO createDTO();

    public BasicTreeCRUDController()
    {
        super();
    }

    public BasicTreeCRUDController( char c )
    {
        super( c );
    }

    protected Treeitem getCurrentRecord()
    {
        if ( getTreeList().getSelectedCount() == 1 ) {
            return getTreeList().getSelectedItem();
        }
        else
            return null;
    }

    public void onSelect$treeList()
    {
        DTO record;

        if ( getTreeList().getSelectedCount() == 1 ) {
            try {
                record = getValue( getTreeList().getSelectedItem() );
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
    public void afterDelete( Treeitem currentRecord )
    {
        if ( currentRecord == null )
            return;
        currentRecord.detach();

    }

    @Override
    public void afterEdit( Treeitem record )
    {
        if ( isAddNewOperation() == false ) {
            DTO dto = getValue( record );
            Treecell cell = ( Treecell )record.getTreerow().getFirstChild();
            if ( cell != null )
                cell.setLabel( dto.toString() );
        }
    }

    protected void moveTreeItem( Treeitem target, Treeitem child )
    {
        Treechildren parent;

        child.detach();
        parent = target.getTreechildren();
        if ( parent == null ) {
            parent = new Treechildren();
            target.appendChild( parent );
        }
        parent.appendChild( child );
    }

    protected void updateRow( Treeitem item )
    {
        if ( item == null )
            return;
        DTO dto = getValue( item );
        Treecell cell = ( Treecell )item.getTreerow().getFirstChild();
        if ( cell != null )
            cell.setLabel( dto.toString() );
    }

    protected DTO getValue( Treeitem selecteItem )
    {
        if ( selecteItem == null )
            return null;
        DTO value = ( DTO )selecteItem.getValue();
        return value;
    }

    protected Treeitem saveRecord( Treeitem selectedItem )
    {
        if ( selectedItem != null ) {
            DTO dto = getValue( selectedItem );
            if ( dto != null )
                copyTo( dto );
        }
        return selectedItem;
    }

    protected Treeitem createNewRecord()
    {
        Treeitem newItem = new Treeitem();

        DTO dto = createDTO();
        copyTo( dto );
        try {
            render( newItem, dto );
            Treeitem parent = getParent( newItem );
            parent.getTreechildren().appendChild( newItem );
            return newItem;
        }
        catch ( Exception e ) {
            return null;
        }
    }


    protected Treeitem getParent( Treeitem child )
    {
        Treechildren root = treeList.getTreechildren();
        MenuDTO dto, childDTO;

        childDTO = ( ( MenuDTO )getValue( child ) );
        if ( SysUtils.isZero( childDTO.getParentId() ) ) {
            return ( Treeitem )root.getFirstChild();
        }
        for ( Treeitem item : root.getChildren() ) {
            dto = ( MenuDTO )getValue( item );
            if ( dto != null ) {
                if ( dto.getId().equals( childDTO.getParentId() ) )
                    return item;
            }
        }
        return ( Treeitem )root.getFirstChild();
    }
}
