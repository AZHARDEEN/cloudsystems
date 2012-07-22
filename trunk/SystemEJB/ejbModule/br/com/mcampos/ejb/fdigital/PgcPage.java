package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPage;
import br.com.mcampos.ejb.fdigital.pgc.Pgc;

import java.util.List;


/**
 * The persistent class for the pgc_page database table.
 * 
 */
@Entity
@Table(name="pgc_page")
public class PgcPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPagePK id;

	@Column(name="rst_id_in", nullable=false)
	private Integer rstIdIn;

	//bi-directional many-to-one association to PgcField
	@OneToMany(mappedBy="pgcPage")
	private List<PgcField> pgcFields;

	//bi-directional many-to-one association to AnotoPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="apg_id_ch", referencedColumnName="apg_id_ch", nullable=false),
		@JoinColumn(name="frm_id_in", referencedColumnName="frm_id_in", nullable=false),
		@JoinColumn(name="pad_id_in", referencedColumnName="pad_id_in", nullable=false)
		})
	private AnotoPage anotoPage;

	//bi-directional many-to-one association to Pgc
    @ManyToOne
	@JoinColumn(name="pgc_id_in", nullable=false, insertable=false, updatable=false)
	private Pgc pgc;

	//bi-directional many-to-one association to PgcPageAttachment
	@OneToMany(mappedBy="pgcPage")
	private List<PgcPageAttachment> pgcPageAttachments;

	//bi-directional many-to-one association to PgcProcessedImage
	@OneToMany(mappedBy="pgcPage")
	private List<PgcProcessedImage> pgcProcessedImages;

    public PgcPage() {
    }

	public PgcPagePK getId() {
		return this.id;
	}

	public void setId(PgcPagePK id) {
		this.id = id;
	}
	
	public Integer getRstIdIn() {
		return this.rstIdIn;
	}

	public void setRstIdIn(Integer rstIdIn) {
		this.rstIdIn = rstIdIn;
	}

	public List<PgcField> getPgcFields() {
		return this.pgcFields;
	}

	public void setPgcFields(List<PgcField> pgcFields) {
		this.pgcFields = pgcFields;
	}
	
	public AnotoPage getAnotoPage() {
		return this.anotoPage;
	}

	public void setAnotoPage(AnotoPage anotoPage) {
		this.anotoPage = anotoPage;
	}
	
	public Pgc getPgc() {
		return this.pgc;
	}

	public void setPgc(Pgc pgc) {
		this.pgc = pgc;
	}
	
	public List<PgcPageAttachment> getPgcPageAttachments() {
		return this.pgcPageAttachments;
	}

	public void setPgcPageAttachments(List<PgcPageAttachment> pgcPageAttachments) {
		this.pgcPageAttachments = pgcPageAttachments;
	}
	
	public List<PgcProcessedImage> getPgcProcessedImages() {
		return this.pgcProcessedImages;
	}

	public void setPgcProcessedImages(List<PgcProcessedImage> pgcProcessedImages) {
		this.pgcProcessedImages = pgcProcessedImages;
	}
	
}