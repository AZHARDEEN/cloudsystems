package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.base.AnotoLoggedController;
import br.com.mcampos.controller.anoto.renderer.AttatchmentGridRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboMediaRenderer;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcFieldListRenderer;
import br.com.mcampos.controller.anoto.renderer.PgcPropertyListRenderer;
import br.com.mcampos.controller.anoto.util.AnotoExport;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

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
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;

import sun.awt.image.BufferedImageGraphicsConfig;


public class AnotoViewController extends AnotoLoggedController
{
    public static final String paramName = "viewCurrentItem";
    public static final String listName = "viewListName";

    protected Image pgcImage;
    private Listbox listFields;

    protected float imageRateSize = 0.0F;
    protected static final int targetWidth = 570;
    protected transient BufferedImage currentImage;


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

    private Gmaps gmapGPS;


    AnotoViewController( char c )
    {
        super( c );
    }

    public AnotoViewController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        configureLabels();
        cmbBackgroundImages.setItemRenderer( new ComboMediaRenderer() );
        gridAttach.setRowRenderer( new AttatchmentGridRenderer() );
        pgcImage.addEventListener( Events.ON_CLICK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    //pgcImageAreaClick( ( MouseEvent )event );
                }
            } );
        if ( dtoParam != null ) {
            process( dtoParam );
        }
        loadZoomRate();
        listPgcAttach.setItemRenderer( new MediaListRenderer() );
        listGPS.setItemRenderer( new PgcPropertyListRenderer() );
        listProperties.setItemRenderer( new PgcPropertyListRenderer() );
        listFields.setItemRenderer( new PgcFieldListRenderer() );
        preparePaging();
    }

    protected void preparePaging()
    {
        if ( currentList == null || currentList.size() == 1 ) {
            return;
        }
        int nIndex = currentList.indexOf( dtoParam );
        pagingPages.setTotalSize( currentList.size() );
        pagingPages.setPageSize( 1 );
        pagingPages.setPageIncrement( 1 );
        pagingPages.setActivePage( nIndex );
    }

    protected void loadZoomRate()
    {
        for ( Integer nRate = 10; nRate <= 150; nRate += 10 ) {
            Comboitem item = cmbZoonRate.appendItem( nRate.toString() + "%" );
            item.setValue( nRate );
        }
    }

    @Override
    public ComponentInfo doBeforeCompose( org.zkoss.zk.ui.Page page, Component parent, ComponentInfo compInfo )
    {
        Object param = getParameter();

        if ( param != null ) {
            ComponentInfo obj = super.doBeforeCompose( page, parent, compInfo );
            return obj;
        }
        else
            return null;
    }

    protected Object getParameter()
    {
        Object param;

        Map args = Executions.getCurrent().getArg();
        if ( args == null || args.size() == 0 )
            args = Executions.getCurrent().getParameterMap();
        param = args.get( paramName );
        if ( param instanceof AnotoResultList ) {
            dtoParam = ( AnotoResultList )param;
            currentList = ( List<AnotoResultList> )args.get( listName );
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
        List<PgcAttachmentDTO> attachments = getSession().getAttachments( getLoggedInUser(), target.getPgcPage() );
        gridAttach.setModel( new ListModelList( attachments, true ) );
        if ( currentPgc == null || currentPgc.compareTo( target.getPgcPage().getPgc() ) != 0 ) {
            currentPgc = target.getPgcPage().getPgc();
            listPgcAttach.setModel( new ListModelList( getSession().getAttachments( getLoggedInUser(), currentPgc ) ) );

            List<PgcPropertyDTO> list = getSession().getGPS( getLoggedInUser(), currentPgc );
            listGPS.setModel( new ListModelList( list ) );
            if ( gmapGPS != null ) {
                gmapGPS.setVisible( SysUtils.isEmpty( list ) == false );
                if ( SysUtils.isEmpty( list ) == false ) {
                    String latitude;
                    String longitude;

                    latitude = list.get( 3 ).getValue();
                    longitude = list.get( 4 ).getValue();
                    gmapGPS.setLat( Double.parseDouble( latitude ) );
                    gmapGPS.setLng( Double.parseDouble( longitude ) );
                }
            }
            listProperties.setModel( new ListModelList( getSession().getProperties( getLoggedInUser(), currentPgc ) ) );
        }
    }

    protected void showFields( AnotoResultList target ) throws ApplicationException
    {
        currentFields = getSession().getFields( getLoggedInUser(), target.getPgcPage() );
        ListModelList model = ( ListModelList )listFields.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<PgcFieldDTO>(), true );
            listFields.setModel( model );
        }
        model.clear();
        model.addAll( currentFields );
    }


    protected void loadImages( AnotoResultList target ) throws ApplicationException
    {
        List<MediaDTO> images;
        Comboitem item;

        images = getSession().getImages( target.getPgcPage() );
        rowBackGroundImages.setVisible( SysUtils.isEmpty( images ) == false && images.size() > 1 );
        if ( SysUtils.isEmpty( images ) )
            return;
        cmbBackgroundImages.getChildren().clear();
        for ( MediaDTO media : images ) {
            item = cmbBackgroundImages.appendItem( media.toString() );
            item.setValue( media );
        }
        cmbBackgroundImages.setSelectedIndex( 0 );
        onSelect$cmbBackgroundImages();
    }

    public void onSelect$cmbBackgroundImages()
    {
        byte[] object;
        MediaDTO media;
        Comboitem item = cmbBackgroundImages.getSelectedItem();

        if ( item == null )
            return;
        try {
            media = ( MediaDTO )item.getValue();
            object = getSession().getObject( media );
            loadImage( object );
            showImage();
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Mostrar Imagem" );
        }
    }

    protected void loadImage( byte[] object ) throws IOException
    {
        ByteArrayInputStream is = new ByteArrayInputStream( object );
        currentImage = ImageIO.read( is );
    }

    protected void showImage() throws IOException
    {
        int w = currentImage.getWidth();
        int h = currentImage.getHeight();

        if ( imageRateSize == 0.0F ) {
            sizeToFit();
        }
        if ( imageRateSize != 1.0F ) {
            w *= imageRateSize;
            h *= imageRateSize;
            BufferedImage bufferedImage = resizeTrick( currentImage, w, h );
            pgcImage.setContent( bufferedImage );
        }
        else {
            pgcImage.setContent( currentImage );
        }
    }

    private static BufferedImage resizeTrick( BufferedImage image, int width, int height )
    {
        image = createCompatibleImage( image );
        //image = blurImage( image );
        image = resize( image, width, height );
        return image;
    }

    private static BufferedImage createCompatibleImage( BufferedImage image )
    {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig( image );
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage( w, h, Transparency.TRANSLUCENT );
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage( image, null );
        g2.dispose();
        return result;
    }


    public static BufferedImage blurImage( BufferedImage image )
    {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };

        Map map = new HashMap();

        map.put( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

        map.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

        map.put( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        RenderingHints hints = new RenderingHints( map );
        BufferedImageOp op = new ConvolveOp( new Kernel( 3, 3, blurKernel ), ConvolveOp.EDGE_NO_OP, hints );
        return op.filter( image, null );
    }

    private static BufferedImage resize( BufferedImage image, int width, int height )
    {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage( width, height, type );
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite( AlphaComposite.Src );

        g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        g.drawImage( image, 0, 0, width, height, null );
        g.dispose();
        return resizedImage;
    }

    protected void sizeToFit()
    {
        if ( currentImage.getWidth() > targetWidth ) {
            imageRateSize = ( ( float )targetWidth ) / ( ( float )currentImage.getWidth() );
        }
        else
            imageRateSize = 1.0F;
    }

    public void onClick$btnFit()
    {
        if ( currentImage == null )
            return;
        try {
            sizeToFit();
            showImage();
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Ajustar Imagem" );
        }
    }

    public void onClick$btnZoomIn()
    {
        if ( currentImage == null )
            return;
        imageRateSize += 0.1;
        try {
            showImage();
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Ajustar Imagem" );
        }
    }

    public void onSelect$cmbZoonRate()
    {

        Comboitem item = cmbZoonRate.getSelectedItem();
        if ( item == null ) {
            String value = cmbZoonRate.getText();
            if ( SysUtils.isEmpty( value ) )
                return;
        }
        else {
            Integer rate = ( Integer )item.getValue();
            imageRateSize = ( rate.floatValue() / 100F );
        }
        try {
            showImage();
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Ajustar Imagem" );
        }
    }

    public void onClick$btnZoomOut()
    {
        if ( currentImage == null )
            return;
        if ( imageRateSize > .2 )
            imageRateSize -= 0.1;
        try {
            showImage();
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Ajustar Imagem" );
        }
    }

    public void onPaging$pagingPages()
    {
        dtoParam = currentList.get( pagingPages.getActivePage() );
        try {
            process( dtoParam );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Paging" );
        }
    }

    public void onClick$btnExport()
    {
        AnotoExport export = new AnotoExport( getLoggedInUser(), currentList );

        Document doc;
        try {
            doc = export.exportToXML();
            if ( doc != null ) {
                XMLOutputter output = new XMLOutputter( Format.getPrettyFormat() );
                String str = output.outputString( doc );
                Filedownload.save( str, "text/xml", currentList.get( 0 ).getForm().getApplication() + ".xml" );
            }
        }
        catch ( ApplicationException e ) {
            try {
                Messagebox.show( e.getMessage() );
            }
            catch ( InterruptedException f ) {
                f = null;
            }
            doc = null;
        }

    }

    protected Element createRootElement( AnotoResultList r )
    {
        Element root = new Element( "Form" );
        root.addContent( new Element( "Application" ).setText( r.getForm().getApplication() ) );
        root.addContent( new Element( "Description" ).setText( r.getForm().getApplication() ) );
        root.addContent( new Element( "Pen" ).setText( r.getPen().getId() ) );

        return root;
    }

    public void onClick$btnDeleteBook()
    {
        if ( pagingPages.getTotalSize() > 0 ) {
            try {
                getSession().remove( getLoggedInUser(), dtoParam );
                pagingPages.setTotalSize( pagingPages.getTotalSize() - 1 );
                currentList.remove( dtoParam );
                if ( pagingPages.getTotalSize() == 0 )
                    gotoPage( "/private/admin/anoto/anoto_view2.zul", getRootParent() );
                else
                    onPaging$pagingPages();
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Excluir Página" );
            }
        }

    }


    public void onClick$btnExportAttach()
    {
        try {
            Listitem item = listPgcAttach.getSelectedItem();

            if ( item != null && item.getValue() != null ) {
                MediaDTO media = ( MediaDTO )item.getValue();
                byte[] obj;
                obj = getSession().getObject( media );
                if ( obj != null ) {
                    Filedownload.save( obj, media.getMimeType(), media.getName() );
                }
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Download" );
        }
    }


    private void configureLabels()
    {
        setLabel( btnExportAttach );
        setLabel( btnFit );
        setLabel( btnZoomIn );
        setLabel( btnZoomOut );
        setLabel( btnDeleteBook );
        setLabel( btnExport );

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
