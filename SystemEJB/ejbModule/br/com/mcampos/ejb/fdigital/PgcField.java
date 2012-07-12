package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the pgc_field database table.
 * 
 */
@Entity
@Table(name="pgc_field")
public class PgcField implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcFieldPK id;

	@Column(name="flt_id_in")
	private Integer fltIdIn;

	@Column(name="med_id_in")
	private Integer medIdIn;

	@Column(name="pfl_end_time_in", precision=16)
	private BigDecimal pflEndTimeIn;

	@Column(name="pfl_has_penstrokes_bt")
	private Boolean pflHasPenstrokesBt;

	@Column(name="pfl_icr_tx", length=2147483647)
	private String pflIcrTx;

	@Column(name="pfl_revised_tx", length=2147483647)
	private String pflRevisedTx;

	@Column(name="pfl_sequence_in")
	private Integer pflSequenceIn;

	@Column(name="pfl_start_time_in", precision=16)
	private BigDecimal pflStartTimeIn;

	//bi-directional many-to-one association to PgcPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="pgc_id_in", referencedColumnName="pgc_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_book_id", referencedColumnName="ppg_book_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_page_id", referencedColumnName="ppg_page_id", nullable=false, insertable=false, updatable=false)
		})
	private PgcPage pgcPage;

    public PgcField() {
    }

	public PgcFieldPK getId() {
		return this.id;
	}

	public void setId(PgcFieldPK id) {
		this.id = id;
	}
	
	public Integer getFltIdIn() {
		return this.fltIdIn;
	}

	public void setFltIdIn(Integer fltIdIn) {
		this.fltIdIn = fltIdIn;
	}

	public Integer getMedIdIn() {
		return this.medIdIn;
	}

	public void setMedIdIn(Integer medIdIn) {
		this.medIdIn = medIdIn;
	}

	public BigDecimal getPflEndTimeIn() {
		return this.pflEndTimeIn;
	}

	public void setPflEndTimeIn(BigDecimal pflEndTimeIn) {
		this.pflEndTimeIn = pflEndTimeIn;
	}

	public Boolean getPflHasPenstrokesBt() {
		return this.pflHasPenstrokesBt;
	}

	public void setPflHasPenstrokesBt(Boolean pflHasPenstrokesBt) {
		this.pflHasPenstrokesBt = pflHasPenstrokesBt;
	}

	public String getPflIcrTx() {
		return this.pflIcrTx;
	}

	public void setPflIcrTx(String pflIcrTx) {
		this.pflIcrTx = pflIcrTx;
	}

	public String getPflRevisedTx() {
		return this.pflRevisedTx;
	}

	public void setPflRevisedTx(String pflRevisedTx) {
		this.pflRevisedTx = pflRevisedTx;
	}

	public Integer getPflSequenceIn() {
		return this.pflSequenceIn;
	}

	public void setPflSequenceIn(Integer pflSequenceIn) {
		this.pflSequenceIn = pflSequenceIn;
	}

	public BigDecimal getPflStartTimeIn() {
		return this.pflStartTimeIn;
	}

	public void setPflStartTimeIn(BigDecimal pflStartTimeIn) {
		this.pflStartTimeIn = pflStartTimeIn;
	}

	public PgcPage getPgcPage() {
		return this.pgcPage;
	}

	public void setPgcPage(PgcPage pgcPage) {
		this.pgcPage = pgcPage;
	}
	
}