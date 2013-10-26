package br.com.mcampos.ejb.cloudsystem.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "SystemParameters.findAll", query = "select o from SystemParameters o" ) } )
@Table( name = "\"system_parameters\"" )
public class SystemParameters implements Serializable
{

    @Column( name = "spr_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "spr_id_ch", nullable = false )
    private String id;
    @Column( name = "spr_value_tx" )
    private String value;

    public static final String passwordValidDays = "PasswordValidDays";
    public static final String maxLoginTryCount = "MaxLoginTryCount";

    public SystemParameters()
    {
    }

    public SystemParameters( String id, String description, String value )
    {
        setDescription( description );
        setId( id );
        setValue( value );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String spr_description_ch )
    {
        this.description = spr_description_ch;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String spr_id_ch )
    {
        this.id = spr_id_ch;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String spr_value_tx )
    {
        this.value = spr_value_tx;
    }

}
