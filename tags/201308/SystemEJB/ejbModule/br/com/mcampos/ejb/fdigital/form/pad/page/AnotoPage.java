package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.BasicEntityRenderer;
import br.com.mcampos.ejb.fdigital.form.pad.Pad;
import br.com.mcampos.ejb.fdigital.page.AnotoPageField;
import br.com.mcampos.ejb.fdigital.penpage.AnotoPenPage;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the anoto_page database table.
 * 
 */
@Entity
@NamedQueries( {
		@NamedQuery( name = AnotoPage.getAll, query = "select o from AnotoPage o where o.pad.form = ?1" )
} )
@Table( name = "anoto_page" )
public class AnotoPage implements Serializable, BasicEntityRenderer<AnotoPage>, Comparable<AnotoPage>
{
	private static final long serialVersionUID = 1L;
	public static final String getAll = "AnotoPage.getAll";

	@EmbeddedId
	private AnotoPagePK id;

	@Column( name = "apg_description_ch", length = 64 )
	private String description;

	@Column( name = "apg_icr_template_ch", length = 512 )
	private String templateIcr;

	// bi-directional many-to-one association to Pad
	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false, insertable = false, updatable = false )
	} )
	private Pad pad;

	// bi-directional many-to-one association to AnotoPageField
	@OneToMany( mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<AnotoPageField> fields;

	// bi-directional many-to-one association to AnotoPenPage
	@OneToMany( mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<AnotoPenPage> pens;

	public AnotoPage( )
	{
	}

	public AnotoPage( Pad pad )
	{
		setPad( pad );
	}

	public AnotoPagePK getId( )
	{
		if ( this.id == null ) {
			this.id = new AnotoPagePK( );
		}
		return this.id;
	}

	public void setId( AnotoPagePK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String apgDescriptionCh )
	{
		this.description = apgDescriptionCh;
	}

	public String getTemplateIcr( )
	{
		return this.templateIcr;
	}

	public void setTemplateIcr( String apgIcrTemplateCh )
	{
		this.templateIcr = apgIcrTemplateCh;
	}

	public Pad getPad( )
	{
		return this.pad;
	}

	public void setPad( Pad pad )
	{
		this.pad = pad;
		if ( getPad( ) != null ) {
			getId( ).setFormId( pad.getForm( ).getId( ) );
			getId( ).setPadId( pad.getId( ).getId( ) );
			for ( AnotoPageField field : getFields( ) ) {
				field.setPage( this );
			}
		}
		else {
			getId( ).setFormId( null );
			getId( ).setPadId( null );
			for ( AnotoPageField field : getFields( ) ) {
				field.setPage( null );
			}
		}
	}

	public List<AnotoPageField> getFields( )
	{
		if ( this.fields == null ) {
			this.fields = new ArrayList<AnotoPageField>( );
		}
		return this.fields;
	}

	public void setFields( List<AnotoPageField> anotoPageFields )
	{
		this.fields = anotoPageFields;
	}

	public List<AnotoPenPage> getPens( )
	{
		if ( this.pens == null ) {
			this.pens = new ArrayList<AnotoPenPage>( );
		}
		return this.pens;
	}

	public void setPens( List<AnotoPenPage> anotoPenPages )
	{
		this.pens = anotoPenPages;
	}

	public AnotoPageField remove( AnotoPageField item )
	{
		SysUtils.remove( getFields( ), item );
		if ( item != null ) {
			item.setPage( null );
		}
		return item;
	}

	public AnotoPageField add( AnotoPageField item )
	{
		if ( item != null ) {
			int nIndex = getFields( ).indexOf( item );
			if ( nIndex < 0 ) {
				getFields( ).add( item );
				item.setPage( this );
			}
		}
		return item;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( );
		case 1:
			return getDescription( );
		}
		return null;
	}

	@Override
	public int compareTo( AnotoPage object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( object.getId( ) );
		case 1:
			return getDescription( ).compareTo( object.getDescription( ) );
		}
		return 0;
	}

	@Override
	public int compareTo( AnotoPage o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	public String getIp( )
	{
		return getId( ).getId( );
	}

}