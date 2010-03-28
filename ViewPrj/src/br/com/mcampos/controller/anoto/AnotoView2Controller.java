package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.ComboFormRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPageRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboPenRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcPenPageListRenderer;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
        resultList.setItemRenderer( new PgcPenPageListRenderer() );
        configureonOkEvents();
    }

    protected void refresh()
    {
        loadApplication();
        loadPages( null );
        loadPens( null );
        //loadPGC( null );
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
        ListModelList model;
        List<AnotoPageDTO> list;
        try {
            if ( dto == null )
                list = getSession().getPages( getLoggedInUser() );
            else
                list = getSession().getPages( getLoggedInUser(), dto );
            model = new ListModelList( list );
            cmbAnotoPage.setItemRenderer( new ComboPageRenderer() );
            cmbAnotoPage.setModel( model );
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
            ListModelList model = getModel();
            model.clear();
            model.addAll( dtos );
            resultList.invalidate();
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
    }

    protected ListModelList getModel()
    {
        ListModelList model = ( ListModelList )resultList.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<PgcPenPageDTO>(), true );
            resultList.setModel( model );
        }
        return model;
    }

    public void onClick$btnFilter()
    {
        Properties prop = new Properties();

        /*
         * Does form combo selected??
         */
        if ( cmbApplication.getSelectedItem() != null ) {
            prop.put( "form", cmbApplication.getSelectedItem().getValue() );
        }

        /*
         * Does page combo selected or have this combo a text?
         */
        String strInfo = "";
        if ( cmbAnotoPage.getSelectedItem() != null ) {
            AnotoPageDTO page;

            page = ( AnotoPageDTO )cmbAnotoPage.getSelectedItem().getValue();
            strInfo = page.getPageAddress();
        }
        else if ( SysUtils.isEmpty( cmbAnotoPage.getText() ) == false ) {
            strInfo = cmbAnotoPage.getText();
        }
        if ( strInfo.length() > 0 )
            prop.put( "page", strInfo );

        /*
         * Does pen combo is selected? Have any text?
         */
        String penInfo = "";
        if ( cmbPen.getSelectedItem() != null ) {
            PenDTO pen;

            pen = ( PenDTO )cmbPen.getSelectedItem().getValue();
            penInfo = pen.getId();
        }
        else if ( SysUtils.isEmpty( cmbPen.getText() ) == false ) {
            penInfo = cmbPen.getText();
        }
        if ( penInfo.length() > 0 )
            prop.put( "pen", penInfo );


        /*
         * Does we have a init Date?
         */
        Date iDate = getDate( initDate, initTime );
        if ( iDate != null )
            prop.put( "initDate", iDate );

        /*
         * Does we have a end Date?
         */
        Date eDate = getDate( endDate, endTime );
        if ( eDate != null )
            prop.put( "endDate", eDate );

        loadPGC( prop );
    }

    protected Date getDate( Datebox d, Timebox t )
    {
        Date eDate = null;
        if ( d.getValue() != null )
            eDate = new Date( d.getValue().getTime() );
        if ( t.getValue() != null ) {
            String strDate, strTime;
            SimpleDateFormat dfh = new SimpleDateFormat( "yyyyMMdd" );
            SimpleDateFormat dft = new SimpleDateFormat( "HHmm" );
            if ( eDate == null )
                eDate = new Date();
            strDate = dfh.format( eDate );
            strTime = dft.format( t.getValue() );
            strDate += strTime;
            dfh = new SimpleDateFormat( "yyyyMMddHHmm" );
            try {
                eDate = dfh.parse( strDate );
            }
            catch ( ParseException e ) {
                e = null;
            }
        }
        return eDate;
    }


    public void onSelect$resultList()
    {
        onClick$btnProperty();
    }

    public void onClick$btnProperty()
    {
        if ( resultList.getSelectedItem() != null ) {
            Properties params = new Properties();
            params.put( AnotoViewController.paramName, resultList.getSelectedItem().getValue() );
            gotoPage( "/private/admin/anoto/anoto_view.zul", getRootParent().getParent(), params );
        }
        else {
            showErrorMessage( "Selecione um pgc da lista primeiro", "Visualizar PGC" );
        }
    }


    protected void configureonOkEvents()
    {
        cmbApplication.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );

        cmbPen.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );

        cmbAnotoPage.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        initDate.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        endDate.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        initTime.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        endTime.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
    }
}
