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
		configureLabels( );
		refresh( );
		if ( resultList != null )
			resultList.setItemRenderer( new PgcPenPageListRenderer( ) );
		if ( cmbMaxRecords != null )
			cmbMaxRecords.setSelectedIndex( 0 );
		configureonOkEvents( );
	}

	private void refresh( )
	{
		loadApplication( );
		// loadPages( null );
		// loadPens( null );
		// loadPGC( null );
	}

	private void loadApplication( )
	{
		if ( cmbApplication == null )
			return;
		List list;
		try {
			list = getSession( ).getForms( getLoggedInUser( ) );
			loadCombobox( cmbApplication, list );
			if ( cmbApplication.getItemCount( ) > 0 ) {
				cmbApplication.setSelectedIndex( 0 );
				getApplicationProperties( );
			}
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Carregar Aplicações" );
		}
	}

	private void loadPages( FormDTO dto )
	{
		ListModelList model;
		List<AnotoPageDTO> list;
		try {
			if ( dto == null )
				list = getSession( ).getPages( getLoggedInUser( ) );
			else
				list = getSession( ).getPages( getLoggedInUser( ), dto );
			model = new ListModelList( list );
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Carregar Páginas" );
		}
	}

	private void loadPens( FormDTO dto )
	{
		List list;
		try {
			list = getSession( ).getPens( getLoggedInUser( ), dto );
			loadCombobox( cmbPen, list );
			Comboitem item = cmbPen.appendItem( getLabel( "all_female" ) );
			if ( item != null )
				cmbPen.setSelectedItem( item );
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Carregar Canetas" );
		}
	}

	private void getApplicationProperties( )
	{
		if ( cmbApplication.getSelectedItem( ) == null )
			return;
		FormDTO form = (FormDTO) cmbApplication.getSelectedItem( ).getValue( );

		loadPages( form );
		loadPens( form );
		loadCustom( form );
	}

	private void loadCustom( FormDTO form )
	{
		if ( gridCustomFields == null || tabCustomFilter == null )
			return;
		List<AnotoPageFieldDTO> fields = Collections.emptyList( );
		try {
			fields = getSession( ).getSearchableFields( getLoggedInUser( ), form );
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Lista de PGC" );
		}
		Rows rows = gridCustomFields.getRows( );
		if ( rows == null ) {
			rows = new Rows( );
			rows.setParent( gridCustomFields );
		}
		if ( rows.getChildren( ) != null )
			rows.getChildren( ).clear( );
		if ( SysUtils.isEmpty( fields ) == false ) {
			tabCustomFilter.setVisible( true );
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
		else
			tabCustomFilter.setVisible( false );

	}

	public void onSelect$cmbApplication( )
	{
		getApplicationProperties( );
	}

	/*
	 * This method can be overridable.
	 */

	protected void loadPGC( Properties prop )
	{
		List<AnotoResultList> dtos;
		try {
			Integer id = Integer.parseInt( cmbMaxRecords.getSelectedItem( ).getLabel( ) );
			dtos = getSession( ).getAllPgcPenPage( getLoggedInUser( ), prop, id, true );
			ListModelList model = getModel( );
			model.clear( );
			model.addAll( dtos );
			updateCharts( dtos );
			// resultList.invalidate();
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Lista de PGC" );
		}
	}

	/*Used in derived classes*/

	protected ListModelList getModel( )
	{
		ListModelList model = (ListModelList) resultList.getModel( );
		if ( model == null ) {
			model = new ListModelList( new ArrayList<AnotoResultList>( ), true );
			resultList.setModel( model );
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
			writeToExcell( workbook );
			Filedownload.save( file, "application/vnd.ms-excel" );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ) );
		}
	}

	public void onClick$mnuExport2( )
	{
		File file;
		try {
			file = File.createTempFile( "export", ".xls" );
			file.deleteOnExit( );
			WritableWorkbook workbook = Workbook.createWorkbook( file );
			writeToExcell2( workbook );
			Filedownload.save( file, "application/vnd.ms-excel" );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ) );
		}
	}

	private void writeToExcell( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
	{
		WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
		int nColumns = setHeader( sheet );
		ListModelList model = getModel( );
		boolean bFirst = true;
		for ( int nIndex = 0; nIndex < model.getSize( ); nIndex++ ) {
			AnotoResultList dto = (AnotoResultList) model.get( nIndex );
			if ( dto != null ) {
				if ( bFirst ) {
					bFirst = false;
					addHead( sheet, nColumns, dto.getFields( ) );
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
				addData( sheet, nColumns, nIndex + 1, dto.getFields( ) );
			}
		}
		workbook.write( );
		workbook.close( );
	}

	private void writeToExcell2( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
	{
		WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
		int nColumns = setHeader( sheet );
		ListModelList model = getModel( );
		boolean bFirst = true;
		for ( int nIndex = 0; nIndex < model.getSize( ); nIndex++ ) {
			AnotoResultList dto = (AnotoResultList) model.get( nIndex );
			if ( dto != null ) {
				if ( bFirst ) {
					bFirst = false;
					addHead( sheet, nColumns, dto.getFields( ) );
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
					addData( sheet, nColumns, nIndex + 1, getSession( ).getFields( getLoggedInUser( ), dto.getPgcPage( ) ) );
				}
				catch ( ApplicationException e ) {
					showErrorMessage( e.getMessage( ) );
				}
			}
		}
		workbook.write( );
		workbook.close( );
	}

	private void addHead( WritableSheet sheet, int nColumns, List<PgcFieldDTO> fields ) throws WriteException,
			RowsExceededException
	{
		if ( SysUtils.isEmpty( fields ) )
			return;
		for ( PgcFieldDTO field : fields )
			sheet.addCell( new jxl.write.Label( nColumns++, 0, field.getName( ) ) );
	}

	private void addData( WritableSheet sheet, int nColumn, int nRow, List<PgcFieldDTO> fields ) throws WriteException,
			RowsExceededException
	{
		if ( SysUtils.isEmpty( fields ) )
			return;
		for ( PgcFieldDTO field : fields ) {
			String value;
			if ( field.getType( ).getId( ).equals( FieldTypeDTO.typeBoolean ) )
				value = field.getHasPenstrokes( ) ? "SIM" : "";
			else
				value = SysUtils.isEmpty( field.getRevisedText( ) ) ? field.getIrcText( ) : field.getRevisedText( );
			sheet.addCell( new jxl.write.Label( nColumn++, nRow, value ) );
		}
	}

	private int setHeader( WritableSheet sheet ) throws WriteException, RowsExceededException
	{
		int nIndex = 1;
		jxl.write.Label l = new jxl.write.Label( 0, 0, headSeq.getLabel( ) );
		sheet.addCell( l );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headApplication.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headFormulario.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headPagina.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headPen.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headDate.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headUserName.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headEmail.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headCellNumber.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headLatitude.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headLongitude.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headBarcode.getLabel( ) ) );
		sheet.addCell( new jxl.write.Label( nIndex++, 0, headPhoto.getLabel( ) ) );
		return nIndex;
	}

	private void verifyCustomFields( Properties prop )
	{
		if ( gridCustomFields == null )
			return;
		Properties custom = new Properties( );

		Rows rows = gridCustomFields.getRows( );
		if ( rows == null || rows.getChildren( ) == null || rows.getChildren( ).size( ) < 1 )
			return;
		for ( int nIndex = 0; nIndex < rows.getChildren( ).size( ); nIndex++ ) {
			Row row = (Row) rows.getChildren( ).get( nIndex );
			if ( row == null )
				continue;
			String fieldName = ( (Label) row.getChildren( ).get( 0 ) ).getValue( );
			Object value = null;
			XulElement c = ( (XulElement) row.getChildren( ).get( 1 ) );
			if ( c instanceof InputElement ) {
				String sValue = ( (InputElement) c ).getRawText( );
				if ( SysUtils.isEmpty( sValue ) )
					continue;
				value = sValue.trim( );
			}
			else if ( c instanceof Checkbox ) {
				Boolean b = ( (Checkbox) c ).isChecked( );
				value = b;
			}
			custom.put( fieldName, value );
		}
		if ( custom.size( ) > 0 )
			prop.put( "custom_fields", custom );
	}

	private Properties getFilters( )
	{
		Properties prop = new Properties( );

		/*
		* Does form combo selected??
		*/
		if ( cmbApplication.getSelectedItem( ) != null )
			prop.put( "form", cmbApplication.getSelectedItem( ).getValue( ) );
		/*
		* Does page combo selected or have this combo a text?
		*/
		String strInfo = "";
		if ( strInfo.length( ) > 0 )
			prop.put( "page", strInfo );
		/*
		* Does pen combo is selected? Have any text?
		*/
		String penInfo = "";
		if ( cmbPen.getSelectedItem( ) != null ) {
			PenDTO pen;

			pen = (PenDTO) cmbPen.getSelectedItem( ).getValue( );
			if ( pen != null )
				penInfo = pen.getId( );
		}
		if ( penInfo.length( ) > 0 )
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
		String barCode = txtBarcode.getValue( );
		if ( SysUtils.isEmpty( barCode ) == false )
			prop.put( "barCode", barCode );
		/*
		* Does we have a barcode?
		*/
		Integer bookIdFrom = txtFormIdFrom.getValue( );
		if ( SysUtils.isZero( bookIdFrom ) == false ) {
			/*
			* Truque. No renderer somamos + 1 ao book id (zero based), porém zero não faz sentido ao usuario
			*/
			bookIdFrom--;
			prop.put( "bookIdFrom", bookIdFrom );
		}
		Integer bookIdTo = txtFormIdTo.getValue( );
		if ( SysUtils.isZero( bookIdTo ) == false ) {
			bookIdTo--;
			prop.put( "bookIdTo", bookIdTo );
		}
		String fieldValue = txtFieldValue.getValue( );
		if ( SysUtils.isEmpty( fieldValue ) == false )
			prop.put( "pgcFieldValue", fieldValue );
		verifyCustomFields( prop );
		return prop;
	}

	public void onClick$btnFilter( )
	{
		loadPGC( getFilters( ) );
		if ( tabAnotoSearch != null )
			tabAnotoSearch.setSelected( true );
	}

	private Date getDate( Datebox d, Timebox t )
	{
		Date eDate = null;
		if ( d.getValue( ) != null )
			eDate = new Date( d.getValue( ).getTime( ) );
		if ( t.getValue( ) != null ) {
			String strDate, strTime;
			SimpleDateFormat dfh = new SimpleDateFormat( "yyyyMMdd" );
			SimpleDateFormat dft = new SimpleDateFormat( "HHmm" );
			if ( eDate == null )
				eDate = new Date( );
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
		onClick$btnProperty( );
	}

	protected void gotoPage( Properties params )
	{
		gotoPage( "/private/admin/anoto/anoto_view.zul", getRootParent( ).getParent( ), params );
	}

	public void onClick$btnProperty( )
	{
		if ( resultList.getSelectedItem( ) != null ) {
			Properties params = new Properties( );
			params.put( AnotoViewController.paramName, resultList.getSelectedItem( ).getValue( ) );
			ListModelList model = getModel( );
			List list = model.getInnerList( );
			params.put( AnotoViewController.listName, list );
			gotoPage( params );
		}
		else
			showErrorMessage( "Selecione um pgc da lista primeiro", "Visualizar PGC" );
	}

	private void configureonOkEvents( )
	{
		if ( cmbApplication != null )
			cmbApplication.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( cmbPen != null )
			cmbPen.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( initDate != null )
			initDate.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( endDate != null )
			endDate.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( initTime != null )
			initTime.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( endTime != null )
			endTime.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( txtBarcode != null )
			txtBarcode.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( txtFormIdFrom != null )
			txtFormIdFrom.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( txtFormIdTo != null )
			txtFormIdTo.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( cmbMaxRecords != null )
			cmbMaxRecords.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
		if ( txtFieldValue != null )
			txtFieldValue.addEventListener( Events.ON_OK, new EventListener( )
			{
				@Override
				public void onEvent( Event event )
				{
					onClick$btnFilter( );
				}
			} );
	}

	private void configureLabels( )
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
		setLabel( mnuExport );
		setLabel( mnuExport2 );
		setLabel( btnSummary );

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

		setLabel( tabFilter );

		setLabel( tabCustomFilter );

		setLabel( columnFieldValue );
		setLabel( columnFieldName );

		setLabel( tabSummary );
		setLabel( tabAnotoSearch );

	}

	protected Menuitem getExportButton( )
	{
		return mnuExport;
	}

	private void updateCharts( List<AnotoResultList> result )
	{
		if ( chartData == null )
			return;
		if ( chartPen == null )
			return;
		chartData.setVisible( SysUtils.isEmpty( result ) == false );
		chartPen.setVisible( SysUtils.isEmpty( result ) == false );

		updateChartPen( result );
	}

	private void updateChartPen( List<AnotoResultList> result )
	{
		if ( SysUtils.isEmpty( result ) )
			return;
		HashMap<String, Double> penMap = new HashMap<String, Double>( );
		for ( AnotoResultList item : result ) {
			Double sum = penMap.get( item.getPen( ).getId( ) );
			if ( sum == null )
				sum = 0.0;
			sum++;
			penMap.put( item.getPen( ).getId( ), sum );
		}
		SimplePieModel penModel = new SimplePieModel( );
		for ( String key : penMap.keySet( ) )
			penModel.setValue( key, penMap.get( key ) );
		chartPen.setModel( penModel );
	}
}
