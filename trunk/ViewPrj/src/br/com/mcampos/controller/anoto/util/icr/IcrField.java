package br.com.mcampos.controller.anoto.util.icr;

import java.io.Serializable;

public class IcrField implements Serializable, Comparable<IcrField>
{
    private String id;
    private String strType;
    private Object value;
    private Integer score;

    public IcrField()
    {
        super();
    }

    public int compareTo( IcrField o )
    {
        return getId().compareTo( o.getId() );
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setStrType( String strType )
    {
        this.strType = strType;
    }

    public String getStrType()
    {
        return strType;
    }

    public void setValue( Object value )
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }

    public void setScore( Integer score )
    {
        this.score = score;
    }

    public Integer getScore()
    {
        return score;
    }
}
