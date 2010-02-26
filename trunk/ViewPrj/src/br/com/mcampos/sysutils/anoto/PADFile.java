package br.com.mcampos.sysutils.anoto;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.io.Reader;

import java.util.Collections;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PADFile
{
    protected static final String licenceChildName = "license";
    protected static final String spaceDefinitionChildName = "spacedefinition";
    protected static final String pagesChildName = "pages";
    protected static final String addressAttributeName = "address";
    protected Document document;

    public PADFile()
    {
        super();
    }

    public PADFile( byte pad )
    {

    }

    public void load( Reader is ) throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        document = builder.build( is );
    }

    public List<Element> getPages()
    {
        Element pages;
        pages = getRoot() == null ? null : getRoot().getChild( pagesChildName );
        return pages.getChildren( "page" );
    }

    public String getPageAddress( Element pageElement )
    {
        return pageElement.getAttributeValue( addressAttributeName );
    }

    public int getNumberOfPages()
    {
        return getPages() == null ? 0 : getPages().size();
    }

    public Element getRoot()
    {
        return ( document == null ) ? null : document.getRootElement();
    }

    public int getVersion()
    {
        Element root = getRoot();
        if ( root == null )
            return 0;
        try {
            return root.getAttribute( "version" ).getIntValue();
        }
        catch ( DataConversionException e ) {
            e = null;
            return 0;
        }
    }
}
