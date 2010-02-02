package br.com.mcampos.util.components;

import br.com.mcampos.sysutils.SysUtils;

import java.io.IOException;

import org.zkoss.xml.HTMLs;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.Textbox;

public class TextboxExt extends Textbox
{
    private String alt = "";
    
    public TextboxExt ( String string )
    {
        super( string );
    }

    public TextboxExt ()
    {
        super();
    }
    
    
    public void setAlt ( String altText )
    {
        if ( !this.alt.equalsIgnoreCase( altText )) {
            this.alt = altText;
            smartUpdate( "alt", getAlt() );
        }
    }
    
    public String  getAlt (  )
    {
        return this.alt;
    }

    @Override
    protected void renderProperties ( ContentRenderer renderer ) throws IOException
    {
        super.renderProperties( renderer );
        if ( SysUtils.isEmpty( getAlt() ) == false )
            render(renderer, "alt", getAlt() );
    }
}


