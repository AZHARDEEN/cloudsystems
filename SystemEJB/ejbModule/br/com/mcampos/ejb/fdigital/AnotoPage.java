package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the anoto_page database table.
 * 
 */
@Entity
@Table(name="anoto_page")
public class AnotoPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPagePK id;

	@Column(name="apg_description_ch", length=64)
	private String apgDescriptionCh;

	@Column(name="apg_icr_template_ch", length=512)
	private String apgIcrTemplateCh;

	//bi-directional many-to-one association to Pad
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="frm_id_in", referencedColumnName="frm_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="pad_id_in", referencedColumnName="pad_id_in", nullable=false, insertable=false, updatable=false)
		})
	private Pad pad;

	//bi-directional many-to-one association to AnotoPageField
	@OneToMany(mappedBy="anotoPage")
	private List<AnotoPageField> anotoPageFields;

	//bi-directional many-to-one association to AnotoPenPage
	@OneToMany(mappedBy="anotoPage")
	private List<AnotoPenPage> anotoPenPages;

	//bi-directional many-to-one association to PgcPage
	@OneToMany(mappedBy="anotoPage")
	private List<PgcPage> pgcPages;

    public AnotoPage() {
    }

	public AnotoPagePK getId() {
		return this.id;
	}

	public void setId(AnotoPagePK id) {
		this.id = id;
	}
	
	public String getApgDescriptionCh() {
		return this.apgDescriptionCh;
	}

	public void setApgDescriptionCh(String apgDescriptionCh) {
		this.apgDescriptionCh = apgDescriptionCh;
	}

	public String getApgIcrTemplateCh() {
		return this.apgIcrTemplateCh;
	}

	public void setApgIcrTemplateCh(String apgIcrTemplateCh) {
		this.apgIcrTemplateCh = apgIcrTemplateCh;
	}

	public Pad getPad() {
		return this.pad;
	}

	public void setPad(Pad pad) {
		this.pad = pad;
	}
	
	public List<AnotoPageField> getAnotoPageFields() {
		return this.anotoPageFields;
	}

	public void setAnotoPageFields(List<AnotoPageField> anotoPageFields) {
		this.anotoPageFields = anotoPageFields;
	}
	
	public List<AnotoPenPage> getAnotoPenPages() {
		return this.anotoPenPages;
	}

	public void setAnotoPenPages(List<AnotoPenPage> anotoPenPages) {
		this.anotoPenPages = anotoPenPages;
	}
	
	public List<PgcPage> getPgcPages() {
		return this.pgcPages;
	}

	public void setPgcPages(List<PgcPage> pgcPages) {
		this.pgcPages = pgcPages;
	}
	
}