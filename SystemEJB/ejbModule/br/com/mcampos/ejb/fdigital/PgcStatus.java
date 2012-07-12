package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pgc_status database table.
 * 
 */
@Entity
@Table(name="pgc_status")
public class PgcStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pgs_id_in", unique=true, nullable=false)
	private Integer pgsIdIn;

	@Column(name="pgs_description_ch", nullable=false, length=64)
	private String pgsDescriptionCh;

	//bi-directional many-to-one association to Pgc
	@OneToMany(mappedBy="pgcStatus")
	private List<Pgc> pgcs;

    public PgcStatus() {
    }

	public Integer getPgsIdIn() {
		return this.pgsIdIn;
	}

	public void setPgsIdIn(Integer pgsIdIn) {
		this.pgsIdIn = pgsIdIn;
	}

	public String getPgsDescriptionCh() {
		return this.pgsDescriptionCh;
	}

	public void setPgsDescriptionCh(String pgsDescriptionCh) {
		this.pgsDescriptionCh = pgsDescriptionCh;
	}

	public List<Pgc> getPgcs() {
		return this.pgcs;
	}

	public void setPgcs(List<Pgc> pgcs) {
		this.pgcs = pgcs;
	}
	
}