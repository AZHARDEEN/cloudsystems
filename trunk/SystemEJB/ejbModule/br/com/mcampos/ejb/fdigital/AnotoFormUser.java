package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;

import java.sql.Timestamp;


/**
 * The persistent class for the anoto_form_user database table.
 * 
 */
@Entity
@Table(name="anoto_form_user")
public class AnotoFormUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoFormUserPK id;

	@Column(name="afu_from_dt", nullable=false)
	private Timestamp afuFromDt;

	@Column(name="afu_to_dt")
	private Timestamp afuToDt;

	@Column(name="usr_id_in", nullable=false)
	private Integer usrIdIn;

	//bi-directional many-to-one association to AnotoForm
    @ManyToOne
	@JoinColumn(name="frm_id_in", nullable=false, insertable=false, updatable=false)
	private AnotoForm anotoForm;

    public AnotoFormUser() {
    }

	public AnotoFormUserPK getId() {
		return this.id;
	}

	public void setId(AnotoFormUserPK id) {
		this.id = id;
	}
	
	public Timestamp getAfuFromDt() {
		return this.afuFromDt;
	}

	public void setAfuFromDt(Timestamp afuFromDt) {
		this.afuFromDt = afuFromDt;
	}

	public Timestamp getAfuToDt() {
		return this.afuToDt;
	}

	public void setAfuToDt(Timestamp afuToDt) {
		this.afuToDt = afuToDt;
	}

	public Integer getUsrIdIn() {
		return this.usrIdIn;
	}

	public void setUsrIdIn(Integer usrIdIn) {
		this.usrIdIn = usrIdIn;
	}

	public AnotoForm getAnotoForm() {
		return this.anotoForm;
	}

	public void setAnotoForm(AnotoForm anotoForm) {
		this.anotoForm = anotoForm;
	}
	
}