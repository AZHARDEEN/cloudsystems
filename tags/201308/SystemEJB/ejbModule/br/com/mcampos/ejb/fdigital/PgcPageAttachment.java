package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pgc_page_attachment database table.
 * 
 */
@Entity
@Table(name="pgc_page_attachment")
public class PgcPageAttachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPageAttachmentPK id;

	@Column(name="med_id_in")
	private Integer medIdIn;

	@Column(name="pat_barcode_type_in")
	private Integer patBarcodeTypeIn;

	@Column(name="pat_value_ch", length=128)
	private String patValueCh;

	//bi-directional many-to-one association to PgcAttachmentType
    @ManyToOne
	@JoinColumn(name="pat_id_in", nullable=false)
	private PgcAttachmentType pgcAttachmentType;

	//bi-directional many-to-one association to PgcPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="pgc_id_in", referencedColumnName="pgc_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_book_id", referencedColumnName="ppg_book_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_page_id", referencedColumnName="ppg_page_id", nullable=false, insertable=false, updatable=false)
		})
	private PgcPage pgcPage;

    public PgcPageAttachment() {
    }

	public PgcPageAttachmentPK getId() {
		return this.id;
	}

	public void setId(PgcPageAttachmentPK id) {
		this.id = id;
	}
	
	public Integer getMedIdIn() {
		return this.medIdIn;
	}

	public void setMedIdIn(Integer medIdIn) {
		this.medIdIn = medIdIn;
	}

	public Integer getPatBarcodeTypeIn() {
		return this.patBarcodeTypeIn;
	}

	public void setPatBarcodeTypeIn(Integer patBarcodeTypeIn) {
		this.patBarcodeTypeIn = patBarcodeTypeIn;
	}

	public String getPatValueCh() {
		return this.patValueCh;
	}

	public void setPatValueCh(String patValueCh) {
		this.patValueCh = patValueCh;
	}

	public PgcAttachmentType getPgcAttachmentType() {
		return this.pgcAttachmentType;
	}

	public void setPgcAttachmentType(PgcAttachmentType pgcAttachmentType) {
		this.pgcAttachmentType = pgcAttachmentType;
	}
	
	public PgcPage getPgcPage() {
		return this.pgcPage;
	}

	public void setPgcPage(PgcPage pgcPage) {
		this.pgcPage = pgcPage;
	}
	
}