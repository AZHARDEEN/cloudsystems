package br.com.mcampos.controller.anoto.base;


import br.com.mcampos.controller.anoto.AnotoViewController;
import br.com.mcampos.controller.anoto.renderer.PgcPenPageListRenderer;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import jxl.Workbook;

import jxl.write.DateTime;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;


public abstract class BaseSearchController extends AnotoLoggedController
{
    private Combobox cmbApplication;
    private Combobox cmbPen;
    private Datebox initDate;
    private Timebox initTime;
    private Datebox endDate;
    private Timebox endTime;
    private Listbox resultList;
    private Textbox txtBarcode;
    private Intbox txtFormIdFrom;
    private Intbox txtFormIdTo;
    private Combobox cmbMaxRecords;
    private Textbox txtFieldValue;

    private Label labelFormView2Title;
    private Label labelApplication;
    private Label labelInitDate;
    private Label labelEndDate;
    private Label labelPen;
    private Label labelBarCode;
    private Label labelTo;
    private Label labelFieldValue;
    private Label labelMaxRecords;
    private Label labelFormNumber;

    private Button btnFilter;
    private Button btnExport;

    private Listheader headSeq;
    private Listheader headApplication;
    private Listheader headFormulario;
    private Listheader headPagina;
    private Listheader headPen;
    private Listheader headDate;

    private Listheader headUserName;
    private Listheader headEmail;
    private Listheader headCellNumber;
    private Listheader headLatitude;
    private Listheader headLongitude;
    private Listheader headBarcode;
    private Listheader headPhoto;

    public BaseSearchController()
    {
        super();
    }

    public BaseSearchController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        configureLabels();
        refresh();
        resultList.setItemRenderer( new PgcPenPageListRenderer() );
        cmbMaxRecords.setSelectedIndex( 0 );
        configureonOkEvents();
    }

    protected void refresh()
    {
        loadApplication();
        //loadPages( null );
        //loadPens( null );
        //loadPGC( null );
    }

    protected void loadApplication()
    {
        List list;
        try {
            list = getSession().getForms( getLoggedInUser() );
            loadCombobox( cmbApplication, list );
            if ( cmbApplication.getItemCount() > 0 ) {
                cmbApplication.setSelectedIndex( 0 );
                loadPens( ( FormDTO )cmbApplication.getSelectedItem().getValue() );
            }
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
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Páginas" );
        }
    }


    protected void loadPens( FormDTO dto )
    {
        List list;
        try {
            if ( dto == null )
                list = getSession().getPens( getLoggedInUser() );
            else
                list = getSession().getPens( getLoggedInUser(), dto );
            loadCombobox( cmbPen, list );
            Comboitem item = cmbPen.appendItem( getLabel( "all_female" ) );
            if ( item != null )
                cmbPen.setSelectedItem( item );
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
        List<AnotoResultList> dtos;
        try {
            Integer id = Integer.parseInt( cmbMaxRecords.getSelectedItem().getLabel() );
            dtos = getSession().getAllPgcPenPage( getLoggedInUser(), prop, id );
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
            model = new ListModelList( new ArrayList<AnotoResultList>(), true );
            resultList.setModel( model );
        }
        return model;
    }

    public void onClick$btnExport()
    {
        File file;
        try {
            file = File.createTempFile( "export", ".xls" );
            file.deleteOnExit();
            WritableWorkbook workbook = Workbook.createWorkbook( file );
            writeToExcell( workbook );
            Filedownload.save( file, "application/vnd.ms-excel" );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    private void writeToExcell( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
    {
        WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
        setHeader( sheet );
        ListModelList model = getModel();
        for ( int nIndex = 0; nIndex < model.getSize(); nIndex++ ) {
            AnotoResultList dto = ( AnotoResultList )model.get( nIndex );
            if ( dto != null ) {
                sheet.addCell( new Number( 0, nIndex + 1, nIndex ) );
                sheet.addCell( new jxl.write.Label( 1, nIndex + 1, dto.getForm().toString() ) );
                sheet.addCell( new Number( 2, nIndex + 1, dto.getPgcPage().getBookId() + 1 ) );
                sheet.addCell( new Number( 3, nIndex + 1, dto.getPgcPage().getPageId() + 1 ) );
                sheet.addCell( new jxl.write.Label( 4, nIndex + 1, dto.getPen().toString() ) );
                sheet.addCell( new DateTime( 5, nIndex + 1, dto.getPgcPage().getPgc().getInsertDate() ) );
                sheet.addCell( new jxl.write.Label( 6, nIndex + 1, dto.getUserName() ) );
                sheet.addCell( new jxl.write.Label( 7, nIndex + 1, dto.getEmail() ) );
                sheet.addCell( new jxl.write.Label( 8, nIndex + 1, dto.getCellNumber() ) );
                sheet.addCell( new jxl.write.Label( 9, nIndex + 1, dto.getLatitude() ) );
                sheet.addCell( new jxl.write.Label( 10, nIndex + 1, dto.getLongitude() ) );
                sheet.addCell( new jxl.write.Label( 11, nIndex + 1, dto.getBarcodeValue() ) );
                sheet.addCell( new jxl.write.Label( 12, nIndex + 1, dto.getAttach() ? "SIM" : "" ) );
            }
        }
        workbook.write();
        workbook.close();
    }

    private void setHeader( WritableSheet sheet ) throws WriteException, RowsExceededException
    {
        jxl.write.Label l = new jxl.write.Label( 0, 0, headSeq.getLabel() );
        sheet.addCell( l );
        sheet.addCell( new jxl.write.Label( 1, 0, headApplication.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 2, 0, headFormulario.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 3, 0, headPagina.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 4, 0, headPen.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 5, 0, headDate.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 6, 0, headUserName.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 7, 0, headEmail.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 8, 0, headCellNumber.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 9, 0, headLatitude.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 10, 0, headLongitude.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 11, 0, headBarcode.getLabel() ) );
        sheet.addCell( new jxl.write.Label( 12, 0, headPhoto.getLabel() ) );
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
        if ( strInfo.length() > 0 )
            prop.put( "page", strInfo );

        /*
       * Does pen combo is selected? Have any text?
       */
        String penInfo = "";
        if ( cmbPen.getSelectedItem() != null ) {
            PenDTO pen;

            pen = ( PenDTO )cmbPen.getSelectedItem().getValue();
            if ( pen != null )
                penInfo = pen.getId();
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

        /*
       * Does we have a barcode?
       */
        String barCode = txtBarcode.getValue();
        if ( SysUtils.isEmpty( barCode ) == false )
            prop.put( "barCode", barCode );


        /*
       * Does we have a barcode?
       */
        Integer bookIdFrom = txtFormIdFrom.getValue();
        if ( SysUtils.isZero( bookIdFrom ) == false ) {
            /*
           * Truque. No renderer somamos + 1 ao book id (zero based), porém zero não faz sentido ao usuario
           */
            bookIdFrom--;
            prop.put( "bookIdFrom", bookIdFrom );
        }


        Integer bookIdTo = txtFormIdTo.getValue();
        if ( SysUtils.isZero( bookIdTo ) == false ) {
            bookIdTo--;
            prop.put( "bookIdTo", bookIdTo );
        }

        String fieldValue = txtFieldValue.getValue();
        if ( SysUtils.isEmpty( fieldValue ) == false ) {
            prop.put( "pgcFieldValue", fieldValue );
        }


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

    protected void gotoPage( Properties params )
    {
        gotoPage( "/private/admin/anoto/anoto_view.zul", getRootParent().getParent(), params );
    }


    public void onClick$btnProperty()
    {
        if ( resultList.getSelectedItem() != null ) {
            Properties params = new Properties();
            params.put( AnotoViewController.paramName, resultList.getSelectedItem().getValue() );
            ListModelList model = getModel();
            List list = model.getInnerList();
            params.put( AnotoViewController.listName, list );
            gotoPage( params );
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
        txtBarcode.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        txtFormIdFrom.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        txtFormIdTo.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        cmbMaxRecords.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
        txtFieldValue.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );
    }


    private void configureLabels()
    {
        setLabel( labelFormView2Title );
        setLabel( labelApplication );
        setLabel( labelInitDate );
        setLabel( labelPen );
        setLabel( labelEndDate );
        setLabel( labelBarCode );
        setLabel( labelTo );
        setLabel( labelFieldValue );
        setLabel( labelMaxRecords );
        setLabel( labelFormNumber );

        setLabel( btnFilter );
        setLabel( btnExport );

        setLabel( headSeq );
        setLabel( headApplication );
        setLabel( headFormulario );
        setLabel( headPagina );
        setLabel( headPen );
        setLabel( headDate );
        setLabel( headUserName );
        setLabel( headEmail );
        setLabel( headCellNumber );
        setLabel( headLatitude );
        setLabel( headLongitude );
        setLabel( headBarcode );
        setLabel( headPhoto );

    }

    protected Button getExportButton()
    {
        return btnExport;
    }
}
