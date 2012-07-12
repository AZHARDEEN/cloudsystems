package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the anoto_form database table.
 * 
 */
@Entity
@Table(name="anoto_form")
public class AnotoForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="frm_id_in", unique=true, nullable=false)
	private Integer id;

	@Column(name="frm_concat_pgc_bt")
	private Boolean frmConcatPgcBt;

	@Column(name="frm_description_ch", length=64)
	private String descriptions;

	@Column(name="frm_icr_image_bt")
	private Boolean frmIcrImageBt;

	@Column(name="frm_image_filepath_ch", length=1024)
	private String frmImageFilepathCh;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="frm_insert_dt", nullable=false)
	private Date insertDate;

	@Column(name="frm_ip_ch", nullable=false, length=16)
	private String frmIpCh;

	//bi-directional many-to-one association to AnotoFormUser
	@OneToMany(mappedBy="anotoForm")
	private List<AnotoFormUser> anotoFormUsers;

	//bi-directional many-to-one association to Pad
	@OneToMany(mappedBy="anotoForm")
	private List<Pad> pads;

    public AnotoForm() {
    }

	public Integer getFrmIdIn() {
		return this.id;
	}

	public void setFrmIdIn(Integer frmIdIn) {
		this.id = frmIdIn;
	}

	public Boolean getFrmConcatPgcBt() {
		return this.frmConcatPgcBt;
	}

	public void setFrmConcatPgcBt(Boolean frmConcatPgcBt) {
		this.frmConcatPgcBt = frmConcatPgcBt;
	}

	public String getFrmDescriptionCh() {
		return this.descriptions;
	}

	public void setFrmDescriptionCh(String frmDescriptionCh) {
		this.descriptions = frmDescriptionCh;
	}

	public Boolean getFrmIcrImageBt() {
		return this.frmIcrImageBt;
	}

	public void setFrmIcrImageBt(Boolean frmIcrImageBt) {
		this.frmIcrImageBt = frmIcrImageBt;
	}

	public String getFrmImageFilepathCh() {
		return this.frmImageFilepathCh;
	}

	public void setFrmImageFilepathCh(String frmImageFilepathCh) {
		this.frmImageFilepathCh = frmImageFilepathCh;
	}

	public Date getFrmInsertDt() {
		return this.insertDate;
	}

	public void setFrmInsertDt(Date frmInsertDt) {
		this.insertDate = frmInsertDt;
	}

	public String getFrmIpCh() {
		return this.frmIpCh;
	}

	public void setFrmIpCh(String frmIpCh) {
		this.frmIpCh = frmIpCh;
	}

	public List<AnotoFormUser> getAnotoFormUsers() {
		return this.anotoFormUsers;
	}

	public void setAnotoFormUsers(List<AnotoFormUser> anotoFormUsers) {
		this.anotoFormUsers = anotoFormUsers;
	}
	
	public List<Pad> getPads() {
		return this.pads;
	}

	public void setPads(List<Pad> pads) {
		this.pads = pads;
	}
	
}