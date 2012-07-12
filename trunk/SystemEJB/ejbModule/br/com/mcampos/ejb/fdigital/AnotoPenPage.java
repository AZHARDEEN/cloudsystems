package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the anoto_pen_page database table.
 * 
 */
@Entity
@Table(name="anoto_pen_page")
public class AnotoPenPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPenPagePK id;

	@Column(name="pdp_insert_dt")
	private Timestamp pdpInsertDt;

	@Column(name="pdp_to_dt")
	private Timestamp pdpToDt;

	//bi-directional many-to-one association to AnotoPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="apg_id_ch", referencedColumnName="apg_id_ch", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="frm_id_in", referencedColumnName="frm_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="pad_id_in", referencedColumnName="pad_id_in", nullable=false, insertable=false, updatable=false)
		})
	private AnotoPage anotoPage;

	//bi-directional many-to-one association to AnotoPen
    @ManyToOne
	@JoinColumn(name="pen_id_ch", nullable=false)
	private AnotoPen anotoPen;

	//bi-directional many-to-many association to Pgc
	@ManyToMany(mappedBy="anotoPenPages")
	private List<Pgc> pgcs;

    public AnotoPenPage() {
    }

	public AnotoPenPagePK getId() {
		return this.id;
	}

	public void setId(AnotoPenPagePK id) {
		this.id = id;
	}
	
	public Timestamp getPdpInsertDt() {
		return this.pdpInsertDt;
	}

	public void setPdpInsertDt(Timestamp pdpInsertDt) {
		this.pdpInsertDt = pdpInsertDt;
	}

	public Timestamp getPdpToDt() {
		return this.pdpToDt;
	}

	public void setPdpToDt(Timestamp pdpToDt) {
		this.pdpToDt = pdpToDt;
	}

	public AnotoPage getAnotoPage() {
		return this.anotoPage;
	}

	public void setAnotoPage(AnotoPage anotoPage) {
		this.anotoPage = anotoPage;
	}
	
	public AnotoPen getAnotoPen() {
		return this.anotoPen;
	}

	public void setAnotoPen(AnotoPen anotoPen) {
		this.anotoPen = anotoPen;
	}
	
	public List<Pgc> getPgcs() {
		return this.pgcs;
	}

	public void setPgcs(List<Pgc> pgcs) {
		this.pgcs = pgcs;
	}
	
}