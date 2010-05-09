package br.com.mcampos.dto.anoto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


public class PgcPropertyDTO implements Serializable
{
    int id;
    List<String> values = new ArrayList<String>();
    String value;

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
}
