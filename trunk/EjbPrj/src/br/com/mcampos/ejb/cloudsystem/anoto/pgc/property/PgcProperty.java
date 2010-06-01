package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property;

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
                              query = "select o from PgcProperty o where o.pgc_id_in = ?1 and o.pgp_id_in <> 16386 order by o.pgp_seq_in" ),
                 @NamedQuery( name = PgcProperty.deleteFromPGC, query = "delete from PgcProperty o where o.pgc_id_in = ?1 " ),
                 @NamedQuery( name = PgcProperty.getAllGPS,
                              query = "select o from PgcProperty o where o.pgc_id_in = ?1 and o.pgp_id_in = 16386 order by o.pgp_seq_in" ) } )
@Table( name = "pgc_property" )
@IdClass( PgcPropertyPK.class )
public class PgcProperty implements Serializable
{
    public static final String getAll = "PgcProperty.findAll";
    public static final String getAllByPgc = "PgcProperty.findAllByPgc";
    public static final String getAllGPS = "PgcProperty.findAllGPS";
    public static final String deleteFromPGC = "PgcProperty.deleteFromPGC";

    @Id
    @Column( name = "pgc_id_in", nullable = false )
    private Integer pgc_id_in;
    @Id
    @Column( name = "pgp_id_in", nullable = false )
    private Integer pgp_id_in;
    @Id
    @Column( name = "pgp_seq_in", nullable = false )
    private Integer pgp_seq_in;
    @Column( name = "ppg_value_ch", nullable = false )
    private String ppg_value_ch;

    public PgcProperty()
    {
    }

    public PgcProperty( Integer pgc_id_in, Integer pgp_id_in, Integer pgp_seq_in, String ppg_value_ch )
    {
        this.pgc_id_in = pgc_id_in;
        this.pgp_id_in = pgp_id_in;
        this.pgp_seq_in = pgp_seq_in;
        this.ppg_value_ch = ppg_value_ch;
    }

    public Integer getPgc_id_in()
    {
        return pgc_id_in;
    }

    public void setPgc_id_in( Integer pgc_id_in )
    {
        this.pgc_id_in = pgc_id_in;
    }

    public Integer getPgp_id_in()
    {
        return pgp_id_in;
    }

    public void setPgp_id_in( Integer pgp_id_in )
    {
        this.pgp_id_in = pgp_id_in;
    }

    public Integer getPgp_seq_in()
    {
        return pgp_seq_in;
    }

    public void setPgp_seq_in( Integer pgp_seq_in )
    {
        this.pgp_seq_in = pgp_seq_in;
    }

    public String getPpg_value_ch()
    {
        return ppg_value_ch;
    }

    public void setPpg_value_ch( String ppg_value_ch )
    {
        this.ppg_value_ch = ppg_value_ch;
    }
}
