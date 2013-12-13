package br.com.mcampos.controller.anoto.base;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import br.com.mcampos.controller.anoto.AnotoViewController;
import br.com.mcampos.controller.anoto.renderer.PgcPenPageListRenderer;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseSearchController extends AnotoLoggedController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseSearchController.class.getSimpleName( ) );
	private Combobox cmbApplication;
	private Combobox cmbPen;
	private Datebox initDate;
	private Timebox initTime;
	private Datebox endDate;
	private Timebox endTime;
	private Listbox resultList;
	private Listbox summaryList;
	private Textbox txtBarcode;
	private Intbox txtFormIdFrom;
	private Intbox txtFormIdTo;
	protected Combobox cmbMaxRecords; /*Used in derived classes*/
	private Textbox txtFieldValue;

	protected Label labelFormView2Title;
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
	protected Button btnSummary;

	private Menuitem mnuExport;
	private Menuitem mnuExport2;

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

	private Column columnFieldName;
	private Column columnFieldValue;

	private Tab tabFilter;

	private Tab tabCustomFilter;

	private Tab tabAnotoSearch;
	private Tab tabSummary;

	private Flashchart chartPen;

	private Flashchart chartData;

	private Grid gridCustomFields;

	public BaseSearchController( )
	{
		super( );
	}

	public BaseSearchController( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.configureLabels( );
		this.refresh( );
		if ( this.resultList != null ) {
			this.resultList.setItemRenderer( new PgcPenPageListRenderer( ) );
		}
		if ( this.cmbMaxRecords != null ) {
			this.cmbMaxRecords.setSelectedIndex( 0 );
		}
		this.configureonOkEvents( );
	}

	private void refresh( )
	{
		this.loadApplication( );
		// loadPages( null );
		// loadPens( null );
		// loadPGC( null );
	}

	private void loadApplication( )
	{
		if ( this.cmbApplication == null ) {
			return;
		}
		List list;
		try {
			list = this.getSession( ).getForms( this.getLoggedInUser( ) );
			this.loadCombobox( this.cmbApplication, list );
			if ( this.cmbApplication.getItemCount( ) > 0 ) {
				this.cmbApplication.setSelectedIndex( 0 );
				this.getApplicationProperties( );
			}
		}
		catch ( ApplicationException e ) {
			this.showErrorMessage( e.getMessage( ), "Carregar Aplicações" );
		}
	}

	private void loadPages( FormDTO dto )
	{
		ListModelList model;
		List<AnotoPageDTO> list;
		try {
			if ( dto == null ) {
				list = this.getSession( ).getPages( this.getLoggedInUser( ) );
			}
			else {
				list = this.getSession( ).getPages( this.getLoggedInUser( ), dto );
			}
			model = new ListModelList( list );
		}
		catch ( ApplicationException e ) {
			this.showErrorMessage( e.getMessage( ), "Carregar Páginas" );
		}
	}

	private void loadPens( FormDTO dto )
	{
		List list;
		try {
			list = this.getSession( ).getPens( this.getLoggedInUser( ), dto );
			this.loadCombobox( this.cmbPen, list );
			Comboitem item = this.cmbPen.appendItem( this.getLabel( "all_female" ) );
			if ( item != null ) {
				this.cmbPen.setSelectedItem( item );
			}
		}
		catch ( ApplicationException e ) {
			this.showErrorMessage( e.getMessage( ), "Carregar Canetas" );
		}
	}

	private void getApplicationProperties( )
	{
		if ( this.cmbApplication.getSelectedItem( ) == null ) {
			return;
		}
		FormDTO form = (FormDTO) this.cmbApplication.getSelectedItem( ).getValue( );

		this.loadPages( form );
		this.loadPens( form );
		this.loadCustom( form );
	}

	private void loadCustom( FormDTO form )
	{
		if ( this.gridCustomFields == null || this.tabCustomFilter == null ) {
			return;
		}
		List<AnotoPageFieldDTO> fields = Collections.emptyList( );
		try {
			fields = this.getSession( ).getSearchableFields( this.getLoggedInUser( ), form );
		}
		catch ( ApplicationException e ) {
			this.showErrorMessage( e.getMessage( ), "Lista de PGC" );
		}
		Rows rows = this.gridCustomFields.getRows( );
		if ( rows == null ) {
			rows = new Rows( );
			rows.setParent( this.gridCustomFields );
		}
		if ( rows.getChildren( ) != null ) {
			rows.getChildren( ).clear( );
		}
		if ( SysUtils.isEmpty( fields ) == false ) {
			this.tabCustomFilter.setVisible( true );
			XulElement c;
			for ( AnotoPageFieldDTO f : fields ) {
				Row row = new Row( );
				row.setParent( rows );
				row.setValue( f );
				new Label( f.getName( ) ).setParent( row );
				c = null;
				switch ( f.getType( ).getId( ) ) {
				case 1:
					c = new Textbox( );
					break;
				case 2:
					c = new Intbox( );
					break;
				case 3:
					c = new Datebox( );
					break;
				case 4:
					c = new Timebox( );
					break;
				case 5:
					c = new Decimalbox( );
					break;
				case 6:
					c = new Checkbox( );
					break;
				}
				if ( c != null ) {
					c.setWidth( "95%" );
					c.setParent( row );
				}
			}
		}
		else {
			this.tabCustomFilter.setVisible( false );
		}

	}

	public void onSelect$cmbApplication( )
	{
		this.getApplicationProperties( );
	}

	/*
	 * This method can be overridable.
	 */

	protected void loadPGC( Properties prop )
	{
		List<AnotoResultList> dtos;
		try {
			Integer id = Integer.parseInt( this.cmbMaxRecords.getSelectedItem( ).getLabel( ) );
			dtos = this.getSession( ).getAllPgcPenPage( this.getLoggedInUser( ), prop, id, true );
			if ( dtos != null && dtos.size( ) > 0 ) {
				LOGGER.info( "We got about " + dtos.size( ) + " records in List<AnotoResultList>" );
			}
			else {
				LOGGER.info( "NO RECORDS IN List<AnotoResultList>" );
			}
			ListModelList model = this.getModel( );
			model.clear( );
			model.addAll( dtos );
			this.updateCharts( dtos );
			// resultList.invalidate();
		}
		catch ( ApplicationException e ) {
			this.showErrorMessage( e.getMessage( ), "Lista de PGC" );
		}
	}

	/*Used in derived classes*/

	protected ListModelList getModel( )
	{
		ListModelList model = (ListModelList) this.resultList.getModel( );
		if ( model == null ) {
			model = new ListModelList( new ArrayList<AnotoResultList>( ), true );
			this.resultList.setModel( model );
		}
		return model;
	}

	public void onClick$mnuExport( )
	{
		File file;
		try {
			file = File.createTempFile( "export", ".xls" );
			file.deleteOnExit( );
			WritableWorkbook workbook = Workbook.createWorkbook( file );
			this.writeToExcell( workbook );
			Filedownload.save( file, "application/vnd.ms-excel" );
		}
		catch ( Exception e ) {
			this.showErrorMessage( e.getMessage( ) );
		}
	}

	public void onClick$mnuExport2( )
	{
		File file;
		try {
			file = File.createTempFile( "export", ".xls" );
			file.deleteOnExit( );
			WritableWorkbook workbook = Workbook.createWorkbook( file );
			this.writeToExcell2( workbook );
			Filedownload.save( file, "application/vnd.ms-excel" );
		}
		catch ( Exception e ) {
			this.showErrorMessage( e.getMessage( ) );
		}
	}

	private void writeToExcell( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
	{
		WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
		int nColumns = this.setHeader( sheet );
		ListModelList model = this.getModel( );
		boolean bFirst = true;
		for ( int nIndex = 0; nIndex < model.getSize( ); nIndex++ ) {
			AnotoResultList dto = (AnotoResultList) model.get( nIndex );
			if ( dto != null ) {
				if ( bFirst ) {
					bFirst = false;
					this.addHead( sheet, nColumns, dto.getFields( ) );
				}
				nColumns = 0;
				sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getForm( ).toString( ) ) );
				sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage( ).getBookId( ) + 1 ) );
				sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage( ).getPageId( ) + 1 ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getPen( ).toString( ) ) );
				sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage( ).getPgc( ).getInsertDate( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getEmail( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLatitude( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLongitude( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getBarcodeValue( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach( ) ? "SIM" : "" ) );
				this.addData( sheet, nColumns, nIndex + 1, dto.getFields( ) );
			}
		}
		workbook.write( );
		workbook.close( );
	}

	private void writeToExcell2( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
	{
		WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
		int nColumns = this.setHeader( sheet );
		ListModelList model = this.getModel( );
		boolean bFirst = true;
		for ( int nIndex = 0; nIndex < model.getSize( ); nIndex++ ) {
			AnotoResultList dto = (AnotoResultList) model.get( nIndex );
			if ( dto != null ) {
				if ( bFirst ) {
					bFirst = false;
					this.addHead( sheet, nColumns, dto.getFields( ) );
				}
				nColumns = 0;
				sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getForm( ).toString( ) ) );
				sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage( ).getBookId( ) + 1 ) );
				sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage( ).getPageId( ) + 1 ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getPen( ).toString( ) ) );
				sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage( ).getPgc( ).getInsertDate( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getEmail( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLatitude( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLongitude( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getBarcodeValue( ) ) );
				sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach( ) ? "SIM" : "" ) );
				try {
					this.addData( sheet, nColumns, nIndex + 1, this.getSession( ).getFields( this.getLoggedInUser( ), dto.getPgcPage( ) ) );
				}
				catch ( ApplicationException e ) {
					this.showErrorMessage( e.getMessage( ) );
				}
			}
		}
		workbook.write( );
		workbook.close( );
	}

	private void addHead( WritableSheet sheet, int nColumns, List<PgcFieldDTO> fields ) throws WriteException,
			RowsExceededException
	{
		if ( SysUtils.isEmpty( fields ) ) {
			return;
		}
		for ( PgcFieldDTO field : fields ) {
			sheet.addCell( new jxl.write.Label( nColumns++, 0, field.getName( ) ) );
		}
	}

	private void addData( WritableSheet sheet, int nColumn, int nRow, List<PgcFieldDTO> fields ) throws WriteException,
			RowsExceededException
	{
		if ( SysUtils.isEmpty( fields ) ) {
			return;
		}
		for ( PgcFieldDTO field : fields ) {
			String value;
			if ( field.getType( ).getId( ).equals( FieldTypeDTO.typeBoolean ) ) {
				value = field.getHasPenstrokes( ) ? "SIM" : "";
			}
			else {
				value = SysUtils.isEmpty( field.getRevisedText( ) ) ? field.getIrcText( ) : field.getRevisedText( );
			}
			sheet.addCell( new jxl.write.Label( nColumn++, nRow, value ) );
		}
	}

	private int setHeader( WritableSheet sheet ) throws WriteException, RowsExceededException
	{
		int nIndex = 1;
		jxl.write.Label l = new jxl.write.Label( 0, 0, this.headSeq.getLabel( ) );
		sheet.addCell( l );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headApplication.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headFormulario.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headPagina.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headPen.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headDate.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headUserName.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headEmail.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headCellNumber.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headLatitude.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headLongitude.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headBarcode.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, this.headPhoto.getLabel( ) ) );
		return nIndex;
	}

	private void verifyCustomFields( Properties prop )
	{
		if ( this.gridCustomFields == null ) {
			return;
		}
		Properties custom = new Properties( );

		Rows rows = this.gridCustomFields.getRows( );
		if ( rows == null || rows.getChildren( ) == null || rows.getChildren( ).size( ) < 1 ) {
			return;
		}
		for ( int nIndex = 0; nIndex < rows.getChildren( ).size( ); nIndex++ ) {
			Row row = (Row) rows.getChildren( ).get( nIndex );
			if ( row == null ) {
				continue;
			}
			String fieldName = ( (Label) row.getChildren( ).get( 0 ) ).getValue( );
			Object value = null;
			XulElement c = ( (XulElement) row.getChildren( ).get( 1 ) );
			if ( c instanceof InputElement ) {
				String sValue = ( (InputElement) c ).getRawText( );
				if ( SysUtils.isEmpty( sValue ) ) {
					continue;
				}
				value = sValue.trim( );
			}
			else if ( c instanceof Checkbox ) {
				Boolean b = ( (Checkbox) c ).isChecked( );
				value = b;
			}
			custom.put( fieldName, value );
		}
		if ( custom.size( ) > 0 ) {
			prop.put( "custom_fields", custom );
		}
	}

	private Properties getFilters( )
	{
		Properties prop = new Properties( );

		/*
		* Does form combo selected??
		*/
		if ( this.cmbApplication.getSelectedItem( ) != null ) {
			prop.put( "form", this.cmbApplication.getSelectedItem( ).getValue( ) );
		}
		/*
		* Does page combo selected or have this combo a text?
		*/
		String strInfo = "";
		if ( strInfo.length( ) > 0 ) {
			prop.put( "page", strInfo );
		}
		/*
		* Does pen combo is selected? Have any text?
		*/
		String penInfo = "";
		if ( this.cmbPen.getSelectedItem( ) != null ) {
			PenDTO pen;

			pen = (PenDTO) this.cmbPen.getSelectedItem( ).getValue( );
			if ( pen != null ) {
				penInfo = pen.getId( );
			}
		}
		if ( penInfo.length( ) > 0 ) {
			prop.put( "pen", penInfo );
		}
		/*
		* Does we have a init Date?
		*/
		Date iDate = this.getDate( this.initDate, this.initTime );
		if ( iDate != null ) {
			prop.put( "initDate", iDate );
		}
		/*
		* Does we have a end Date?
		*/
		Date eDate = this.getDate( this.endDate, this.endTime );
		if ( eDate != null ) {
			prop.put( "endDate", eDate );
		}
		/*
		* Does we have a barcode?
		*/
		String barCode = this.txtBarcode.getValue( );
		if ( SysUtils.isEmpty( barCode ) == false ) {
			prop.put( "barCode", barCode );
		}
		/*
		* Does we have a barcode?
		*/
		Integer bookIdFrom = this.txtFormIdFrom.getValue( );
		if ( SysUtils.isZero( bookIdFrom ) == false ) {
			/*
			* Truque. No renderer somamos + 1 ao book id (zero based), porém zero não faz sentido ao usuario
			*/
			bookIdFrom--;
			prop.put( "bookIdFrom", bookIdFrom );
		}
		Integer bookIdTo = this.txtFormIdTo.getValue( );
		if ( SysUtils.isZero( bookIdTo ) == false ) {
			bookIdTo--;
			prop.put( "bookIdTo", bookIdTo );
		}
		String fieldValue = this.txtFieldValue.getValue( );
		if ( SysUtils.isEmpty( fieldValue ) == false ) {
			prop.put( "pgcFieldValue", fieldValue );
		}
		this.verifyCustomFields( prop );
		return prop;
	}

	public void onClick$btnFilter( )
	{
		LOGGER.info( "Filtering for pgc" );
		this.loadPGC( this.getFilters( ) );
		if ( this.tabAnotoSearch != null ) {
			this.tabAnotoSearch.setSelected( true );
		}
	}

	private Date getDate( Datebox d, Timebox t )
	{
		Date eDate = null;
		if ( d.getValue( ) != null ) {
			eDate = new Date( d.getValue( ).getTime( ) );
		}
		if ( t.getValue( ) != null ) {
			String strDate, strTime;
			SimpleDateFormat dfh = new SimpleDateFormat( "yyyyMMdd" );
			SimpleDateFormat dft = new SimpleDateFormat( "HHmm" );
			if ( eDate == null ) {
				eDate = new Date( );
			}
			strDate = dfh.format( eDate );
			strTime = dft.format( t.getValue( ) );
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

	public void onSelect$resultList( )
	{
		this.onClick$btnProperty( );
	}

	protected void gotoPage( Properties params )
	{
		this.gotoPage( "/private/admin/anoto/anoto_view.zul", this.getRootParent( ).getParent( ), params );
	}

	public void onClick$btnProperty( )
	{
		if ( this.resultList.getSelectedItem( ) != null ) {
			Properties params = new Properties( );
			params.put( AnotoViewController.paramName, this.resultList.getSelectedItem( ).getValue( ) );
			ListModelList model = this.getModel( );
			List list = model.getInnerList( );
			params.put( AnotoViewController.listName, list );
			this.gotoPage( params );
		}
		else {
			this.showErrorMessage( "Selecione um pgc da lista primeiro", "Visualizar PGC" );
		}
	}

	private void configureonOkEvents( )
	{
		if ( this.cmbApplication != null ) {
			this.cmbApplication.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.cmbPen != null ) {
			this.cmbPen.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.initDate != null ) {
			this.initDate.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.endDate != null ) {
			this.endDate.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.initTime != null ) {
			this.initTime.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.endTime != null ) {
			this.endTime.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.txtBarcode != null ) {
			this.txtBarcode.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.txtFormIdFrom != null ) {
			this.txtFormIdFrom.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.txtFormIdTo != null ) {
			this.txtFormIdTo.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.cmbMaxRecords != null ) {
			this.cmbMaxRecords.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
		if ( this.txtFieldValue != null ) {
			this.txtFieldValue.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					BaseSearchController.this.onClick$btnFilter( );
				}
			} );
		}
	}

	private void configureLabels( )
	{
		this.setLabel( this.labelFormView2Title );
		this.setLabel( this.labelApplication );
		this.setLabel( this.labelInitDate );
		this.setLabel( this.labelPen );
		this.setLabel( this.labelEndDate );
		this.setLabel( this.labelBarCode );
		this.setLabel( this.labelTo );
		this.setLabel( this.labelFieldValue );
		this.setLabel( this.labelMaxRecords );
		this.setLabel( this.labelFormNumber );

		this.setLabel( this.btnFilter );
		this.setLabel( this.mnuExport );
		this.setLabel( this.mnuExport2 );
		this.setLabel( this.btnSummary );

		this.setLabel( this.headSeq );
		this.setLabel( this.headApplication );
		this.setLabel( this.headFormulario );
		this.setLabel( this.headPagina );
		this.setLabel( this.headPen );
		this.setLabel( this.headDate );
		this.setLabel( this.headUserName );
		this.setLabel( this.headEmail );
		this.setLabel( this.headCellNumber );
		this.setLabel( this.headLatitude );
		this.setLabel( this.headLongitude );
		this.setLabel( this.headBarcode );
		this.setLabel( this.headPhoto );

		this.setLabel( this.tabFilter );

		this.setLabel( this.tabCustomFilter );

		this.setLabel( this.columnFieldValue );
		this.setLabel( this.columnFieldName );

		this.setLabel( this.tabSummary );
		this.setLabel( this.tabAnotoSearch );

	}

	protected Menuitem getExportButton( )
	{
		return this.mnuExport;
	}

	private void updateCharts( List<AnotoResultList> result )
	{
		if ( this.chartData == null ) {
			return;
		}
		if ( this.chartPen == null ) {
			return;
		}
		this.chartData.setVisible( SysUtils.isEmpty( result ) == false );
		this.chartPen.setVisible( SysUtils.isEmpty( result ) == false );

		this.updateChartPen( result );
	}

	private void updateChartPen( List<AnotoResultList> result )
	{
		if ( SysUtils.isEmpty( result ) ) {
			return;
		}
		HashMap<String, Double> penMap = new HashMap<String, Double>( );
		for ( AnotoResultList item : result ) {
			Double sum = penMap.get( item.getPen( ).getId( ) );
			if ( sum == null ) {
				sum = 0.0;
			}
			sum++;
			penMap.put( item.getPen( ).getId( ), sum );
		}
		SimplePieModel penModel = new SimplePieModel( );
		for ( String key : penMap.keySet( ) ) {
			penModel.setValue( key, penMap.get( key ) );
		}
		this.chartPen.setModel( penModel );
	}
}
