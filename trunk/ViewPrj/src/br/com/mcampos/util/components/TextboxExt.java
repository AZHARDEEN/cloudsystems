package br.com.mcampos.util.components;

import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.xml.HTMLs;
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
            invalidate();
        }
    }
    
    public String  getAlt (  )
    {
        return this.alt;
    }


    public String getInnerAttrs ()
    {
        /*
        final String attrs = super.getAttribu
        if ( SysUtils.isEmpty ( getAlt() ) )
            return attrs;
        final StringBuffer sb = new StringBuffer(64).append(attrs);
        HTMLs.appendAttribute(sb, "alt",  getAlt());
        return sb.toString();
*/
        return "";
    }
}




