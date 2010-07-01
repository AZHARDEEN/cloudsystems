package br.com.mcampos.dto.anoto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


public class PgcPropertyDTO implements Serializable
{
    public final static Integer gpsPropertyId = 16386;
    private int id;
    private int sequence;
    private List<String> values = new ArrayList<String>();
    private String value;
    private String internalCode;

    public PgcPropertyDTO()
    {
        super();
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void add( String value )
    {
        getValues().add( value );
    }

    public void setValues( List<String> values )
    {
        this.values = values;
    }

    public List<String> getValues()
    {
        return values;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setSequence( int sequence )
    {
        this.sequence = sequence;
    }

    public int getSequence()
    {
        return sequence;
    }

    public void setInternalCode( String internalCode )
    {
        this.internalCode = internalCode;
    }

    public String getInternalCode()
    {
        return internalCode;
    }
}
