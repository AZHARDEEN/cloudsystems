package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.fdigital.pen.AnotoPen;

/**
 * The persistent class for the anoto_pen_page database table.
 * 
 */
@Entity
@Table( name = "anoto_pen_page" )
@NamedQueries( {
		@NamedQuery( name = AnotoPenPage.getFormPen,
				query = "select distinct o.pen from AnotoPenPage o where o.page.pad.form = ?1 and o.toDate is null" )
} )
public class AnotoPenPage implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getFormPen = "AnotoPenPage.getFormPen";

	@EmbeddedId
	private AnotoPenPagePK id;

	@Column( name = "pdp_insert_dt" )
	private Date insertDate;

	@Column( name = "pdp_to_dt" )
	private Date toDate;

	// bi-directional many-to-one association to AnotoPage
	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "apg_id_ch", referencedColumnName = "apg_id_ch", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false, insertable = false, updatable = false )
	} )
	private AnotoPage page;

	// bi-directional many-to-one association to AnotoPen
	@ManyToOne
	@JoinColumn( name = "pen_id_ch", nullable = false )
	private AnotoPen pen;

	public AnotoPenPage( )
	{

	}

	public AnotoPenPagePK getId( )
	{
		return this.id;
	}

	public void setId( AnotoPenPagePK id )
	{
		this.id = id;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date pdpInsertDt )
	{
		this.insertDate = pdpInsertDt;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date pdpToDt )
	{
		this.toDate = pdpToDt;
	}

	public AnotoPage getPage( )
	{
		return this.page;
	}

	public void setPage( AnotoPage anotoPage )
	{
		this.page = anotoPage;
	}

	public AnotoPen getPen( )
	{
		return this.pen;
	}

	public void setPen( AnotoPen anotoPen )
	{
		this.pen = anotoPen;
	}
}