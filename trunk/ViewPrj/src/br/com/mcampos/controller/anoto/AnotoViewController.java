package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.AttatchmentGridRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboMediaRenderer;
import br.com.mcampos.controller.anoto.util.AnotoBook;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.controller.anoto.util.PgcFile;
import br.com.mcampos.dto.anoto.AnotoResultList;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import sun.awt.image.BufferedImageGraphicsConfig;


public class AnotoViewController extends AnotoLoggedController
{
    public static final String paramName = "PgcPenPageParam";

    protected Imagemap pgcImage;
    protected Combobox fields;
    protected Row icrInfo;
    protected Row correctedInfo;
    protected Textbox correctedValue;
    protected Label icrValue;
    protected Image fieldImage;

    protected float factor = 1.0F;
    protected static final int targetWidth = 570;


    private AnotoResultList dtoParam;
    private PadFile padFile;
    private PgcFile pgcFile;


    protected Grid gridProperties;
    private Paging pagingBooks;
    private Paging pagingPages;
    private Row rowBackGroundImages;
    private Combobox cmbBackgroundImages;
    private Div divCenterImageArea;

    private List<AnotoBook> books;
    private Grid gridAttach;


    public AnotoViewController( char c )
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
    }

    @Override
    public ComponentInfo doBeforeCompose( org.zkoss.zk.ui.Page page, Component parent, ComponentInfo compInfo )
    {
        Object param = getParameter();

        if ( param != null )
            return super.doBeforeCompose( page, parent, compInfo );
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
            return dtoParam;
        }
        else
            return null;
    }

    protected void process( AnotoResultList target ) throws ApplicationException
    {
        loadImages ( target );
    }

    protected void loadImages ( AnotoResultList target ) throws ApplicationException
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

    public void onSelect$cmbBackgroundImages ()
    {
        byte[] object;
        MediaDTO media;
        Comboitem item = cmbBackgroundImages.getSelectedItem();

        if ( item == null )
            return;
        try {
            media = (MediaDTO)item.getValue ();
            object = getSession().getObject( media );
            showImage( object );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Mostrar Imagem" );
        }
    }

    protected void showImage ( byte[] object ) throws IOException
    {
        ByteArrayInputStream is = new ByteArrayInputStream( object );
        BufferedImage bufferedImage = ImageIO.read( is );
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        float r = 1.0F;

        if ( w > targetWidth )
        {
            r = ((float)w) / ((float)targetWidth);
        }
        w *= .35;
        h *= .35;

        bufferedImage = resizeTrick( bufferedImage, w, h );

        pgcImage.setContent( bufferedImage );
    }

    private static BufferedImage resizeTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        //image = resize(image, 100, 100);
        image = blurImage(image);
        image = resize(image, width, height);
        return image;
    }

    private static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage(image, null);
        g2.dispose();
        return result;
    }


    public static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f/9.0f;
        float[] blurKernel = {
        ninth, ninth, ninth,
        ninth, ninth, ninth,
        ninth, ninth, ninth
        };

        Map map = new HashMap();

        map.put(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
