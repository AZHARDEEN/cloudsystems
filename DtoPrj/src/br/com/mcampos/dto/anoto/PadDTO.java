package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

import java.security.InvalidParameterException;


public class PadDTO implements Comparable<PadDTO>, Serializable
{
    protected Integer formId;
    protected Integer id;


    public PadDTO()
    {
        super();
    }

    public PadDTO( FormDTO form, MediaDTO pad )
    {
        if ( form == null || pad == null || form.getId() == 0 || pad.getId() == 0 )
            throw new InvalidParameterException( "Os dtos envolvidos não podem ser vazios ou nulos" );
        setFormId( form.getId() );
        setId( pad.getId() );
    }

    public int compareTo( PadDTO o )
    {
        if ( o == null )
            return -1;
        int nRet = getFormId().compareTo( o.getFormId() );
        if ( nRet == 0 )
            nRet = getId().compareTo( o.getId() );
        return nRet;
    }

    public void setFormId( Integer formId )
    {
        this.formId = formId;
    }

    public Integer getFormId()
    {
        if ( formId == null )
            formId = new Integer( 0 );
        return formId;
    }

    public void setId( Integer padId )
    {
        this.id = padId;
    }

    public Integer getId()
    {
        if ( id == null )
            id = new Integer( 0 );
        return id;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        if ( obj instanceof PadDTO ) {
            PadDTO dto = ( PadDTO )obj;
            return getFormId().equals( dto.getFormId() ) && getId().equals( dto.getId() );
        }
        else
            return false;
    }
}
