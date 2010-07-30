package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.SystemUserPropertyDTO;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.facade.SystemUserPropertyFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;


public class SystemUserParameterController extends SimpleTableController<SystemUserPropertyDTO>
{
    private SystemUserPropertyFacade session;
    private Combobox cmbType;
    private Label labelType;
    private Label labelEditType;

    /*Fields*/
    private Label recordType;

    public SystemUserParameterController()
    {
        super();
    }

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( ( SystemUserPropertyDTO )currentRecord ).getId() );
    }

    protected Object createNewRecord()
    {
        return new SystemUserPropertyDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( ( SystemUserPropertyDTO )e ) );
        else
            getSession().update( getLoggedInUser(), ( ( SystemUserPropertyDTO )e ) );
    }

    public SystemUserPropertyFacade getSession()
    {
        if ( session == null )
            session = ( SystemUserPropertyFacade )getRemoteSession( SystemUserPropertyFacade.class );
        return session;
    }

    @Override
    protected void clearRecordInfo()
    {
        super.clearRecordInfo();
        if ( cmbType != null ) {
            cmbType.setSelectedIndex( 0 );
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelType );
        labelEditType.setValue( labelType.getValue() );
        loadCombobox( cmbType, getSession().getTypes() );
        if ( cmbType != null && cmbType.getItemCount() > 0 )
            cmbType.setSelectedIndex( 0 );
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "systemUserParameterPageTitle" );
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        SystemUserPropertyDTO dto = ( SystemUserPropertyDTO )record;
        recordType.setValue( dto.getType().toString() );
    }

    @Override
    protected SimpleTableDTO prepareToUpdate( Object currentRecord )
    {
        SystemUserPropertyDTO dto = ( SystemUserPropertyDTO )super.prepareToUpdate( currentRecord );
        findType( dto.getType() );
        return dto;
    }

    private void findType( FieldTypeDTO dto )
    {
        if ( cmbType != null ) {
            for ( int index = 0; index < cmbType.getItemCount(); index++ ) {
                if ( cmbType.getItemAtIndex( index ).getValue().equals( dto ) ) {
                    cmbType.setSelectedIndex( index );
                    break;
                }
            }
        }
    }

    @Override
    protected Object saveRecord( Object object )
    {
        SystemUserPropertyDTO dto = ( SystemUserPropertyDTO )super.saveRecord( object );
        if ( cmbType.getSelectedItem() != null )
            dto.setType( ( FieldTypeDTO )cmbType.getSelectedItem().getValue() );
        return dto;
    }
}
