package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.controller.admin.security.SecutityBaseController;
import br.com.mcampos.controller.admin.system.config.task.TaskListRenderer;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;


public class RoleController extends SecutityBaseController
{

    private Label labelId;
    private Label labelDescription;
    private Label labelParent;

    private Intbox editId;
    private Textbox editDescription;
    private Combobox comboParent;

    private Listbox freeTasks;
    private Listbox listTasks;

    public RoleController( char c )
    {
        super( c );
    }

    public RoleController()
    {
        super();
    }

    protected void refresh ()
    {
        RoleDTO dto;

        getTree().setTreeitemRenderer( new RoleRenderer() );
        try {
            dto = getSession().getRootRole( getLoggedInUser() );
            getTree().setModel( new RoleModel ( getSession(), getLoggedInUser(), dto ) );
            comboParent.setItemRenderer(  new RoleItemRenderer() );
            comboParent.setModel( new ListModelList ( getSession().getRoles( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Role" );
        }
    }

    protected void showRecord( Object record )
    {
        RoleDTO dto = ( RoleDTO )record;

        labelDescription.setValue(  dto.getDescription() );
        labelId.setValue( dto.getId().toString( ) );
        labelParent.setValue( dto.getParent() != null ? dto.getParent().toString() : "" );

        try {
            loadTasks ( dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "LoadTasks" );
        }
    }

    protected void loadTasks ( RoleDTO dto ) throws ApplicationException
    {
        List<TaskDTO> tasks = getSession().getTasks ( getLoggedInUser(), dto );
        listTasks.getItems().clear();
        ListModelList model = new ListModelList (tasks, false);
        listTasks.setModel( model );
    }

    protected void clearRecord()
    {
        labelDescription.setValue(  "" );
        labelId.setValue( "" );
        labelParent.setValue( "" );
        editId.setRawValue( new Integer (0) );
        editDescription.setValue( "" );
        updateComboParent( null );
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete ( getLoggedInUser(), ((RoleDTO) currentRecord));
    }

    protected void afterDelete( Object currentRecord )
    {
        ListModelList model = ( ListModelList ) comboParent.getModel();
        RoleModel treeModel = (RoleModel)getTree().getModel();

        model.remove( currentRecord );
        treeModel.delete( ((RoleDTO) currentRecord) );
    }

    protected void afterPersist( Object currentRecord )
    {
        ListModelList model = ( ListModelList ) comboParent.getModel();
        RoleModel treeModel = (RoleModel)getTree().getModel();
        if ( isAddNewOperation() ) {
            model.add( currentRecord );
            treeModel.add( (RoleDTO) currentRecord );
        }
        else {
            int nIndex = model.indexOf( currentRecord );
            model.set( nIndex, currentRecord );
            treeModel.update( (RoleDTO) currentRecord );
        }
    }

    protected Object saveRecord( Object currentRecord )
    {
        RoleDTO dto = ( RoleDTO )currentRecord;
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setParent( ( RoleDTO )comboParent.getSelectedItem().getValue() );
        return currentRecord;
    }

    protected void prepareToInsert()
    {
        clearRecord();
        editId.setReadonly( false );
        editId.setDisabled( false );
        try {
            editId.setValue( getSession().getRoleMaxId( getLoggedInUser() ) );
            editDescription.setFocus( true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Role" );
            editId.setValue( 1 );
        }
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        RoleDTO dto = ( RoleDTO )currentRecord;

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
        editDescription.setFocus( true );
        updateComboParent( dto.getParent() );
        return currentRecord;
    }

    protected void updateComboParent ( RoleDTO parent )
    {
        if ( parent == null )
            parent = new RoleDTO ( 1 );
        ListModelList model = ( ListModelList ) comboParent.getModel();
        int nIndex = model.indexOf( parent );
        comboParent.setSelectedIndex( nIndex );
    }

    protected Object createNewRecord()
    {
        return new RoleDTO ();
    }

    protected void persist( Object obj ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), (RoleDTO) obj );
        else
            getSession().update( getLoggedInUser(), (RoleDTO) obj );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listTasks.setItemRenderer( new TaskListRenderer() );
    }
}
