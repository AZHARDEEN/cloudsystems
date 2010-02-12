package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.core.BasicTreeCRUDController;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.util.business.BusinessDelegate;
import br.com.mcampos.util.business.SystemLocator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class MenuController extends BasicTreeCRUDController
{

    protected SystemLocator locator;


    Label recordId;
    Label recordDescription;
    Label recordParent;
    Label recordURL;
    Label recordSequence;
    Checkbox recordSeparator;
    Checkbox recordAutocheck;
    Checkbox recordChecked;
    Checkbox recordCheckmark;
    Checkbox recordDisabled;


    Intbox editId;
    Textbox editDescription;
    Intbox editParent;
    Textbox editURL;
    Intbox editSequence;
    Checkbox editSeparator;
    Checkbox editAutocheck;
    Checkbox editChecked;
    Checkbox editCheckmark;
    Checkbox editDisabled;


    public MenuController( char c )
    {
        super( c );
    }

    public MenuController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        getTreeList().setTreeitemRenderer( new MenuTreeRender( this ) );
        refresh();
    }


    protected SystemLocator getLocator()
    {
        if ( locator == null )
            locator = new SystemLocator();
        return locator;
    }

    protected void refresh()
    {
        getTreeList().setModel( new MenuTreeModel( getLocator().getMenuList( getLoggedInUser() ) ) );
    }

    protected Object getSingleRecord( Treeitem selecteItem )
    {
        if ( selecteItem == null )
            return null;
        Object value = selecteItem.getValue();
        if ( value instanceof MenuDTO )
            return value;
        else
            return null;
    }

    protected void showRecord( Object record )
    {
        if ( record instanceof MenuDTO ) {
            MenuDTO dto = ( MenuDTO )record;
            recordId.setValue( dto.getId().toString() );
            recordDescription.setValue( dto.getDescription() );
            if ( dto.getParent() != null )
                recordParent.setValue( dto.getParent().toString() );
            else
                recordParent.setValue( "" );
            recordURL.setValue( dto.getTargetURL() );
            recordSequence.setValue( dto.getSequence().toString() );
            recordSeparator.setChecked( dto.getSeparatorBefore() );
            recordAutocheck.setChecked( dto.getAutocheck() );
            recordChecked.setChecked( dto.getChecked() );
            recordCheckmark.setChecked( dto.getCheckmark() );
            recordDisabled.setChecked( dto.getDisabled() );
        }
    }

    protected void prepareToInsert( BusinessDelegate locator )
    {
        editId.setValue( 0 );
        editId.setReadonly( false );
        editDescription.setValue( "" );
        editParent.setValue( 0 );
        editURL.setValue( "" );
        editSequence.setValue( 0 );
        editSeparator.setChecked( false );
        editAutocheck.setChecked( false );
        editChecked.setChecked( false );
        editCheckmark.setChecked( false );
        editDisabled.setChecked( false );
    }

    protected void prepareToUpdate( BusinessDelegate locator, Object currentRecord )
    {
        if ( currentRecord instanceof MenuDTO ) {
            MenuDTO dto = ( MenuDTO )currentRecord;
            editId.setValue( dto.getId() );
            editId.setReadonly( true );
            editDescription.setValue( dto.getDescription() );
            //editParent dto.get;
            editURL.setValue( dto.getTargetURL() );
            editSequence.setValue( dto.getSequence() );
            editSeparator.setChecked( dto.getSeparatorBefore() );
            editAutocheck.setChecked( dto.getAutocheck() );
            editChecked.setChecked( dto.getChecked() );
            editCheckmark.setChecked( dto.getCheckmark() );
            editDisabled.setChecked( dto.getDisabled() );
        }
    }

    protected MenuDTO getMenuDTO( Treerow tr )
    {
        Component c = tr.getParent();

        if ( c instanceof Treeitem ) {
            Treeitem ti = ( Treeitem )c;
            Object objRec = ti.getValue();
            if ( objRec instanceof MenuDTO ) {
                return ( MenuDTO )objRec;
            }
        }
        return null;
    }

    public void onDrop( Event evt )
    {
        DropEvent de;

        if ( !( evt instanceof DropEvent ) )
            return;
        de = ( DropEvent )evt;
        if ( de.getTarget() instanceof Treerow ) {
            MenuDTO toDTO = getMenuDTO( ( Treerow )de.getTarget() );
            Object obj = de.getDragged();

            if ( obj instanceof Treerow && toDTO != null ) {
                MenuDTO fromDTO = getMenuDTO( ( Treerow )obj );

                fromDTO.setParent( toDTO );
                getLocator().updateMenu( getLoggedInUser(), fromDTO );
                refresh();
            }
        }
    }
}
