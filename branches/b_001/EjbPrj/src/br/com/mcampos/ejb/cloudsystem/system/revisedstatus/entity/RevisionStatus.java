package br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "RevisionStatus.findAll", query = "select o from RevisionStatus o" ) } )
@Table( name = "revision_status" )
public class RevisionStatus implements Serializable
{
    public static final Integer statusNotVerified = 1;
    public static final Integer statusVerifying = 2;
    public static final Integer statusVerified = 3;

    @Column( name = "rst_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "rst_id_in", nullable = false )
    private Integer id;

    public RevisionStatus()
    {
    }

    public RevisionStatus( Integer rst_id_in, String rst_description_ch )
    {
        setDescription( rst_description_ch );
        setId( rst_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String rst_description_ch )
    {
        this.description = rst_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer rst_id_in )
    {
        this.id = rst_id_in;
    }
}
