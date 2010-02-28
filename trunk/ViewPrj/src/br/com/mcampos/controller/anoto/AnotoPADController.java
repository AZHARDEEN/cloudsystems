package br.com.mcampos.controller.anoto;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;


public class AnotoPADController extends AnotoBaseController<AnotoPageDTO>
{
    public static final String padIdParameterName = "padId";

    private PadDTO padParam;


    public AnotoPADController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        cmdCreate.setVisible( false );
        cmdDelete.setVisible( false );
    }

    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        Object param = getParameter();

        if ( param != null )
            return super.doBeforeCompose( page, parent, compInfo );
        else
            return null;
    }

    protected Object getParameter()
    {
        Object param;

        Map args = Executions.getCurrent().getArg();
        if ( args == null || args.size() == 0 )
            args = Executions.getCurrent().getParameterMap();
        param = args.get( padIdParameterName );
        if ( param instanceof PadDTO ) {
            padParam = ( PadDTO )param;
            return padParam;
        }
        else
            return null;
    }

    protected Integer getNextId()
    {
        return null;
    }

    protected SimpleTableDTO createDTO()
    {
        return null;
    }

    protected List getRecordList()
    {
        try {
            List list = getSession().getPages( getLoggedInUser(), padParam );
            return list;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "List de Páginas" );
            return Collections.emptyList();
        }
    }

    protected void delete( Listitem currentRecord )
    {
    }

    protected void insertItem( Listitem e )
    {
    }

    protected void updateItem( Listitem e )
    {
    }

    @Override
    protected void configure( Listitem item )
    {
        AnotoPageDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getPageAddress() ) );
        }
    }

    protected AnotoPageDTO getValue( Listitem item )
    {
        return ( AnotoPageDTO )item.getValue();
    }
}
