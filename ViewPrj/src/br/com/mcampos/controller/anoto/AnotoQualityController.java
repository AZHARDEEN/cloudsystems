package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.base.BaseSearchController;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;
import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModelList;


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
        List<AnotoResultList> dtos;
        try {
            Integer id = Integer.parseInt( cmbMaxRecords.getSelectedItem().getLabel() );
            dtos = getSession().getAllPgcPenPage( getLoggedInUser(), prop, id, false );
            if ( SysUtils.isEmpty( dtos ) == false ) {
                for ( AnotoResultList item : dtos ) {
                    item.clearFields();
                }
            }
            ListModelList model = getModel();
            model.clear();
            model.addAll( dtos );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
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
        if ( labelFormView2Title != null )
            labelFormView2Title.setValue( getLabel( "qualityControl" ) );
        if ( getExportButton() != null )
            getExportButton().setVisible( false );
        if ( btnSummary != null )
            btnSummary.setVisible( false );
    }
}
