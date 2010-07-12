package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.controller.anoto.model.AnotoPageFieldComparator;
import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Column;

public class GridFieldController extends BaseController
{
    private Column headerName;
    private Column headerType;
    private Column headerIcr;
    private Column headerExport;
    private Column headerSearch;

    public GridFieldController()
    {
        super();
    }

    public GridFieldController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        if ( headerIcr != null ) {
            headerIcr.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerIcr ) );
            headerIcr.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerIcr ) );
        }
        if ( headerType != null ) {
            headerType.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerType ) );
            headerType.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerType ) );
        }
        if ( headerName != null ) {
            headerName.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerName ) );
            headerName.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerName ) );
        }
        setLabel( headerName );
        setLabel( headerType );
        setLabel( headerIcr );
        setLabel( headerExport );
        setLabel( headerSearch );
    }
}
