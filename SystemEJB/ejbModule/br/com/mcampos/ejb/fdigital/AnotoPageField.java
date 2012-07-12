package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the anoto_page_field database table.
 * 
 */
@Entity
@Table(name="anoto_page_field")
public class AnotoPageField implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPageFieldPK id;

	@Column(name="aft_icr_bt", nullable=false)
	private Boolean aftIcrBt;

	@Column(name="alf_alias_ch", length=256)
	private String alfAliasCh;

	@Column(name="alf_height_in")
	private Integer alfHeightIn;

	@Column(name="alf_search_bt")
	private Boolean alfSearchBt;

	@Column(name="alf_top_in")
	private Integer alfTopIn;

	@Column(name="alf_width_in")
	private Integer alfWidthIn;

	@Column(name="apf_export_bt")
	private Boolean apfExportBt;

	@Column(name="apf_left_in")
	private Integer apfLeftIn;

	@Column(name="apf_pk_bt")
	private Boolean apfPkBt;

	@Column(name="apf_sequence_in")
	private Integer apfSequenceIn;

	@Column(name="flt_id_in", nullable=false)
	private Integer fltIdIn;

	//bi-directional many-to-one association to AnotoPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="apg_id_ch", referencedColumnName="apg_id_ch", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="frm_id_in", referencedColumnName="frm_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="pad_id_in", referencedColumnName="pad_id_in", nullable=false, insertable=false, updatable=false)
		})
	private AnotoPage anotoPage;

    public AnotoPageField() {
    }

	public AnotoPageFieldPK getId() {
		return this.id;
	}

	public void setId(AnotoPageFieldPK id) {
		this.id = id;
	}
	
	public Boolean getAftIcrBt() {
		return this.aftIcrBt;
	}

	public void setAftIcrBt(Boolean aftIcrBt) {
		this.aftIcrBt = aftIcrBt;
	}

	public String getAlfAliasCh() {
		return this.alfAliasCh;
	}

	public void setAlfAliasCh(String alfAliasCh) {
		this.alfAliasCh = alfAliasCh;
	}

	public Integer getAlfHeightIn() {
		return this.alfHeightIn;
	}

	public void setAlfHeightIn(Integer alfHeightIn) {
		this.alfHeightIn = alfHeightIn;
	}

	public Boolean getAlfSearchBt() {
		return this.alfSearchBt;
	}

	public void setAlfSearchBt(Boolean alfSearchBt) {
		this.alfSearchBt = alfSearchBt;
	}

	public Integer getAlfTopIn() {
		return this.alfTopIn;
	}

	public void setAlfTopIn(Integer alfTopIn) {
		this.alfTopIn = alfTopIn;
	}

	public Integer getAlfWidthIn() {
		return this.alfWidthIn;
	}

	public void setAlfWidthIn(Integer alfWidthIn) {
		this.alfWidthIn = alfWidthIn;
	}

	public Boolean getApfExportBt() {
		return this.apfExportBt;
	}

	public void setApfExportBt(Boolean apfExportBt) {
		this.apfExportBt = apfExportBt;
	}

	public Integer getApfLeftIn() {
		return this.apfLeftIn;
	}

	public void setApfLeftIn(Integer apfLeftIn) {
		this.apfLeftIn = apfLeftIn;
	}

	public Boolean getApfPkBt() {
		return this.apfPkBt;
	}

	public void setApfPkBt(Boolean apfPkBt) {
		this.apfPkBt = apfPkBt;
	}

	public Integer getApfSequenceIn() {
		return this.apfSequenceIn;
	}

	public void setApfSequenceIn(Integer apfSequenceIn) {
		this.apfSequenceIn = apfSequenceIn;
	}

	public Integer getFltIdIn() {
		return this.fltIdIn;
	}

	public void setFltIdIn(Integer fltIdIn) {
		this.fltIdIn = fltIdIn;
	}

	public AnotoPage getAnotoPage() {
		return this.anotoPage;
	}

	public void setAnotoPage(AnotoPage anotoPage) {
		this.anotoPage = anotoPage;
	}
	
}