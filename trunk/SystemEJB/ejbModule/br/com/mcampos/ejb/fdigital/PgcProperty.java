package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

import br.com.mcampos.ejb.fdigital.pgc.Pgc;


/**
 * The persistent class for the pgc_property database table.
 * 
 */
@Entity
@Table(name="pgc_property")
public class PgcProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPropertyPK id;

	@Column(name="ppg_value_ch", nullable=false, length=128)
	private String ppgValueCh;

	//bi-directional many-to-one association to Pgc
    @ManyToOne
	@JoinColumn(name="pgc_id_in", nullable=false, insertable=false, updatable=false)
	private Pgc pgc;

    public PgcProperty() {
    }

	public PgcPropertyPK getId() {
		return this.id;
	}

	public void setId(PgcPropertyPK id) {
		this.id = id;
	}
	
	public String getPpgValueCh() {
		return this.ppgValueCh;
	}

	public void setPpgValueCh(String ppgValueCh) {
		this.ppgValueCh = ppgValueCh;
	}

	public Pgc getPgc() {
		return this.pgc;
	}

	public void setPgc(Pgc pgc) {
		this.pgc = pgc;
	}
	
}