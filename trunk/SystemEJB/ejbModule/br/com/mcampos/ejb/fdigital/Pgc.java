package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the pgc database table.
 * 
 */
@Entity
@Table(name="pgc")
public class Pgc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcIdIn;

	@Column(name="pgc_description_ch", length=64)
	private String pgcDescriptionCh;

	@Column(name="pgc_insert_dt")
	private Timestamp pgcInsertDt;

	@Column(name="pgc_pen_id", length=16)
	private String pgcPenId;

	@Column(name="pgc_time_diff_in", precision=16)
	private BigDecimal pgcTimeDiffIn;

	@Column(name="rst_id_in", nullable=false)
	private Integer rstIdIn;

	//bi-directional many-to-one association to PgcStatus
    @ManyToOne
	@JoinColumn(name="pgs_id_in", nullable=false)
	private PgcStatus pgcStatus;

	//bi-directional many-to-one association to PgcAttachment
	@OneToMany(mappedBy="pgc")
	private List<PgcAttachment> pgcAttachments;

	//bi-directional many-to-one association to PgcPage
	@OneToMany(mappedBy="pgc")
	private List<PgcPage> pgcPages;

	//bi-directional many-to-many association to AnotoPenPage
    @ManyToMany
	@JoinTable(
		name="pgc_pen_page"
		, joinColumns={
			@JoinColumn(name="pgc_id_in", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="apg_id_ch", referencedColumnName="apg_id_ch", nullable=false),
			@JoinColumn(name="frm_id_in", referencedColumnName="frm_id_in", nullable=false),
			@JoinColumn(name="pad_id_in", referencedColumnName="pad_id_in", nullable=false),
			@JoinColumn(name="pdp_seq_in", referencedColumnName="pdp_seq_in", nullable=false)
			}
		)
	private List<AnotoPenPage> anotoPenPages;

	//bi-directional many-to-one association to PgcProperty
	@OneToMany(mappedBy="pgc")
	private List<PgcProperty> pgcProperties;

    public Pgc() {
    }

	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}

	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}

	public String getPgcDescriptionCh() {
		return this.pgcDescriptionCh;
	}

	public void setPgcDescriptionCh(String pgcDescriptionCh) {
		this.pgcDescriptionCh = pgcDescriptionCh;
	}

	public Timestamp getPgcInsertDt() {
		return this.pgcInsertDt;
	}

	public void setPgcInsertDt(Timestamp pgcInsertDt) {
		this.pgcInsertDt = pgcInsertDt;
	}

	public String getPgcPenId() {
		return this.pgcPenId;
	}

	public void setPgcPenId(String pgcPenId) {
		this.pgcPenId = pgcPenId;
	}

	public BigDecimal getPgcTimeDiffIn() {
		return this.pgcTimeDiffIn;
	}

	public void setPgcTimeDiffIn(BigDecimal pgcTimeDiffIn) {
		this.pgcTimeDiffIn = pgcTimeDiffIn;
	}

	public Integer getRstIdIn() {
		return this.rstIdIn;
	}

	public void setRstIdIn(Integer rstIdIn) {
		this.rstIdIn = rstIdIn;
	}

	public PgcStatus getPgcStatus() {
		return this.pgcStatus;
	}

	public void setPgcStatus(PgcStatus pgcStatus) {
		this.pgcStatus = pgcStatus;
	}
	
	public List<PgcAttachment> getPgcAttachments() {
		return this.pgcAttachments;
	}

	public void setPgcAttachments(List<PgcAttachment> pgcAttachments) {
		this.pgcAttachments = pgcAttachments;
	}
	
	public List<PgcPage> getPgcPages() {
		return this.pgcPages;
	}

	public void setPgcPages(List<PgcPage> pgcPages) {
		this.pgcPages = pgcPages;
	}
	
	public List<AnotoPenPage> getAnotoPenPages() {
		return this.anotoPenPages;
	}

	public void setAnotoPenPages(List<AnotoPenPage> anotoPenPages) {
		this.anotoPenPages = anotoPenPages;
	}
	
	public List<PgcProperty> getPgcProperties() {
		return this.pgcProperties;
	}

	public void setPgcProperties(List<PgcProperty> pgcProperties) {
		this.pgcProperties = pgcProperties;
	}
	
}