package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the anoto_pen database table.
 * 
 */
@Entity
@Table(name="anoto_pen")
public class AnotoPen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pen_id_ch", unique=true, nullable=false, length=16)
	private String penIdCh;

	@Column(name="pen_description_ch", nullable=false, length=64)
	private String penDescriptionCh;

    @Temporal( TemporalType.DATE)
	@Column(name="pen_insert_dt")
	private Date penInsertDt;

	@Column(name="pen_pin_ch")
	private Integer penPinCh;

	@Column(name="pen_serial_ch", length=32)
	private String penSerialCh;

	//bi-directional many-to-one association to AnotoPenPage
	@OneToMany(mappedBy="anotoPen")
	private List<AnotoPenPage> anotoPenPages;

	//bi-directional many-to-one association to AnotoPenUser
	@OneToMany(mappedBy="anotoPen")
	private List<AnotoPenUser> anotoPenUsers;

    public AnotoPen() {
    }

	public String getPenIdCh() {
		return this.penIdCh;
	}

	public void setPenIdCh(String penIdCh) {
		this.penIdCh = penIdCh;
	}

	public String getPenDescriptionCh() {
		return this.penDescriptionCh;
	}

	public void setPenDescriptionCh(String penDescriptionCh) {
		this.penDescriptionCh = penDescriptionCh;
	}

	public Date getPenInsertDt() {
		return this.penInsertDt;
	}

	public void setPenInsertDt(Date penInsertDt) {
		this.penInsertDt = penInsertDt;
	}

	public Integer getPenPinCh() {
		return this.penPinCh;
	}

	public void setPenPinCh(Integer penPinCh) {
		this.penPinCh = penPinCh;
	}

	public String getPenSerialCh() {
		return this.penSerialCh;
	}

	public void setPenSerialCh(String penSerialCh) {
		this.penSerialCh = penSerialCh;
	}

	public List<AnotoPenPage> getAnotoPenPages() {
		return this.anotoPenPages;
	}

	public void setAnotoPenPages(List<AnotoPenPage> anotoPenPages) {
		this.anotoPenPages = anotoPenPages;
	}
	
	public List<AnotoPenUser> getAnotoPenUsers() {
		return this.anotoPenUsers;
	}

	public void setAnotoPenUsers(List<AnotoPenUser> anotoPenUsers) {
		this.anotoPenUsers = anotoPenUsers;
	}
	
}