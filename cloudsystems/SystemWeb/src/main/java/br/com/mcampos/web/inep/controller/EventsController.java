package br.com.mcampos.web.inep.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import br.com.mcampos.ExcelFile;
import br.com.mcampos.dto.inep.InepStationSubscriptionResponsableImportDTO;
import br.com.mcampos.dto.inep.InepSubscriptionImportDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.packs.InepPackageSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.UploadMedia;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.core.listbox.ListboxParams;
import br.com.mcampos.web.renderer.inep.EventListRenderer;

public class EventsController extends BaseDBListController<InepPackageSession, InepEvent>
{
	private static final long serialVersionUID = 3519756939620280341L;
	private static final Logger LOGGER = LoggerFactory.getLogger( EventsController.class );

	@Wire
	private Label infoId;

	@Wire
	private Label infoDescription;

	@Wire
	private Label infoInitDate;

	@Wire
	private Label infoEndDate;

	@Wire
	private Intbox id;

	@Wire
	private Textbox description;

	@Wire
	private Datebox initDate;

	@Wire
	private Datebox endDate;

	@Wire
	private Toolbarbutton loadSubscriptions;

	@Wire
	private Toolbarbutton loadStationResponsables;

	@Override
	protected Class<InepPackageSession> getSessionClass( )
	{
		return InepPackageSession.class;
	}

	@Override
	protected void showFields( InepEvent e )
	{
		if ( e != null )
		{
			this.infoInitDate.setValue( SysUtils.formatDate( e.getInitDate( ) ) );
			this.infoEndDate.setValue( SysUtils.formatDate( e.getEndDate( ) ) );
			this.initDate.setValue( e.getInitDate( ) );
			this.endDate.setValue( e.getEndDate( ) );
			this.infoId.setValue( e.getId( ).getId( ).toString( ) );
			this.infoDescription.setValue( e.getDescription( ) );
			this.id.setValue( e.getId( ).getId( ) );
			this.description.setValue( e.getDescription( ) );
		}
		else
		{
			this.infoInitDate.setValue( "" );
			this.infoEndDate.setValue( "" );
			this.infoId.setValue( "" );
			this.infoDescription.setValue( "" );

			this.initDate.setValue( null );
			this.endDate.setValue( new Date( ) );
			this.id.setValue( this.getSession( ).getNextId( this.getPrincipal( ) ) );
			this.description.setValue( "" );
			this.description.setFocus( true );
		}
	}

	@Override
	protected void updateTargetEntity( InepEvent target )
	{
		target.setEndDate( this.endDate.getValue( ) );
		target.setInitDate( this.initDate.getValue( ) != null ? this.initDate.getValue( ) : new Date( ) );
		target.getId( ).setCompanyId( this.getPrincipal( ).getCompanyID( ) );
		target.getId( ).setId( this.id.getValue( ) );
		target.setDescription( this.description.getValue( ) );
	}

	@Override
	protected boolean validateEntity( InepEvent entity, int operation )
	{
		return true;
	}

	@Override
	protected ListitemRenderer<?> getListRenderer( )
	{
		return new EventListRenderer( );
	}

	@Override
	protected Collection<InepEvent> getList( )
	{
		return this.getAll( 0 );
	}

	@Override
	protected Collection<InepEvent> getAll( int activePage )
	{
		return this.getSession( ).getAll( this.getPrincipal( ), new DBPaging( activePage, ListboxParams.maxListBoxPageSize ) );
	}

	@Listen( "onClick=#loadSubscriptions" )
	public void onClickUpload( MouseEvent evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		List<InepEvent> events = this.getSelectedRecords( );
		if ( SysUtils.isEmpty( events ) ) {
			Messagebox.show( "Por favor, escolha um evento primeiro", "Carregar Inscrições", Messagebox.OK, Messagebox.ERROR );
			return;
		}
		for ( InepEvent event : events ) {
			if ( event.getEndDate( ) != null && event.getEndDate( ).compareTo( new Date( ) ) <= 0 ) {
				Messagebox.show( "O evento " + event.getDescription( ) + " está fechado e não permite mais alterações",
						"Carregar Inscrições", Messagebox.OK, Messagebox.ERROR );
				return;
			}
		}
		Executions.getCurrent( ).getDesktop( ).setAttribute( "org.zkoss.zul.Fileupload.target", this.loadSubscriptions );
		Fileupload.get( "Arquivo Excel a ser importado", "Carregar Inscrições", 1, 4096, true );
	}

	/**
	 * Breaf Carregas as pessoas responsáveis pelos postos aplicadores. Esta pessoa terá acesso as funcionalidades do posto aplicador e será encarregado de
	 * inserir as notas no sistema.
	 * 
	 * @param evt
	 *            ZKoss click mouse event
	 */
	@Listen( "onClick=#loadStationResponsables" )
	public void onClickUploadStationResponsabled( MouseEvent evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		List<InepEvent> events = this.getSelectedRecords( );
		if ( SysUtils.isEmpty( events ) ) {
			Messagebox.show( "Por favor, escolha um evento primeiro", "Carregar Inscrições", Messagebox.OK, Messagebox.ERROR );
			return;
		}
		for ( InepEvent event : events ) {
			if ( event.getEndDate( ) != null && event.getEndDate( ).compareTo( new Date( ) ) <= 0 ) {
				Messagebox.show( "O evento " + event.getDescription( ) + " está fechado e não permite mais alterações",
						"Carregar Inscrições", Messagebox.OK, Messagebox.ERROR );
				return;
			}
		}
		Executions.getCurrent( ).getDesktop( ).setAttribute( "org.zkoss.zul.Fileupload.target", this.loadStationResponsables );
		Fileupload.get( "Arquivo Excel a ser importado", "Carregar Inscrições", 1, 4096, true );
	}

	@Listen( "onUpload=#loadSubscriptions; onUpload=#loadStationResponsables" )
	public void onUpload( UploadEvent evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		if ( evt.getMedia( ) == null ) {
			Messagebox.show( "Nenhum arquivo selecionado para carga", "Carregar Arquivo", Messagebox.OK, Messagebox.ERROR );
			return;
		}
		try {
			if ( evt.getTarget( ) == this.loadSubscriptions ) {
				if ( this.processFile( evt.getMedia( ) ) ) {
					Messagebox.show( "Carga do arquivo " + evt.getMedia( ).getName( ) + " realizada com sucesso", "Carregar Inscrições", Messagebox.OK,
							Messagebox.INFORMATION );
				}
			}
			else {
				if ( this.processStationResponsableFile( evt.getMedia( ) ) ) {
					Messagebox.show( "Carga do arquivo " + evt.getMedia( ).getName( ) + " realizada com sucesso",
							"Carregar Responsáveis pelos Postos Aplicadores", Messagebox.OK, Messagebox.INFORMATION );
				}
			}
		}
		catch ( Exception e ) {
			this.getSession( ).storeException( e );
			LOGGER.error( "ProcessFileError", e );
			Messagebox.show( "Erro na carga do arquivo " + evt.getMedia( ).getName( ) + ".", "Carregar Arquivo", Messagebox.OK,
					Messagebox.ERROR );
		}
	}

	private boolean processFile( Media media ) throws IOException
	{
		if ( media == null || SysUtils.isEmpty( media.getName( ) ) ) {
			return false;
		}
		ExcelFile excel = new ExcelFile( media );
		Sheet sheet = excel.getSheetAt( 0 );
		// List<InepSubscriptionImportDTO> subscriptions = new ArrayList<InepSubscriptionImportDTO>( );
		List<InepEvent> events = this.getSelectedRecords( );

		Iterator<Row> rowIterator = sheet.iterator( );
		int rowNumber = 0, rejected = 0;
		while ( rowIterator.hasNext( ) ) {
			Row row = rowIterator.next( );
			rowNumber++;
			if ( rowNumber == 1 ) {
				continue;
			}
			Iterator<Cell> cellIterator = row.iterator( );
			int cellIndex = 0;
			InepSubscriptionImportDTO dto = new InepSubscriptionImportDTO( );
			while ( cellIterator.hasNext( ) ) {
				Cell cell = cellIterator.next( );
				switch ( cell.getCellType( ) ) {
				case Cell.CELL_TYPE_BLANK:
					dto.set( cellIndex++, "" );
					break;
				case Cell.CELL_TYPE_STRING:
					dto.set( cellIndex++, cell.getStringCellValue( ) );
					break;
				case Cell.CELL_TYPE_NUMERIC:
					dto.set( cellIndex++, cell.getNumericCellValue( ) );
					break;
				}
			}
			try {
				this.getSession( ).add( this.getPrincipal( ), dto, events.get( 0 ) );
				LOGGER.info( "Processing record: " + rowNumber + ". " + dto.getName( ) );
			}
			catch ( Exception e ) {
				LOGGER.error( "Error processing record: " + rowNumber + ". " + dto.getName( ) );
				this.getSession( ).storeException( e );
				rejected++;
			}
			// subscriptions.add( dto );
		}
		// this.getSession( ).add( this.getPrincipal( ), subscriptions, events.get( 0 ) );
		this.storeUploadFile( media, rowNumber, rejected );
		return true;
	}

	private boolean processStationResponsableFile( Media media )
	{
		if ( media == null || SysUtils.isEmpty( media.getName( ) ) ) {
			return false;
		}
		ExcelFile excel = new ExcelFile( media );
		Sheet sheet = excel.getSheetAt( 0 );
		// List<InepSubscriptionImportDTO> subscriptions = new ArrayList<InepSubscriptionImportDTO>( );
		List<InepEvent> events = this.getSelectedRecords( );

		Iterator<Row> rowIterator = sheet.iterator( );
		int rowNumber = 0, rejected = 0;
		while ( rowIterator.hasNext( ) ) {
			Row row = rowIterator.next( );
			rowNumber++;
			if ( rowNumber == 1 ) {
				continue;
			}
			Iterator<Cell> cellIterator = row.iterator( );
			int cellIndex = 0;
			InepStationSubscriptionResponsableImportDTO dto = new InepStationSubscriptionResponsableImportDTO( );
			while ( cellIterator.hasNext( ) ) {
				Cell cell = cellIterator.next( );
				switch ( cell.getCellType( ) ) {
				case Cell.CELL_TYPE_BLANK:
					dto.set( cellIndex++, "" );
					break;
				case Cell.CELL_TYPE_STRING:
					dto.set( cellIndex++, cell.getStringCellValue( ) );
					break;
				case Cell.CELL_TYPE_NUMERIC:
					dto.set( cellIndex++, "" + cell.getNumericCellValue( ) );
					break;
				}
			}
			try {
				if ( !SysUtils.isEmpty( dto.getStationId( ) ) && !SysUtils.isEmpty( dto.getEmail( ) ) ) {
					LOGGER.info( "Processando{ID:" + dto.getStationId( ) + "; Name: " + dto.getName( ) + "; Address: " + dto.getAddress( ) + "; email: "
							+ dto.getEmail( ) + "}" );
					this.getSession( ).add( this.getPrincipal( ), dto, events.get( 0 ) );
					LOGGER.info( "Processing record: " + rowNumber + ". " + dto.getName( ) );
				}
				else {
					LOGGER.error( "Rejecting{ID:" + dto.getStationId( ) + "; Name: " + dto.getName( ) + "; Address: " + dto.getAddress( ) + "; email: "
							+ dto.getEmail( ) + "}" );
					rejected++;
				}
			}
			catch ( Exception e ) {
				LOGGER.error( "Error processing record: " + rowNumber + ". " + dto.getName( ) );
				this.getSession( ).storeException( e );
				rejected++;
			}
			// subscriptions.add( dto );
		}
		// this.getSession( ).add( this.getPrincipal( ), subscriptions, events.get( 0 ) );
		this.storeUploadFile( media, rowNumber, rejected );
		return true;
	}

	private void storeUploadFile( Media media, int processed, int rejected )
	{
		try {
			MediaDTO dto = UploadMedia.getMedia( media );
			this.getSession( ).storeUploadInformation( this.getPrincipal( ), dto, processed, rejected );

		}
		catch ( Exception e ) {
			LOGGER.error( "Error storing file upload information", e );
			this.getSession( ).storeException( e );
		}
	}
}
