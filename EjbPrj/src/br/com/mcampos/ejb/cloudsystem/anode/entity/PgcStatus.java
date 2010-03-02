package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PgcStatusDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "PgcStatus.findAll", query = "select o from PgcStatus o" ) } )
@Table( name = "\"pgc_status\"" )
public class PgcStatus implements Serializable, EntityCopyInterface<PgcStatusDTO>
{
    public static final Integer statusOk = 1;
    public static final Integer statusNoPen = 2;
    public static final Integer statusNoPenForm = 3;

    @Column( name = "pgs_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "pgs_id_in", nullable = false )
    private Integer id;

    public PgcStatus()
    {
    }

    public PgcStatus( Integer pgs_id_in )
    {
        this.id = pgs_id_in;
    }

    public PgcStatus( Integer pgs_id_in, String pgs_description_ch )
    {
        this.description = pgs_description_ch;
        this.id = pgs_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String pgs_description_ch )
    {
        this.description = pgs_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pgs_id_in )
    {
        this.id = pgs_id_in;
    }

    public PgcStatusDTO toDTO()
    {
        return new PgcStatusDTO( getId(), getDescription() );
    }
}
