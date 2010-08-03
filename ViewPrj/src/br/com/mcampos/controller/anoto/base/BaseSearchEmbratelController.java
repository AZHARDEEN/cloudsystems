package br.com.mcampos.controller.anoto.base;


import br.com.mcampos.controller.anoto.AnotoViewController;
import br.com.mcampos.controller.anoto.renderer.EmbratelResultListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.AnotoSummary;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.EmbratelFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.api.InputElement;
import org.zkoss.zul.impl.api.XulElement;


public abstract class BaseSearchEmbratelController extends LoggedBaseController
{
    private EmbratelFacade session;

    private Combobox cmbApplication;
    private Combobox cmbDealer;
    private Combobox cmbResale;

    private Datebox initDate;
    private Datebox endDate;
    private Listbox resultList;
    private Combobox cmbMaxRecords;
    private Textbox txtFieldValue;

    private Intbox resaleCode;
    private Intbox dealerCode;

    protected Label labelFormView2Title;
    private Label labelApplication;
    private Label labelInitDate;
    private Label labelEndDate;
    private Label labelPen;
    private Label labelTo;
    private Label labelFieldValue;
    private Label labelMaxRecords;
    private Label labelFormNumber;

    private Label labelSummaryForms;
    private Label labelSummaryPre;
    private Label labelSummaryPos;
    private Label labelSummaryMoney;
    private Label labelSummaryBoleto;
    private Label labelSummaryDI;
    private Label labelSummaryPap;
    private Label labelSummaryCvm;
    private Label labelSummaryFend;
    private Label labelSummaryRejectZip;
    private Label labelSummaryRejectCredit;
    private Label labelSummaryNoPhoto;
    private Label labelSummaryPhoto;

    private Label labelEmptySituation;
    private Label labelEmptyType;
    private Label labelEmptyPayment;
    private Label labelEmptyCategory;

    private Button btnFilter;
    protected Button btnSummary;

    private Menuitem mnuExport;
    private Menuitem mnuExport2;

    private Listheader headSeq;
    private Listheader headDate;

    private Listheader headDealerName;
    private Listheader headCellNumber;
    private Listheader headPhoto;

    private Column columnFieldName;
    private Column columnFieldValue;

    private Tab tabFilter;

    private Tab tabAnotoSearch;
    private Tab tabSummary;

    private Flashchart chartAttach;
    private Flashchart chartType;
    private Flashchart chartPay;
    private Flashchart chartCategory;
    private Flashchart chartStatus;

    private Row rowResale;
    private Row rowDealer;

    private Grid gridCustomFields;

    private Checkbox chkNoBackoffice;

    public BaseSearchEmbratelController()
    {
        super();
    }

    public BaseSearchEmbratelController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        configureLabels();
        refresh();
        resultList.setItemRenderer( new EmbratelResultListRenderer() );
        cmbMaxRecords.setSelectedIndex( 0 );
        configureonOkEvents();
        SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy HHmmss" );
        df.parse( "24072010 000000" );
        initDate.setValue( df.parse( "24072010 000000" ) );

        DealerDTO myAccount = getSession().myDealerAccount( getLoggedInUser() );
        if ( myAccount != null ) {
            rowDealer.setVisible( myAccount.getType().getId().equals( DealerTypeDTO.typeDealer ) == false );
            rowResale.setVisible( false );
            findResale( myAccount.getResale() );
            findDealer( myAccount );
        }
        else {
            rowDealer.setVisible( true );
            rowResale.setVisible( true );
        }
    }


    private void findResale( ResaleDTO resale )
    {
        if ( resale == null )
            return;
        for ( int nIndex = 0; nIndex < cmbResale.getItemCount(); nIndex++ ) {
            Comboitem item = cmbResale.getItemAtIndex( nIndex );
            if ( item != null ) {
                ResaleDTO dto = ( ResaleDTO )item.getValue();
                if ( resale.equals( dto ) ) {
                    cmbResale.setSelectedItem( item );
                    loadDealers( dto );
                    return;
                }
            }
        }
        showErrorMessage( "A revenda não foi localizada no cadastro de revendas" );
    }

    private void findDealer( DealerDTO dealer )
    {
        if ( dealer == null )
            return;
        for ( int nIndex = 0; nIndex < cmbDealer.getItemCount(); nIndex++ ) {
            Comboitem item = cmbDealer.getItemAtIndex( nIndex );
            if ( item != null ) {
                DealerDTO dto = ( DealerDTO )item.getValue();
                if ( dealer.equals( dto ) ) {
                    cmbDealer.setSelectedItem( item );
                    return;
                }
            }
        }
    }

    private void refresh()
    {
        loadApplication();
        loadResales();
        //loadPages( null );
        //loadPens( null );
        //loadPGC( null );
    }

    private void loadApplication()
    {
        List list;
        try {
            list = getSession().getForms( getLoggedInUser() );
            loadCombobox( cmbApplication, list );
            if ( cmbApplication.getItemCount() > 0 ) {
                cmbApplication.setSelectedIndex( 0 );
                getApplicationProperties();
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Aplicações" );
        }
    }

    private void loadResales()
    {
        List list;
        try {
            list = getSession().getResales( getLoggedInUser() );
            loadCombobox( cmbResale, list );
            Comboitem item = cmbResale.appendItem( getLabel( "all_female" ) );
            if ( item != null ) {
                cmbResale.setSelectedItem( item );
                getResaleProperties();
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onSelect$cmbResale()
    {
        ResaleDTO resaleDTO = ( ResaleDTO )cmbResale.getSelectedItem().getValue();
        loadDealers( resaleDTO );
    }

    private void getResaleProperties()
    {
        ResaleDTO resaleDTO = ( ResaleDTO )cmbResale.getSelectedItem().getValue();
        loadDealers( resaleDTO );
    }


    private void loadDealers( ResaleDTO resaleDTO )
    {
        if ( cmbDealer == null )
            return;
        List list;
        try {
            list = getSession().getDealers( getLoggedInUser(), resaleDTO );
            if ( cmbDealer.getItems() != null )
                cmbDealer.getItems().clear();
            loadCombobox( cmbDealer, list );
            Comboitem item = cmbDealer.appendItem( getLabel( "all_male" ) );
            if ( item != null )
                cmbDealer.setSelectedItem( item );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Carregar Canetas" );
        }
    }

    private void getApplicationProperties()
    {
        if ( cmbApplication.getSelectedItem() == null )
            return;
        loadCustom( ( FormDTO )cmbApplication.getSelectedItem().getValue() );
    }

    private void loadCustom( FormDTO form )
    {
        List<AnotoPageFieldDTO> fields = Collections.emptyList();
        try {
            fields = getSession().getSearchableFields( getLoggedInUser(), form );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
        Rows rows = gridCustomFields.getRows();
        if ( rows == null ) {
            rows = new Rows();
            rows.setParent( gridCustomFields );
        }
        if ( rows.getChildren() != null )
            rows.getChildren().clear();
        if ( SysUtils.isEmpty( fields ) == false ) {
            gridCustomFields.setVisible( true );
            XulElement c;
            for ( AnotoPageFieldDTO f : fields ) {
                Row row = new Row();
                row.setParent( rows );
                row.setValue( f );
                new Label( f.getName() ).setParent( row );
                c = null;
                switch ( f.getType().getId() ) {
                case 1:
                    c = new Textbox();
                    break;
                case 2:
                    c = new Intbox();
                    break;
                case 3:
                    c = new Datebox();
                    break;
                case 4:
                    c = new Timebox();
                    break;
                case 5:
                    c = new Decimalbox();
                    break;
                case 6:
                    c = new Checkbox();
                    break;
                }
                if ( c != null ) {
                    c.setWidth( "95%" );
                    c.setParent( row );
                }
            }
        }
        else
            gridCustomFields.setVisible( false );

    }

    public void onSelect$cmbApplication()
    {
        getApplicationProperties();
    }

    /*
     * This method can be overridable.
     */

    private void configureHeader( List<AnotoResultList> dtos )
    {
        Listheader h;
        Listhead head = resultList.getListhead();
        if ( head == null || SysUtils.isEmpty( dtos ) )
            return;
        if ( head.getChildren() != null ) {
            head.getChildren().clear();
        }

        h = new Listheader( getLabel( "headSeq" ) );
        h.setWidth( "50px" );
        head.appendChild( h );

        h = new Listheader( getLabel( "headDate" ) );
        h.setWidth( "120px" );
        head.appendChild( h );

        h = new Listheader( getLabel( "headDealerName" ) );
        h.setWidth( "250px" );
        head.appendChild( h );

        h = new Listheader( getLabel( "headCellNumber" ) );
        h.setWidth( "100px" );
        head.appendChild( h );

        h = new Listheader( getLabel( "headPhoto" ) );
        h.setWidth( "70px" );
        head.appendChild( h );

        for ( PgcFieldDTO field : dtos.get( 0 ).getFields() ) {
            if ( field.getName().equals( "CEP" ) )
                continue;
            h = new Listheader( field.getName() );
            h.setWidth( ( field.getName().length() * 10 ) + "px" );
            head.appendChild( h );
        }
    }

    protected void loadPGC( Properties prop )
    {
        List<AnotoResultList> dtos;
        try {
            Integer id = Integer.parseInt( cmbMaxRecords.getSelectedItem().getLabel() );
            dtos = getSession().getAllPgcPenPage( getLoggedInUser(), prop, id );
            configureHeader( dtos );
            ListModelList model = getModel();
            model.clear();
            model.addAll( dtos );
            //resultList.invalidate();
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
    }


    private void updateChart( AnotoSummary sum )
    {
        if ( sum.getPgc().equals( 0 ) ) {
            sum.setPgc( 200 );
            sum.setPrepago( 130 );
            sum.setBoleto( 90 );
            sum.setDinheiro( 60 );
            sum.setDi( 50 );
            sum.setFoto( 30 );
            sum.setCvm( 80 );
            sum.setPap( 120 );
            sum.setFend( 160 );
            sum.setRejeitadoCep( 10 );
            sum.setRejeitadoCredito( 30 );
        }

        SimplePieModel model = new SimplePieModel();
        model.setValue( "Com Foto", sum.getFoto() );
        model.setValue( "Sem Foto", sum.getSemFoto() );
        chartAttach.setModel( model );

        model = new SimplePieModel();
        model.setValue( "Pré-pago", sum.getPrepago() );
        model.setValue( "Pós-pago", sum.getPospago() );
        model.setValue( "Branco", sum.getEmptyType() );
        chartType.setModel( model );

        model = new SimplePieModel();
        model.setValue( "Dinheiro", sum.getDinheiro() );
        model.setValue( "Boleto", sum.getBoleto() );
        model.setValue( "DI", sum.getDi() );
        model.setValue( "Branco", sum.getEmptyPayment() );
        chartPay.setModel( model );

        model = new SimplePieModel();
        model.setValue( "PAP", sum.getPap() );
        model.setValue( "CVM", sum.getCvm() );
        model.setValue( "Branco", sum.getEmptyCategory() );
        chartCategory.setModel( model );

        model = new SimplePieModel();
        model.setValue( "FEND", sum.getFend() );
        model.setValue( "Rej. CEP", sum.getRejeitadoCep() );
        model.setValue( "Rej. Credito", sum.getRejeitadoCredito() );
        model.setValue( "Branco", sum.getEmptySituation() );
        chartStatus.setModel( model );

    }

    protected void loadSummary( Properties prop )
    {
        AnotoSummary sum;
        try {
            //Integer id = Integer.parseInt( cmbMaxRecords.getSelectedItem().getLabel() );
            sum = getSession().getSummary( getLoggedInUser(), prop );
            labelSummaryForms.setValue( sum.getPgc().toString() );
            labelSummaryPhoto.setValue( sum.getFoto().toString() );
            labelSummaryNoPhoto.setValue( sum.getSemFoto().toString() );
            labelSummaryPre.setValue( sum.getPrepago().toString() );
            labelSummaryPos.setValue( sum.getPospago().toString() );
            labelSummaryMoney.setValue( sum.getDinheiro().toString() );
            labelSummaryBoleto.setValue( sum.getBoleto().toString() );
            labelSummaryDI.setValue( sum.getDi().toString() );
            labelSummaryPap.setValue( sum.getPap().toString() );
            labelSummaryCvm.setValue( sum.getCvm().toString() );
            labelSummaryFend.setValue( sum.getFend().toString() );
            labelSummaryRejectZip.setValue( sum.getRejeitadoCep().toString() );
            labelSummaryRejectCredit.setValue( sum.getRejeitadoCredito().toString() );
            labelEmptyCategory.setValue( sum.getEmptyCategory().toString() );
            labelEmptyType.setValue( sum.getEmptyType().toString() );
            labelEmptyPayment.setValue( sum.getEmptyPayment().toString() );
            labelEmptySituation.setValue( sum.getEmptySituation().toString() );
            updateChart( sum );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de PGC" );
        }
    }


    private ListModelList getModel()
    {
        ListModelList model = ( ListModelList )resultList.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<AnotoResultList>(), true );
            resultList.setModel( model );
        }
        return model;
    }

    public void onClick$mnuExport()
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

    public void onClick$mnuExport2()
    {
        File file;
        try {
            file = File.createTempFile( "export", ".xls" );
            file.deleteOnExit();
            WritableWorkbook workbook = Workbook.createWorkbook( file );
            writeToExcell2( workbook );
            Filedownload.save( file, "application/vnd.ms-excel" );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    private void writeToExcell( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
    {
        WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
        int nColumns = setHeader( sheet );
        ListModelList model = getModel();
        boolean bFirst = true;
        for ( int nIndex = 0; nIndex < model.getSize(); nIndex++ ) {
            AnotoResultList dto = ( AnotoResultList )model.get( nIndex );
            if ( dto != null ) {
                if ( bFirst ) {
                    bFirst = false;
                    addHead( sheet, nColumns, dto.getFields() );
                }
                nColumns = 0;
                sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex + 1 ) );
                sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage().getPgc().getInsertDate() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach() ? "SIM" : "" ) );
                addData( sheet, nColumns, nIndex + 1, dto.getFields() );
            }
        }
        workbook.write();
        workbook.close();
    }


    private void writeToExcell2( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
    {
        WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
        int nColumns = setHeader2( sheet );
        ListModelList model = getModel();
        boolean bFirst = true;
        List<PgcFieldDTO> fields;
        for ( int nIndex = 0; nIndex < model.getSize(); nIndex++ ) {
            AnotoResultList dto = ( AnotoResultList )model.get( nIndex );
            if ( dto != null ) {
                try {
                    fields = getSession().getFields( getLoggedInUser(), dto.getPgcPage() );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage() );
                    return;
                }
                if ( bFirst ) {
                    bFirst = false;
                    addHead( sheet, nColumns, fields );
                }
                nColumns = 0;
                sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex + 1 ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getForm().toString() ) );
                sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage().getBookId() + 1 ) );
                sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage().getPageId() + 1 ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getPen().toString() ) );
                sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage().getPgc().getInsertDate() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getEmail() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLatitude() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLongitude() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach() ? "SIM" : "" ) );
                addData( sheet, nColumns, nIndex + 1, fields );
            }
        }
        workbook.write();
        workbook.close();
    }


    private void addHead( WritableSheet sheet, int nColumns, List<PgcFieldDTO> fields ) throws WriteException,
                                                                                               RowsExceededException
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        for ( PgcFieldDTO field : fields ) {
            sheet.addCell( new jxl.write.Label( nColumns++, 0, field.getName() ) );
        }
    }

    private void addData( WritableSheet sheet, int nColumn, int nRow, List<PgcFieldDTO> fields ) throws WriteException,
                                                                                                        RowsExceededException
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        for ( PgcFieldDTO field : fields ) {
            sheet.addCell( new jxl.write.Label( nColumn++, nRow, field.getValue() ) );
        }
    }

    private int setHeader( WritableSheet sheet ) throws WriteException, RowsExceededException
    {
        int nIndex = 1;
        jxl.write.Label l = new jxl.write.Label( 0, 0, headSeq.getLabel() );
        sheet.addCell( l );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headDate.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headDealerName.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headCellNumber.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headPhoto.getLabel() ) );
        return nIndex;
    }

    private int setHeader2( WritableSheet sheet ) throws WriteException, RowsExceededException
    {
        int nIndex = 1;
        jxl.write.Label l = new jxl.write.Label( 0, 0, headSeq.getLabel() );
        sheet.addCell( l );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Aplicação" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Formulário" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Página" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Caneta" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headDate.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headDealerName.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Email" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headCellNumber.getLabel() ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Latitude" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, "Longitude" ) );
        sheet.addCell( new jxl.write.Label( nIndex++, 0, headPhoto.getLabel() ) );
        return nIndex;
    }

    private void verifyCustomFields( Properties prop )
    {
        Properties custom = new Properties();

        Rows rows = gridCustomFields.getRows();
        if ( rows != null && rows.getChildren() != null && rows.getChildren().size() >= 1 ) {
            for ( int nIndex = 0; nIndex < rows.getChildren().size(); nIndex++ ) {
                Row row = ( Row )rows.getChildren().get( nIndex );
                if ( row == null )
                    continue;
                String fieldName = ( ( Label )row.getChildren().get( 0 ) ).getValue();
                Object value = null;
                XulElement c = ( ( XulElement )row.getChildren().get( 1 ) );
                if ( c instanceof InputElement ) {
                    String sValue = ( ( InputElement )c ).getRawText();
                    if ( SysUtils.isEmpty( sValue ) )
                        continue;
                    value = sValue.trim();
                }
                else if ( c instanceof Checkbox ) {
                    Boolean b = ( ( Checkbox )c ).isChecked();
                    value = b;
                }
                custom.put( fieldName, value );
            }
        }
        /*filtrar por revenda*/
        if ( ( cmbResale.getSelectedItem() != null && cmbResale.getSelectedItem().getValue() != null ) ) {
            ResaleDTO dto = ( ResaleDTO )cmbResale.getSelectedItem().getValue();
            custom.put( "Codigo Revenda", dto.getCode() );
        }
        if ( SysUtils.isZero( resaleCode.getValue() ) == false ) {
            custom.put( "Codigo Revenda", resaleCode.getValue().toString() );
        }
        if ( custom.size() > 0 ) {
            prop.put( "custom_fields", custom );
        }
    }

    private Properties getFilters()
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
        if ( cmbDealer.getSelectedItem() != null && cmbDealer.getSelectedItem().getValue() != null ) {
            String pen = null;
            try {
                pen = getSession().getDealerPen( getLoggedInUser(), ( DealerDTO )cmbDealer.getSelectedItem().getValue() );
                if ( SysUtils.isEmpty( pen ) ) {
                    showErrorMessage( "Vendedor está sem caneta associada atualmente" );
                    pen = "InvalidPen";
                }
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage() );
                pen = "InvalidPen";
            }
            prop.put( "pen", pen );
        }
        Date iDate = initDate.getValue();
        if ( iDate != null )
            prop.put( "initDate", iDate );
        Date eDate = endDate.getValue();
        if ( eDate != null )
            prop.put( "endDate", eDate );
        String fieldValue = txtFieldValue.getValue();
        if ( SysUtils.isEmpty( fieldValue ) == false ) {
            prop.put( "pgcFieldValue", fieldValue );
        }
        verifyCustomFields( prop );

        if ( chkNoBackoffice.isChecked() ) {
            prop.put( "noBackOffice", "yes" );
        }
        return prop;
    }


    public void onClick$btnFilter()
    {
        loadPGC( getFilters() );
        tabAnotoSearch.setSelected( true );
    }

    public void onClick$btnSummary()
    {
        loadSummary( getFilters() );
        tabSummary.setSelected( true );
    }

    public void onSelect$resultList()
    {
        onClick$btnProperty();
    }

    protected void gotoPage( Properties params )
    {
        gotoPage( "/private/admin/anoto/embratel_view.zul", getRootParent().getParent(), params );
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


    private void configureonOkEvents()
    {
        cmbApplication.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    onClick$btnFilter();
                }
            } );

        if ( cmbDealer != null )
            cmbDealer.addEventListener( Events.ON_OK, new EventListener()
                {
                    public void onEvent( Event event )
                    {
                        onClick$btnFilter();
                    }
                } );

        if ( initDate != null )
            initDate.addEventListener( Events.ON_OK, new EventListener()
                {
                    public void onEvent( Event event )
                    {
                        onClick$btnFilter();
                    }
                } );
        if ( endDate != null )
            endDate.addEventListener( Events.ON_OK, new EventListener()
                {
                    public void onEvent( Event event )
                    {
                        onClick$btnFilter();
                    }
                } );
        if ( cmbMaxRecords != null )
            cmbMaxRecords.addEventListener( Events.ON_OK, new EventListener()
                {
                    public void onEvent( Event event )
                    {
                        onClick$btnFilter();
                    }
                } );
        if ( txtFieldValue != null )
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
        setLabel( labelTo );
        setLabel( labelFieldValue );
        setLabel( labelMaxRecords );
        setLabel( labelFormNumber );

        setLabel( btnFilter );
        setLabel( mnuExport );
        setLabel( mnuExport2 );
        setLabel( btnSummary );

        setLabel( headSeq );
        setLabel( headDate );
        setLabel( headDealerName );
        setLabel( headCellNumber );
        setLabel( headPhoto );

        setLabel( tabFilter );

        setLabel( columnFieldValue );
        setLabel( columnFieldName );

        setLabel( tabSummary );
        setLabel( tabAnotoSearch );

    }

    protected Menuitem getExportButton()
    {
        return mnuExport;
    }

    private EmbratelFacade getSession()
    {
        if ( session == null )
            session = ( EmbratelFacade )getRemoteSession( EmbratelFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return "Painel Gerencial - Embratel";
    }
}
