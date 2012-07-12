package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the anoto_pen_user database table.
 * 
 */
@Entity
@Table(name="anoto_pen_user")
public class AnotoPenUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPenUserPK id;

	@Column(name="apu_from_dt", nullable=false)
	private Timestamp apuFromDt;

	@Column(name="apu_to_dt")
	private Timestamp apuToDt;

	@Column(name="usr_id_in", nullable=false)
	private Integer usrIdIn;

	//bi-directional many-to-one association to AnotoPen
    @ManyToOne
	@JoinColumn(name="pen_id_ch", nullable=false, insertable=false, updatable=false)
	private AnotoPen anotoPen;

    public AnotoPenUser() {
    }

	public AnotoPenUserPK getId() {
		return this.id;
	}

	public void setId(AnotoPenUserPK id) {
		this.id = id;
	}
	
	public Timestamp getApuFromDt() {
		return this.apuFromDt;
	}

	public void setApuFromDt(Timestamp apuFromDt) {
		this.apuFromDt = apuFromDt;
	}

	public Timestamp getApuToDt() {
		return this.apuToDt;
	}

	public void setApuToDt(Timestamp apuToDt) {
		this.apuToDt = apuToDt;
	}

	public Integer getUsrIdIn() {
		return this.usrIdIn;
	}

	public void setUsrIdIn(Integer usrIdIn) {
		this.usrIdIn = usrIdIn;
	}

	public AnotoPen getAnotoPen() {
		return this.anotoPen;
	}

	public void setAnotoPen(AnotoPen anotoPen) {
		this.anotoPen = anotoPen;
	}
	
}