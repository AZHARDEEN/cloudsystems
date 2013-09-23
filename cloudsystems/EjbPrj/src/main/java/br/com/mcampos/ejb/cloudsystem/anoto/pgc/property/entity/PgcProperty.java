package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = PgcProperty.getAll, query = "select o from PgcProperty o" ),
                 @NamedQuery( name = PgcProperty.getAllByPgc,
                              query = "select o from PgcProperty o where o.pgcId = ?1 and o.id <> 16386 order by o.sequence" ),
                 @NamedQuery( name = PgcProperty.deleteFromPGC, query = "delete from PgcProperty o where o.pgcId = ?1 " ),
                 @NamedQuery( name = PgcProperty.getAllGPS,
                              query = "select o from PgcProperty o where o.pgcId = ?1 and o.id = 16386 order by o.sequence" ),
                 @NamedQuery( name = PgcProperty.getProperty,
                              query = "select o from PgcProperty o where o.pgcId = ?1 and o.id = ?2 order by o.sequence" ) } )
@Table( name = "pgc_property" )
@IdClass( PgcPropertyPK.class )
public class PgcProperty implements Serializable
{
    public static final String getAll = "PgcProperty.findAll";
    public static final String getAllByPgc = "PgcProperty.findAllByPgc";
    public static final String getAllGPS = "PgcProperty.findAllGPS";
    public static final String deleteFromPGC = "PgcProperty.deleteFromPGC";
    public static final String getProperty = "PgcProperty.getProperty";

    public static final Integer cellNumber = 8204;

    @Id
    @Column( name = "pgc_id_in", nullable = false )
    private Integer pgcId;

    @Id
    @Column( name = "pgp_id_in", nullable = false )
    private Integer id;

    @Id
    @Column( name = "pgp_seq_in", nullable = false )
    private Integer sequence;

    @Column( name = "ppg_value_ch", nullable = false )
    private String value;

    public PgcProperty()
    {
    }

    public PgcProperty( Integer pgc_id_in, Integer pgp_id_in, Integer pgp_seq_in, String ppg_value_ch )
    {
        this.pgcId = pgc_id_in;
        this.id = pgp_id_in;
        this.sequence = pgp_seq_in;
        this.value = ppg_value_ch;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pgp_id_in )
    {
        this.id = pgp_id_in;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer pgp_seq_in )
    {
        this.sequence = pgp_seq_in;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String ppg_value_ch )
    {
        this.value = ppg_value_ch;
    }
}
