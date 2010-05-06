package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnotoExportFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;


public class AnotoExport
{
    private List<AnotoResultList> currentList = null;
    private AuthenticationDTO user;

    AnotoExportFacade session;


    public AnotoExport( AuthenticationDTO user )
    {
        super();
        setUser( user );
    }

    public AnotoExport( AuthenticationDTO user, List<AnotoResultList> currentList )
    {
        super();
        setUser( user );
        setCurrentList( currentList );
    }


    public Document exportToXML() throws ApplicationException
    {
        int pgcId = -1;
        int bookId = -1;
        Element form = null;
        Element book = null;
        Element root;
        Element pages = null;
        Date now = new Date();


        if ( SysUtils.isEmpty( getCurrentList() ) )
            return null;
        root = new Element( "Export" );
        root.setAttribute( "timestamp", now.toString() );
        for ( AnotoResultList item : getCurrentList() ) {
            if ( item.getPgcPage().getPgc().getId().equals( pgcId ) == false ) {
                pgcId = item.getPgcPage().getPgc().getId();
                form = createFormElement( item );
                root.addContent( form );
                bookId = -1;
            }
            if ( item.getPgcPage().getBookId().equals( bookId ) == false ) {
                bookId = item.getPgcPage().getBookId();
                book = createBookElement( item );
                pages = new Element( "Pages" );
                book.addContent( pages );
                form.addContent( book );
            }
            exportToXML( item, pages );
        }
        return new Document( root );
    }

    public Element exportToXML( AnotoResultList item, Element root ) throws ApplicationException
    {
        String xml = "";

        Element page = createPageElement( item.getPgcPage() );
        root.addContent( page );
        return root;
    }

    protected Element createFormElement( AnotoResultList r )
    {
        Element root = new Element( "Form" );
        root.addContent( new Element( "Application" ).setText( r.getForm().getApplication() ) );
        root.addContent( new Element( "Description" ).setText( r.getForm().getDescription() ) );
        root.addContent( new Element( "Pen" ).setText( r.getPen().getId() ) );
        return root;
    }

    protected Element createBookElement( AnotoResultList r )
    {
        Element root = new Element( "Book" );
        int id = r.getPgcPage().getBookId();
        id++;
        root.setAttribute( "id", "" + id );
        return root;
    }


    protected Element createPageElement( PgcPageDTO pgcPage ) throws ApplicationException
    {
        Element page = new Element( "Page" );
        page.setAttribute( "Address", pgcPage.getAnotoPage().getPageAddress() );

        List<PgcFieldDTO> fields = getSession().getFields( user, pgcPage );
        Element xmlFields = new Element( "Fields" );
        page.addContent( xmlFields );
        if ( SysUtils.isEmpty( fields ) )
            return page;
        for ( PgcFieldDTO field : fields ) {
            xmlFields.addContent( createField( field ) );
        }
        return page;
    }

    protected Element createField( PgcFieldDTO field )
    {
        Element xmlField = new Element( "Field" );
        xmlField.addContent( new Element( "Name" ).setText( field.getName() ) );
        if ( field.getType() == null )
            field.setType( new FieldTypeDTO( 1, "String" ) );
        xmlField.setAttribute( "type", field.getType().getDescription() );
        String value;
        if ( field.getType().getId().equals( FieldTypeDTO.typeBoolean ) )
            value = field.getHasPenstrokes() ? "true" : "false";
        else
            value = SysUtils.isEmpty( field.getRevisedText() ) ? field.getIrcText() : field.getRevisedText();
        Element xmlValue = new Element( "value" );
        xmlValue.setText( value );
        xmlField.addContent( xmlValue );
        xmlField.addContent( new Element( "HasPenstrokes" ).setText( field.getHasPenstrokes() ? "true" : "false" ) );
        if ( field.getHasPenstrokes() ) {
            if ( field.getStartTime() != null )
                xmlField.addContent( new Element( "StartTime" ).setText( field.getStartTime().toString() ) );
            if ( field.getEndTime() != null )
                xmlField.addContent( new Element( "EndTime" ).setText( field.getEndTime().toString() ) );
        }
        return xmlField;
    }


    public void setCurrentList( List<AnotoResultList> currentList )
    {
        this.currentList = currentList;
    }

    public List<AnotoResultList> getCurrentList()
    {
        return currentList;
    }

    public void setUser( AuthenticationDTO user )
    {
        this.user = user;
    }

    public AuthenticationDTO getUser()
    {
        return user;
    }

    protected AnotoExportFacade getRemoteSession()
    {
        try {
            return ( AnotoExportFacade )ServiceLocator.getInstance().getRemoteSession( AnotoExportFacade.class );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    public AnotoExportFacade getSession()
    {
        if ( session == null )
            session = getRemoteSession();
        return session;
    }
}
