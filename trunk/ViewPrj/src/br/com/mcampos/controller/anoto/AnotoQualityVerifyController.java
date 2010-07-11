package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.ReviseFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;

import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;


public class AnotoQualityVerifyController extends LoggedBaseController
{

    public static final String paramName = "viewCurrentItem";
    public static final String listName = "viewListName";

    private Label labelQualityVerifyTitle;
    private Grid gridQuality;
    private Column gridQualityColumn;

    private AnotoResultList dtoParam;
    private List<AnotoResultList> currentList;
    private List<PgcFieldDTO> currentFields;

    private Paging pagingPages;

    private ReviseFacade session;

    public AnotoQualityVerifyController( char c )
    {
        super( c );
    }

    public AnotoQualityVerifyController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelQualityVerifyTitle );
        setLabel( gridQualityColumn );
        if ( dtoParam != null ) {
            preparePaging();
            process( dtoParam );
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

    private Object getParameter()
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

    private void process( AnotoResultList target ) throws ApplicationException
    {
        currentFields = getSession().getFields( getLoggedInUser(), target.getPgcPage() );

        if ( gridQuality.getRows() != null && gridQuality.getRows().getChildren() != null )
            gridQuality.getRows().getChildren().clear();
        for ( PgcFieldDTO field : currentFields ) {
            Row row = createRow( field );
            if ( row == null )
                continue;
            gridQuality.getRows().appendChild( row );
        }
    }

    private Image loadImage( MediaDTO media )
    {
        ByteArrayInputStream is;
        try {
            is = new ByteArrayInputStream( getSession().getObject( media ) );
            BufferedImage img = ImageIO.read( is );
            Image imgField = new Image();
            imgField.setContent( img );
            return imgField;
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Carregar Imagem" );
        }
        return null;
    }

    private Textbox createTextbox( PgcFieldDTO field )
    {
        String rev;
        rev = field.getRevisedText();
        if ( SysUtils.isEmpty( rev ) )
            rev = field.getIrcText();
        Textbox textBox = new Textbox( rev );
        textBox.setWidth( "100%" );
        textBox.setAttribute( "field", field );
        textBox.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    processField( event );
                }
            } );
        textBox.addEventListener( Events.ON_BLUR, new EventListener()
            {
                public void onEvent( Event event )
                {
                    processField( event );
                }
            } );
        return textBox;
    }

    protected Row createRow( PgcFieldDTO field )
    {
        Row row = null;

        if ( field != null && field.getType().getId() != FieldTypeDTO.typeBoolean && field.getMedia() != null ) {
            MediaDTO media = field.getMedia();
            row = new Row();
            Vbox box = new Vbox();
            Label label = new Label( field.getIrcText() );
            label.setStyle( "font-size:30px" );
            box.appendChild( loadImage( media ) );
            box.appendChild( label );
            box.appendChild( createTextbox( field ) );
            row.appendChild( box );
        }
        return row;
    }

    protected void processField( Event event )
    {
        Component target = event.getTarget();

        if ( target == null || !( target instanceof Textbox ) )
            return;
        Textbox textBox = ( Textbox )target;
        PgcFieldDTO dto = ( PgcFieldDTO )textBox.getAttribute( "field" );
        if ( dto == null )
            return;
        String value = textBox.getValue();
        if ( SysUtils.isEmpty( value ) == false && !value.equals( dto.getIrcText() ) ) {
            dto.setRevisedText( value );
            try {
                getSession().update( getLoggedInUser(), dto );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
        event.stopPropagation();
    }

    private void preparePaging()
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

    private void setRevising( AnotoResultList target ) throws ApplicationException
    {
        if ( target == null || target.getPgcPage().getRevisionStatus().getId() == 2 )
            return;
        getSession().setStatus( getLoggedInUser(), target.getPgcPage(), 2 );
        target.getPgcPage().getRevisionStatus().setId( 2 );
    }


    public void onPaging$pagingPages( Event evt )
    {
        try {
            dtoParam = currentList.get( pagingPages.getActivePage() );
            if ( dtoParam != null ) {
                setRevising( dtoParam );
                process( dtoParam );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Paging" );
        }
        evt.stopPropagation();
    }

    public void onCtrlKey$rootParent( Event evt )
    {

    }

    public ReviseFacade getSession()
    {
        if ( session == null )
            session = ( ReviseFacade )getRemoteSession( ReviseFacade.class );
        return session;
    }
}
