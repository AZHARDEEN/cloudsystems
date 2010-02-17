package br.com.mcampos.controller.core;

import br.com.mcampos.exception.ApplicationException;


import java.util.TreeMap;

import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.impl.XulElement;

/**
 * Classe base para CRUD em uma tree.
 * Neste modelo de desenvolvimento optou-se pelo uso do pattern DTO.
 * @param <DTO>
 */
public abstract class BasicTreeCRUDController<DTO> extends BasicCRUDController<Treeitem> implements TreeitemRenderer
{
    protected Tree treeList;
    protected TreeMap treeMap;

    /**
     * Quando um item da tree é selecionado, o implementador desta função abstrata deve mostrar todas as informações
     * pertinentes ao registro. Em alguns casos pode-se recorrer ao banco de dados para obter mais informações.
     *
     * @param record Indica o registro selecionado. Neste caso não é repassado o treeitem, mas o seu value associado.
     * @throws ApplicationException
     */
    protected abstract void showRecord( DTO record ) throws ApplicationException;


    protected abstract DTO copyTo( DTO dto );

    protected abstract DTO createDTO();

    protected abstract void configureTreeitem( Treeitem item );

    protected abstract Treeitem getParent( Treeitem newChild );

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
        removeNode( currentRecord );
    }

    @Override
    public void afterEdit( Treeitem record )
    {
        Object dto = getValue( record );
        if ( isAddNewOperation() == false ) {
            Treecell cell = ( Treecell )record.getTreerow().getFirstChild();
            if ( cell != null )
                cell.setLabel( dto.toString() );
            getTreeMap().remove( dto );
        }
        getTreeMap().put( dto, record );
    }


    protected void addNode( XulElement parent, Treeitem child )
    {
        if ( parent == null || child == null )
            return;

        Treechildren tc;
        if ( parent instanceof Tree )
            tc = ( ( Tree )parent ).getTreechildren();
        else if ( parent instanceof Treeitem )
            tc = ( ( Treeitem )parent ).getTreechildren();
        else
            return;
        if ( tc == null ) {
            tc = new Treechildren();
            parent.appendChild( tc );
        }
        tc.appendChild( child );
    }


    protected void removeNode( Treeitem child )
    {
        Treeitem parentItem = child.getParentItem();

        child.detach();
        if ( parentItem != null && parentItem.getTreechildren() != null ) {
            if ( parentItem.getTreechildren().getChildren().isEmpty() ) {
                Treechildren tc = parentItem.getTreechildren();
                if ( tc != null )
                    tc.detach();
            }
        }
        if ( parentItem != null )
            parentItem.invalidate();
        else
            getTreeList().invalidate();
    }


    protected void moveTreeItem( Treeitem target, Treeitem child )
    {
        removeNode( child );
        addNode( target, child );
    }

    protected void updateRow( Treeitem item )
    {
        if ( item == null )
            return;
        Object dto = getValue( item );
        Treecell cell = ( Treecell )item.getTreerow().getFirstChild();
        if ( cell != null )
            cell.setLabel( dto.toString() );
    }

    protected DTO getValue( Treeitem selecteItem )
    {
        if ( selecteItem == null )
            return null;
        return ( ( DTO )selecteItem.getValue() );
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
            if ( parent != null )
                addNode( parent, newItem );
            else
                addNode( getTreeList(), newItem );
            return newItem;
        }
        catch ( Exception e ) {
            return null;
        }
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        Treerow tr = null;


        item.setValue( data );
        if ( item.getTreerow() == null ) {
            tr = new Treerow();
            tr.setParent( item );
        }
        else {
            tr = item.getTreerow();
            tr.getChildren().clear();
        }
        configureTreeitem( item );
        getTreeMap().put( data, item );
    }


    public TreeMap getTreeMap()
    {
        if ( treeMap == null )
            treeMap = new TreeMap();
        return treeMap;
    }

}
