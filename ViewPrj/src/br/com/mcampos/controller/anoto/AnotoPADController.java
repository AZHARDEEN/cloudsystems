package br.com.mcampos.controller.anoto;


import br.com.mcampos.dto.anoto.PadDTO;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;


public class AnotoPADController extends GenericForwardComposer
{
    public static final String padIdParameterName = "padId";

    private PadDTO padParam;

    public AnotoPADController( char c )
    {
        super( c );
    }

    public AnotoPADController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
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
}
