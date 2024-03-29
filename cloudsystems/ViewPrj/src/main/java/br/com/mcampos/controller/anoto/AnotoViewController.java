package br.com.mcampos.controller.anoto;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import sun.awt.image.BufferedImageGraphicsConfig;
import br.com.mcampos.controller.anoto.base.AnotoLoggedController;
import br.com.mcampos.controller.anoto.renderer.AttatchmentGridRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboMediaRenderer;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcFieldListRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcPropertyListRenderer;
import br.com.mcampos.controller.anoto.util.AnotoExport;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.sysutils.SysUtils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class AnotoViewController extends AnotoLoggedController
{
	public static final String paramName = "viewCurrentItem";
	public static final String listName = "viewListName";

	protected Image pgcImage;
	private Listbox listFields;

	protected float imageRateSize = 0.0F;
	protected static final int targetWidth = 570;
	protected transient BufferedImage currentImage;

	protected static final String fieldOs = "Ordem de Servico OS";
	protected static final String fieldTelefoneLivre = "Numero do Telefone Livre";
	protected static final String fieldTelefonePortado = "Numero Portado";
	protected static final String fieldFend = "Venda cadastrada FEND";
	protected static final String fieldRejeitadoCEP = "Venda rejeitada por CEP inválido";
	protected static final String fieldRejeitadoCredito = "Venda rejeitada por Análise Credito";
	protected static final String fieldBackOffice = "Backoffice Responsavel";

	private AnotoResultList dtoParam;
	private List<AnotoResultList> currentList;
	private List<PgcFieldDTO> currentFields;
	private PGCDTO currentPgc;

	private Listbox listProperties;
	private Paging pagingPages;
	private Row rowBackGroundImages;
	private Combobox cmbBackgroundImages;
	private Grid gridAttach;
	private Combobox cmbZoonRate;
	private Listbox listPgcAttach;
	private Listbox listGPS;

	private Button btnExportAttach;
	private Button btnFit;
	private Button btnZoomIn;
	private Button btnZoomOut;
	private Button btnDeleteBook;
	private Button btnExport;
	private Button btnExportPDF;

	private Label labelFormViewTitle;
	private Label labelFormFields;
	private Label labelRecognition;
	private Label labelTimeDiff;
	private Label labelAdjustment;
	private Label labelPages;
	private Label labelBackgrounImage;

	private Tab tabIcr;
	private Tab tabPgc;
	private Tab tabbarCode;
	private Tab tabPhotos;
	private Tab tabGPS;
	private Tab tabBackoffice;

	private Gmaps gmapGPS;

	private Intbox ordemServico;
	private Textbox telefoneLivre;
	private Textbox numeroPortado;
	private Radio rejeitadoCEP;
	private Radio rejeitadoCredito;

	private Radio vendaCadastrada;
	private Radio vendaInvalida;

	private Grid gridVendaOK;
	private Radiogroup optionRejeicao;

	private AnotoViewController( char c )
	{
		super( c );
	}

	public AnotoViewController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		configureLabels( );
		cmbBackgroundImages.setItemRenderer( new ComboMediaRenderer( ) );
		gridAttach.setRowRenderer( new AttatchmentGridRenderer( ) );
		pgcImage.addEventListener( Events.ON_CLICK, new EventListener( )
		{
			@Override
			public void onEvent( Event event )
			{
				// pgcImageAreaClick( ( MouseEvent )event );
			}
		} );
		if ( dtoParam != null )
			process( dtoParam );
		loadZoomRate( );
		listPgcAttach.setItemRenderer( new MediaListRenderer( ) );
		listGPS.setItemRenderer( new PgcPropertyListRenderer( ) );
		listProperties.setItemRenderer( new PgcPropertyListRenderer( ) );
		if ( listFields.getPaginal( ) != null )
			listFields.setPageSize( 19 );
		// listFields.setPagingPosition( "both" );
		listFields.setItemRenderer( new PgcFieldListRenderer( ) );
		preparePaging( );
	}

	public void onClick$btnBackofficeSave( )
	{
		if ( dtoParam == null )
			return;
		PgcFieldDTO field;
		if ( vendaCadastrada.isChecked( ) == false && vendaInvalida.isChecked( ) == false ) {
			showErrorMessage( "Por favor, escolha uma opção para a situação do formulário." );
			return;
		}
		if ( vendaCadastrada.isChecked( ) ) {
			if ( SysUtils.isZero( ordemServico.getValue( ) ) ) {
				showErrorMessage( "A ordem de serviço não pode estar em branco." );
				return;
			}
			if ( SysUtils.isEmpty( telefoneLivre.getValue( ) ) && SysUtils.isEmpty( numeroPortado.getValue( ) ) ) {
				showErrorMessage( "O número do Telefone Livre ou Número Portado deve estar preenchido" );
				return;
			}
			if ( SysUtils.isEmpty( telefoneLivre.getValue( ) ) == false && SysUtils.isEmpty( numeroPortado.getValue( ) ) == false ) {
				showErrorMessage( "Apenas uma das informações (Telefone Livre ou Número Portado) pode estar preenchida." );
				return;
			}
		}
		else if ( rejeitadoCredito.isChecked( ) == false && rejeitadoCEP.isChecked( ) == false ) {
			showErrorMessage( "Favor informar o motivo da rejeição" );
			return;
		}
		try {
			field = findField( fieldFend );
			field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
			field.setHasPenstrokes( vendaCadastrada.isChecked( ) );
			getSession( ).update( getLoggedInUser( ), field );

			field = findField( fieldBackOffice );
			field.setType( new FieldTypeDTO( FieldTypeDTO.typeString ) );
			field.setIrcText( getLoggedInUser( ).getUserId( ).toString( ) );
			getSession( ).update( getLoggedInUser( ), field );

			if ( vendaCadastrada.isChecked( ) ) {

				field = findField( fieldOs );
				field.setRevisedText( ordemServico.getValue( ).toString( ) );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldTelefoneLivre );
				field.setRevisedText( telefoneLivre.getValue( ) );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldTelefonePortado );
				field.setRevisedText( numeroPortado.getValue( ) );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldRejeitadoCredito );
				field.setHasPenstrokes( false );
				field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldRejeitadoCEP );
				field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
				field.setHasPenstrokes( false );
				getSession( ).update( getLoggedInUser( ), field );
				Messagebox.show( "O formulário foi cadastrado no FEND" );
			}
			else {
				field = findField( fieldOs );
				field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
				field.setRevisedText( "" );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldRejeitadoCredito );
				field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
				field.setHasPenstrokes( rejeitadoCredito.isChecked( ) );
				getSession( ).update( getLoggedInUser( ), field );

				field = findField( fieldRejeitadoCEP );
				field.setType( new FieldTypeDTO( FieldTypeDTO.typeBoolean ) );
				field.setHasPenstrokes( rejeitadoCEP.isChecked( ) );
				getSession( ).update( getLoggedInUser( ), field );
				Messagebox.show( "O formulário foi rejeitado." );
			}
			showFields( dtoParam );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ) );
		}
	}

	private void clearBackofficeStatus( )
	{
		vendaCadastrada.setChecked( false );
		vendaInvalida.setChecked( false );
		gridVendaOK.setVisible( false );
		optionRejeicao.setVisible( false );
	}

	public PgcFieldDTO findField( String name )
	{
		for ( PgcFieldDTO field : currentFields )
			if ( field.getName( ).equalsIgnoreCase( name ) )
				return field;
		PgcFieldDTO field = new PgcFieldDTO( dtoParam.getPgcPage( ) );
		field.setEndTime( 0L );
		field.setHasPenstrokes( false );
		field.setIrcText( "" );
		field.setMedia( null );
		field.setName( name );
		field.setRevisedText( "" );
		field.setSequence( 90 );
		field.setStartTime( 0L );
		field.setType( new FieldTypeDTO( 1 ) );
		return field;
	}

	protected void preparePaging( )
	{
		if ( currentList == null || currentList.size( ) == 1 )
			return;
		int nIndex = currentList.indexOf( dtoParam );
		pagingPages.setTotalSize( currentList.size( ) );
		pagingPages.setPageSize( 1 );
		pagingPages.setPageIncrement( 1 );
		pagingPages.setActivePage( nIndex );
	}

	protected void loadZoomRate( )
	{
		for ( Integer nRate = 10; nRate <= 150; nRate += 10 ) {
			Comboitem item = cmbZoonRate.appendItem( nRate.toString( ) + "%" );
			item.setValue( nRate );
		}
	}

	@Override
	public ComponentInfo doBeforeCompose( org.zkoss.zk.ui.Page page, Component parent, ComponentInfo compInfo )
	{
		Object param = getParameter( );

		if ( param != null ) {
			ComponentInfo obj = super.doBeforeCompose( page, parent, compInfo );
			return obj;
		}
		else
			return null;
	}

	protected Object getParameter( )
	{
		Object param;

		Map args = Executions.getCurrent( ).getArg( );
		if ( args == null || args.size( ) == 0 )
			args = Executions.getCurrent( ).getParameterMap( );
		param = args.get( paramName );
		if ( param instanceof AnotoResultList ) {
			dtoParam = (AnotoResultList) param;
			currentList = (List<AnotoResultList>) args.get( listName );
			return dtoParam;
		}
		else
			return null;
	}

	protected void process( AnotoResultList target ) throws ApplicationException
	{
		loadImages( target );
		showFields( target );
		showAttachments( target );
	}

	protected void showAttachments( AnotoResultList target ) throws ApplicationException
	{
		List<PgcAttachmentDTO> attachments = getSession( ).getAttachments( getLoggedInUser( ), target.getPgcPage( ) );
		gridAttach.setModel( new ListModelList( attachments, true ) );
		if ( currentPgc == null || currentPgc.compareTo( target.getPgcPage( ).getPgc( ) ) != 0 ) {
			currentPgc = target.getPgcPage( ).getPgc( );
			listPgcAttach.setModel( new ListModelList( getSession( ).getAttachments( getLoggedInUser( ), currentPgc ) ) );

			List<PgcPropertyDTO> list = getSession( ).getGPS( getLoggedInUser( ), currentPgc );
			listGPS.setModel( new ListModelList( list ) );
			if ( gmapGPS != null ) {
				gmapGPS.setVisible( SysUtils.isEmpty( list ) == false );
				if ( SysUtils.isEmpty( list ) == false ) {
					String latitude;
					String longitude;

					latitude = list.get( 3 ).getValue( );
					longitude = list.get( 4 ).getValue( );
					try {
						gmapGPS.setLat( Double.parseDouble( latitude ) );
					}
					catch ( NumberFormatException e ) {
						e = null;
						gmapGPS.setLat( 0.0 );
					}
					try {
						gmapGPS.setLng( Double.parseDouble( longitude ) );
					}
					catch ( NumberFormatException e ) {
						e = null;
						gmapGPS.setLng( 0.0 );
					}

				}
				listProperties.setModel( new ListModelList( getSession( ).getProperties( getLoggedInUser( ), currentPgc ) ) );
			}
		}
	}

	protected void showFields( AnotoResultList target ) throws ApplicationException
	{
		currentFields = getSession( ).getFields( getLoggedInUser( ), target.getPgcPage( ) );
		ListModelList model = (ListModelList) listFields.getModel( );
		if ( model == null ) {
			model = new ListModelList( new ArrayList<PgcFieldDTO>( ), true );
			listFields.setModel( model );
		}
		model.clear( );
		model.addAll( currentFields );

		ordemServico.setValue( 0 );
		boolean bBackoffice = false;
		for ( PgcFieldDTO field : currentFields ) {
			if ( field.getName( ).equalsIgnoreCase( fieldBackOffice ) )
				bBackoffice = true;
			if ( field.getName( ).equalsIgnoreCase( fieldOs ) )
				try {
					ordemServico.setValue( Integer.parseInt( field.getValue( ) ) );
				}
				catch ( Exception e ) {
					ordemServico.setValue( 0 );
				}
			else if ( field.getName( ).equalsIgnoreCase( fieldTelefoneLivre ) )
				telefoneLivre.setValue( field.getValue( ) );
			else if ( field.getName( ).equalsIgnoreCase( fieldTelefonePortado ) )
				numeroPortado.setValue( field.getValue( ) );
		}
		if ( bBackoffice == false ) {
			tabBackoffice.setVisible( true );
			tabBackoffice.setSelected( true );
			clearBackofficeStatus( );
		}
		else {
			tabBackoffice.setVisible( false );
			tabIcr.setSelected( true );
		}
	}

	public void onChange$numeroPortado( )
	{
		telefoneLivre.setDisabled( SysUtils.isEmpty( numeroPortado.getValue( ) ) == false );
	}

	protected void loadImages( AnotoResultList target ) throws ApplicationException
	{
		List<MediaDTO> images;
		Comboitem item;

		images = getSession( ).getImages( target.getPgcPage( ) );
		rowBackGroundImages.setVisible( SysUtils.isEmpty( images ) == false && images.size( ) > 1 );
		if ( SysUtils.isEmpty( images ) )
			return;
		cmbBackgroundImages.getChildren( ).clear( );
		for ( MediaDTO media : images ) {
			item = cmbBackgroundImages.appendItem( media.toString( ) );
			item.setValue( media );
		}
		cmbBackgroundImages.setSelectedIndex( 0 );
		onSelect$cmbBackgroundImages( );
	}

	public void onSelect$cmbBackgroundImages( )
	{
		byte[ ] object;
		MediaDTO media;
		Comboitem item = cmbBackgroundImages.getSelectedItem( );

		if ( item == null )
			return;
		try {
			media = (MediaDTO) item.getValue( );
			object = getSession( ).getObject( media );
			loadImage( object );
			showImage( );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ), "Mostrar Imagem" );
		}
	}

	protected void loadImage( byte[ ] object ) throws IOException
	{
		ByteArrayInputStream is = new ByteArrayInputStream( object );
		currentImage = ImageIO.read( is );
	}

	protected void showImage( ) throws IOException
	{
		int w = currentImage.getWidth( );
		int h = currentImage.getHeight( );

		if ( imageRateSize == 0.0F )
			sizeToFit( );
		if ( imageRateSize != 1.0F ) {
			w *= imageRateSize;
			h *= imageRateSize;
			BufferedImage bufferedImage = resizeTrick( currentImage, w, h );
			pgcImage.setContent( bufferedImage );
		}
		else
			pgcImage.setContent( currentImage );
	}

	private static BufferedImage resizeTrick( BufferedImage image, int width, int height )
	{
		image = createCompatibleImage( image );
		// image = blurImage( image );
		image = resize( image, width, height );
		return image;
	}

	private static BufferedImage createCompatibleImage( BufferedImage image )
	{
		GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig( image );
		int w = image.getWidth( );
		int h = image.getHeight( );
		BufferedImage result = gc.createCompatibleImage( w, h, Transparency.TRANSLUCENT );
		Graphics2D g2 = result.createGraphics( );
		g2.drawRenderedImage( image, null );
		g2.dispose( );
		return result;
	}

	public static BufferedImage blurImage( BufferedImage image )
	{
		float ninth = 1.0f / 9.0f;
		float[ ] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };

		Map map = new HashMap( );

		map.put( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

		map.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		map.put( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		RenderingHints hints = new RenderingHints( map );
		BufferedImageOp op = new ConvolveOp( new Kernel( 3, 3, blurKernel ), ConvolveOp.EDGE_NO_OP, hints );
		return op.filter( image, null );
	}

	private static BufferedImage resize( BufferedImage image, int width, int height )
	{
		int type = image.getType( ) == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType( );
		BufferedImage resizedImage = new BufferedImage( width, height, type );
		Graphics2D g = resizedImage.createGraphics( );
		g.setComposite( AlphaComposite.Src );

		g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

		g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		g.drawImage( image, 0, 0, width, height, null );
		g.dispose( );
		return resizedImage;
	}

	protected void sizeToFit( )
	{
		if ( currentImage.getWidth( ) > targetWidth )
			imageRateSize = ( (float) targetWidth ) / ( (float) currentImage.getWidth( ) );
		else
			imageRateSize = 1.0F;
	}

	public void onClick$btnFit( )
	{
		if ( currentImage == null )
			return;
		try {
			sizeToFit( );
			showImage( );
		}
		catch ( IOException e ) {
			showErrorMessage( e.getMessage( ), "Ajustar Imagem" );
		}
	}

	public void onClick$btnZoomIn( )
	{
		if ( currentImage == null )
			return;
		imageRateSize += 0.1;
		try {
			showImage( );
		}
		catch ( IOException e ) {
			showErrorMessage( e.getMessage( ), "Ajustar Imagem" );
		}
	}

	public void onSelect$cmbZoonRate( )
	{

		Comboitem item = cmbZoonRate.getSelectedItem( );
		if ( item == null ) {
			String value = cmbZoonRate.getText( );
			if ( SysUtils.isEmpty( value ) )
				return;
		}
		else {
			Integer rate = (Integer) item.getValue( );
			imageRateSize = ( rate.floatValue( ) / 100F );
		}
		try {
			showImage( );
		}
		catch ( IOException e ) {
			showErrorMessage( e.getMessage( ), "Ajustar Imagem" );
		}
	}

	public void onClick$btnZoomOut( )
	{
		if ( currentImage == null )
			return;
		if ( imageRateSize > .2 )
			imageRateSize -= 0.1;
		try {
			showImage( );
		}
		catch ( IOException e ) {
			showErrorMessage( e.getMessage( ), "Ajustar Imagem" );
		}
	}

	public void onPaging$pagingPages( )
	{
		dtoParam = currentList.get( pagingPages.getActivePage( ) );
		try {
			process( dtoParam );
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Paging" );
		}
	}

	public void onClick$btnExport( ) throws InterruptedException
	{
		AnotoExport export = new AnotoExport( getLoggedInUser( ), currentList );

		Document doc;
		try {
			doc = export.exportToXML( );
			if ( doc != null ) {
				XMLOutputter output = new XMLOutputter( Format.getPrettyFormat( ) );
				String str = output.outputString( doc );
				Filedownload.save( str, "text/xml", currentList.get( 0 ).getForm( ).getApplication( ) + ".xml" );
			}
		}
		catch ( ApplicationException e ) {
			Messagebox.show( e.getMessage( ) );
			doc = null;
		}

	}

	protected Element createRootElement( AnotoResultList r )
	{
		Element root = new Element( "Form" );
		root.addContent( new Element( "Application" ).setText( r.getForm( ).getApplication( ) ) );
		root.addContent( new Element( "Description" ).setText( r.getForm( ).getApplication( ) ) );
		root.addContent( new Element( "Pen" ).setText( r.getPen( ).getId( ) ) );

		return root;
	}

	public void onClick$btnDeleteBook( )
	{
		if ( pagingPages.getTotalSize( ) > 0 )
			try {
				getSession( ).remove( getLoggedInUser( ), dtoParam );
				pagingPages.setTotalSize( pagingPages.getTotalSize( ) - 1 );
				currentList.remove( dtoParam );
				if ( pagingPages.getTotalSize( ) == 0 )
					gotoPage( "/private/admin/anoto/anoto_view2.zul", getRootParent( ) );
				else
					onPaging$pagingPages( );
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ), "Excluir Página" );
			}

	}

	public void onClick$btnExportAttach( )
	{
		try {
			Listitem item = listPgcAttach.getSelectedItem( );

			if ( item != null && item.getValue( ) != null ) {
				MediaDTO media = (MediaDTO) item.getValue( );
				byte[ ] obj;
				obj = getSession( ).getObject( media );
				if ( obj != null )
					Filedownload.save( obj, media.getMimeType( ), media.getName( ) );
			}
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Download" );
		}
	}

	public void onClick$btnExportPDF( )
	{
		if ( dtoParam == null || dtoParam.getForm( ) == null || currentFields == null )
			return;
		FormDTO form = dtoParam.getForm( );
		byte[ ] pdfByte;
		try {
			pdfByte = getSession( ).getPDFTemplate( getLoggedInUser( ), form );
			if ( pdfByte != null ) {
				ByteArrayInputStream bais = new ByteArrayInputStream( pdfByte );
				PdfReader reader = new PdfReader( bais );
				File outFile = File.createTempFile( "export", ".pdf" );
				FileOutputStream out = new FileOutputStream( outFile );
				PdfStamper pdfOut = new PdfStamper( reader, out );

				AcroFields fields = pdfOut.getAcroFields( );
				for ( PgcFieldDTO field : currentFields ) {
					String fName;
					if ( SysUtils.isEmpty( field.getValue( ) ) )
						continue;
					if ( field.isBoolean( ) ) {
						fName = String.format( "%s_%02d", field.getName( ), 1 );
						fields.setField( field.getName( ) + "_01", "X" );
					}
					else
						for ( int index = 0; index < field.getValue( ).length( ); index++ ) {
							fName = String.format( "%s_%02d", field.getName( ), index + 1 );
							String fValue = field.getValue( ).substring( index, index + 1 );
							fields.setField( fName, fValue );
						}
				}
				pdfOut.close( );
				Filedownload.save( outFile, "application/pdf" );
				out.close( );
			}
			else
				showErrorMessage( "Esta aplicação não possui template em pdf" );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ) );
		}
	}

	private void configureLabels( )
	{
		setLabel( btnExportAttach );
		setLabel( btnFit );
		setLabel( btnZoomIn );
		setLabel( btnZoomOut );
		setLabel( btnDeleteBook );
		setLabel( btnExport );
		setLabel( btnExportPDF );

		setLabel( labelFormViewTitle );
		setLabel( labelFormFields );
		setLabel( labelRecognition );
		setLabel( labelTimeDiff );
		setLabel( labelAdjustment );
		setLabel( labelPages );
		setLabel( labelBackgrounImage );

		setLabel( tabIcr );
		setLabel( tabPgc );
		setLabel( tabbarCode );
		setLabel( tabPhotos );
		setLabel( tabGPS );

	}

}
