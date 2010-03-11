package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.controller.core.BasicTreeCRUDController;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class TaskController extends BasicTreeCRUDController<TaskDTO>
{
    protected TaskLocator locator;

    Label recordId;
    Label recordDescription;
    Label recordParent;


    Intbox editId;
    Textbox editDescription;
    Intbox editParent;

    public TaskController( char c )
    {
        super( c );
    }

    public TaskController()
    {
        super();
    }

    protected void showRecord( TaskDTO record )
    {
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue( record.getDescription() );
        if ( record.getParent() != null )
            recordParent.setValue( record.getParent().toString() );
        else
            recordParent.setValue( "" );
    }

    protected TaskDTO copyTo( TaskDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setParentId( editParent.getValue() );
        return dto;
    }

    protected TaskDTO createDTO()
    {
        return new TaskDTO();
    }

    protected void configureTreeitem( Treeitem item )
    {
        TaskDTO data = getValue( item );
        Treerow row;

        row = item.getTreerow();
        row.appendChild( new Treecell( data.toString() ) );
        item.setOpen( true );
        row.setDraggable( "true" );
        row.setDroppable( "true" );
        row.addEventListener( Events.ON_DROP, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    onDrop( event );
                }
            } );
    }

    protected Treeitem getParent( Treeitem newChild )
    {
        return null;
    }

    protected void refresh()
    {
        try {
            getTreeList().setModel( new TaskTreeModel( getLocator().getTasks( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Refresh Menu" );
        }
    }

    protected void delete( Object currentRecord )
    {
        if ( currentRecord == null )
            return;

        TaskDTO dto = getValue( (Treeitem)currentRecord );
        if ( dto != null ) {
            try {
                getLocator().delete( getLoggedInUser(), dto );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Exclur Task" );
            }
        }
    }

    protected void prepareToInsert()
    {
        editId.setRawValue( getNextId() );
        editId.setReadonly( false );
        editDescription.setFocus( true );
        editDescription.setValue( "" );
        editParent.setRawValue( 0 );
    }

    protected int getNextId()
    {
        try {
            return getLocator().getNextTaskId( getLoggedInUser() );
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }

    }


    protected TaskDTO prepareToUpdate( Object currentRecord )
    {
        TaskDTO dto = null;

        dto = getValue( (Treeitem)currentRecord );
        if ( dto != null ) {
            editId.setValue( dto.getId() );
            editId.setReadonly( true );
            editDescription.setValue( dto.getDescription() );
            editDescription.setFocus( true );
            editParent.setValue( dto.getParent() == null ? 0 : dto.getParent().getId() );
        }
        return dto;
    }

    protected void persist( Object e )
    {
        try {
            TaskDTO dto = getLocator().add( getLoggedInUser(), getValue( (Treeitem)e ) );
            if ( dto != null )
                ((Treeitem)e).setValue( dto );
        }
        catch ( ApplicationException ex ) {
            showErrorMessage( ex.getMessage(), "Inserir" );
        }
    }

    protected void updateItem( Object e )
    {
        try {
            TaskDTO dto = getLocator().update( getLoggedInUser(), getValue( ((Treeitem)e) ) );
            if ( dto != null )
                ((Treeitem)e).setValue( dto );
        }
        catch ( ApplicationException ex ) {
            showErrorMessage( ex.getMessage(), "Atualizar" );
        }
    }

    protected TaskLocator getLocator()
    {
        if ( locator == null )
            locator = new TaskLocator();
        return locator;
    }


    public void onDrop( Event evt )
    {

    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        getTreeList().setTreeitemRenderer( this );
        refresh();
    }
}
