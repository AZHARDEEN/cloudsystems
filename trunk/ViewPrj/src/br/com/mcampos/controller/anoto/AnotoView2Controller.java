package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.ComboFormRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPageRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPenRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcPenPageListRenderer;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Timebox;


public class AnotoView2Controller extends AnotoLoggedController
{
    protected Combobox cmbApplication;
    protected Combobox cmbAnotoPage;
    protected Combobox cmbPen;
    protected Datebox initDate;
    protected Timebox initTime;
    protected Datebox endDate;
    protected Timebox endTime;
    protected Listbox resultList;

    public AnotoView2Controller( char c )
    {
        super( c );
    }

    public AnotoView2Controller()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        refresh();
    }

    protected void refresh()
    {
        loadApplication();
        loadPages( null );
        loadPens( null );
        loadPGC( null );
    }

    protected void loadApplication()
    {
        ListModelList model;
        List list;
        try {
            list = getSession().getForms( getLoggedInUser() );
            model = new ListModelList( list );
            cmbApplication.setItemRenderer( new ComboFormRenderer() );
            cmbApplication.setModel( model );
            if ( list.size() > 0 && cmbApplication.getItemCount() > 0 )
                cmbApplication.setSelectedIndex( 0 );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Aplicações" );
        }
    }


    protected void loadPages( FormDTO dto )
    {
        SimpleListModel model;
        List<AnotoPageDTO> list;
        try {
            if ( dto == null )
                list = getSession().getPages( getLoggedInUser() );
            else
                list = getSession().getPages( getLoggedInUser(), dto );
            model = new SimpleListModel( list );
            cmbAnotoPage.setItemRenderer( new ComboPageRenderer() );
            cmbAnotoPage.setModel( model );
            cmbAnotoPage.invalidate();
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Páginas" );
        }
    }


    protected void loadPens( FormDTO dto )
    {
        ListModelList model;
        List list;
        try {
            if ( dto == null )
                list = getSession().getPens( getLoggedInUser() );
            else
                list = getSession().getPens( getLoggedInUser() );
            model = new ListModelList( list );
            cmbPen.setItemRenderer( new ComboPenRenderer() );
            cmbPen.setModel( model );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Canetas" );
        }
    }

    public void onSelect$cmbApplication()
    {
        if ( cmbApplication.getSelectedItem() != null )
            loadPages( ( FormDTO )cmbApplication.getSelectedItem().getValue() );
        else
            loadPages( null );
    }

    protected void loadPGC( Properties prop )
    {
        List<PgcPenPageDTO> dtos;
        try {
            dtos = getSession().getAllPgcPenPage( getLoggedInUser(), prop );
            ListModelList model = new ListModelList( dtos );
            resultList.setItemRenderer( new PgcPenPageListRenderer() );
            resultList.setModel( model );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
    }

    public void onClick$btnFilter()
    {
        Properties prop = new Properties();

        if ( cmbApplication.getSelectedItem() != null ) {
            prop.put( "form", cmbApplication.getSelectedItem().getValue() );
        }

        String strPage = "";
        if ( cmbAnotoPage.getSelectedItem() != null ) {
            AnotoPageDTO page;

            page = ( AnotoPageDTO )cmbAnotoPage.getSelectedItem().getValue();
            strPage = page.getPageAddress();
        }
        else if ( cmbAnotoPage.getText() != null ) {
            strPage = cmbAnotoPage.getText();
        }
        if ( strPage.length() > 0 )
            prop.put( "page", strPage );
        loadPGC( prop );
    }
}
