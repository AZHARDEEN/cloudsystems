package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.base.BaseSearchController;

import java.util.Properties;

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
        super.loadPGC( prop );
    }


    protected void gotoPage( Properties params )
    {
        gotoPage( "/private/admin/anoto/anoto_quality_verify.zul", getRootParent().getParent(), params );
    }
}