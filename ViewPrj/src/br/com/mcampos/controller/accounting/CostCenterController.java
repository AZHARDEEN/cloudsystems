package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.accounting.CostCenterDTO;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.facade.CostCenterFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listheader;


public class CostCenterController extends SimpleTableController<CostCenterDTO>
{
    private CostCenterFacade session;
    private Combobox cmbOwner;
    private Label labelCostArea;

    private Listheader headerCode;

    public CostCenterController()
    {
        super();
    }

    protected Integer getNextId() throws ApplicationException
    {
        CostAreaDTO area = getArea();
        if ( area != null ) {
            return getSession().nextId( getLoggedInUser(), area.getId() );
        }
        else
            return 1;
    }

    protected List getRecordList() throws ApplicationException
    {
        CostAreaDTO area = getArea();
        if ( area != null )
            return getSession().getAll( getLoggedInUser(), area.getId() );
        else
            return Collections.emptyList();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        CostAreaDTO area = getArea();
        if ( area != null ) {
            CostCenterDTO center = ( CostCenterDTO )currentRecord;
            getSession().delete( getLoggedInUser(), area.getId(), center.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new CostCenterDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        CostAreaDTO area = getArea();
        if ( area != null ) {
            if ( isAddNewOperation() ) {
                getSession().add( getLoggedInUser(), ( CostCenterDTO )e );
            }
            else
                getSession().update( getLoggedInUser(), ( CostCenterDTO )e );
        }
    }

    public CostCenterFacade getSession()
    {
        if ( session == null )
            session = ( CostCenterFacade )getRemoteSession( CostCenterFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "costCenterPageTitle" );
    }

    private void setLabels()
    {
        setLabel( labelCostArea );
        setLabel( headerCode );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
        loadCombobox( cmbOwner, getSession().getCostAreas( getLoggedInUser() ) );
        if ( cmbOwner.getItemCount() > 0 ) {
            cmbOwner.setSelectedIndex( 0 );
            refresh();
        }
    }

    public void onSelect$cmbOwner()
    {
        refresh();
    }

    private CostAreaDTO getArea()
    {
        if ( cmbOwner == null || cmbOwner.getSelectedItem() == null )
            return null;
        return ( CostAreaDTO )cmbOwner.getSelectedItem().getValue();
    }


    @Override
    protected void refresh()
    {
        if ( getArea() != null )
            super.refresh();
    }

    @Override
    protected Object saveRecord( Object object )
    {
        CostAreaDTO area = getArea();
        if ( area != null ) {
            CostCenterDTO c = ( CostCenterDTO )object;
            c.setArea( area );
            return super.saveRecord( object );
        }
        else
            return object;
    }
}
