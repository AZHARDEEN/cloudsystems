package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.base.BaseSearchController;

import java.util.Properties;

import org.zkoss.zk.ui.Component;


public class AnotoQualityController extends BaseSearchController
{
    public AnotoQualityController( char c )
    {
        super( c );
    }

    public AnotoQualityController()
    {
        super();
    }

    @Override
    protected void loadPGC( Properties prop )
    {
        prop.put( "revisedStatus", "1" );
        prop.remove( "custom_fields" );
        super.loadPGC( prop );
    }


    @Override
    protected void gotoPage( Properties params )
    {
        gotoPage( "/private/admin/anoto/anoto_quality_verify.zul", getRootParent().getParent(), params );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        labelFormView2Title.setValue( getLabel( "qualityControl" ) );
        getExportButton().setVisible( false );
        btnSummary.setVisible( false );
    }
}
