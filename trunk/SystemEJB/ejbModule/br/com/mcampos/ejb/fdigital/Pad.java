package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pad database table.
 * 
 */
@Entity
@Table(name="pad")
public class Pad implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PadPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="pad_insert_dt")
	private Date padInsertDt;

	@Column(name="pad_unique_bt")
	private Boolean padUniqueBt;

	//bi-directional many-to-one association to AnotoPage
	@OneToMany(mappedBy="pad")
	private List<AnotoPage> anotoPages;

	//bi-directional many-to-one association to AnotoForm
    @ManyToOne
	@JoinColumn(name="frm_id_in", nullable=false, insertable=false, updatable=false)
	private AnotoForm anotoForm;

    public Pad() {
    }

	public PadPK getId() {
		return this.id;
	}

	public void setId(PadPK id) {
		this.id = id;
	}
	
	public Date getPadInsertDt() {
		return this.padInsertDt;
	}

	public void setPadInsertDt(Date padInsertDt) {
		this.padInsertDt = padInsertDt;
	}

	public Boolean getPadUniqueBt() {
		return this.padUniqueBt;
	}

	public void setPadUniqueBt(Boolean padUniqueBt) {
		this.padUniqueBt = padUniqueBt;
	}

	public List<AnotoPage> getAnotoPages() {
		return this.anotoPages;
	}

	public void setAnotoPages(List<AnotoPage> anotoPages) {
		this.anotoPages = anotoPages;
	}
	
	public AnotoForm getAnotoForm() {
		return this.anotoForm;
	}

	public void setAnotoForm(AnotoForm anotoForm) {
		this.anotoForm = anotoForm;
	}
	
}