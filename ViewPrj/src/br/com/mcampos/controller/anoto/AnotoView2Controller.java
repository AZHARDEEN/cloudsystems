package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.ComboFormRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPageRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPenRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcListRendered;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Timebox;


public class AnotoView2Controller extends AnotoLoggedController
{
    protected Combobox cmbApplication;
    protected Combobox cmbPage;
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
        refresh ();
    }

    protected void refresh ()
    {
        loadApplication ();
        loadPages( null );
        loadPens ( null );
        loadPGC ();
    }

    protected void loadApplication ()
    {
        ListModelList model;
        List list;
        try {
            list = getSession().getForms( getLoggedInUser() );
            model = new ListModelList ( list );
            cmbApplication.setItemRenderer( new ComboFormRenderer() );
            cmbApplication.setModel( model );
            if ( list.size() > 0 && cmbApplication.getItemCount() > 0 )
                cmbApplication.setSelectedIndex( 0 );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Aplicações");
        }
    }


    protected void loadPages ( FormDTO dto )
    {
        ListModelList model;
        List list;
        try {
            if ( dto == null )
                list = getSession().getPages( getLoggedInUser() );
            else
                list = getSession().getPages( getLoggedInUser() );
            model = new ListModelList ( list );
            cmbPage.setItemRenderer( new ComboPageRenderer() );
            cmbPage.setModel( model );
            if ( list.size() > 0 && cmbPage.getItemCount() > 0 )
                cmbPage.setSelectedIndex( 0 );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Páginas");
        }
    }


    protected void loadPens ( FormDTO dto )
    {
        ListModelList model;
        List list;
        try {
            if ( dto == null )
                list = getSession().getPens( getLoggedInUser() );
            else
                list = getSession().getPens( getLoggedInUser() );
            model = new ListModelList ( list );
            cmbPen.setItemRenderer( new ComboPenRenderer() );
            cmbPen.setModel( model );
            if ( list.size() > 0 && cmbPen.getItemCount() > 0 )
                cmbPen.setSelectedIndex( 0 );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Canetas");
        }
    }

    public void onSelect$cmbApplication ()
    {
        loadPages( (FormDTO) cmbApplication.getSelectedItem().getValue() );
    }

    protected void loadPGC ()
    {
        List<PGCDTO> medias;
        try {
            medias = getSession().getAllPgc( getLoggedInUser() );
            ListModelList model = new ListModelList ( medias );
            resultList.setItemRenderer( new PgcListRendered() );
            resultList.setModel( model );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
    }
}
