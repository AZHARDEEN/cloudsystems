package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

public class FormMediaDTO implements Comparable<FormMediaDTO>, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3684852697396820727L;
	FormDTO form;
    MediaDTO media;


    public FormMediaDTO()
    {
        super();
    }

    public FormMediaDTO( FormDTO form, MediaDTO media )
    {
        super();
        setForm( form );
        setMedia( media );
    }


    public int compareTo( FormMediaDTO o )
    {
        int nRet = getForm().compareTo( o.getForm() );
        if ( nRet == 0 )
            nRet = getMedia().compareTo( o.getMedia() );
        return nRet;
    }

    public void setForm( FormDTO form )
    {
        this.form = form;
    }

    public FormDTO getForm()
    {
        return form;
    }

    public void setMedia( MediaDTO media )
    {
        this.media = media;
    }

    public MediaDTO getMedia()
    {
        return media;
    }
}
