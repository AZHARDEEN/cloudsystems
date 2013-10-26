package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.BasicEntityRenderer;

/**
 * The persistent class for the inep_package database table.
 * 
 */
@Entity
@Table( name = "inep_package", schema = "inep" )
@NamedQueries(
{
		@NamedQuery( name = InepEvent.getAll, query = "select o from InepEvent o where o.company = ?1 " ),
		@NamedQuery( name = InepEvent.getAllAvailable, query = "select o from InepEvent o where ( o.endDate is null or o.endDate >= CURRENT_TIMESTAMP )" ),
		@NamedQuery( name = InepEvent.getAvailable, query = "select o from InepEvent o where o.company = ?1 and "
				+ "( o.endDate is null or o.endDate >= CURRENT_TIMESTAMP )" )
} )
public class InepEvent extends BaseCompanyEntity implements Serializable, Comparable<InepEvent>, BasicEntityRenderer<InepEvent>
{
	private static final long serialVersionUID = 1L;
	public static final String getAll = "InepEvent.getAll";
	public static final String getAllAvailable = "InepEvent.getAllAvailable";
	public static final String getAvailable = "InepEvent.getAvailable";

	@EmbeddedId
	private InepEventPK id;

	@Column( name = "pct_code_ch" )
	private String description;

	@Column( name = "pct_init_dt" )
	@Temporal( TemporalType.DATE )
	private Date initDate;

	@Column( name = "pct_end_dt" )
	@Temporal( TemporalType.DATE )
	private Date endDate;

	public InepEvent( )
	{
	}

	@Override
	public InepEventPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepEventPK( );
		}
		return this.id;
	}

	public void setId( InepEventPK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String pctCodeCh )
	{
		this.description = pctCodeCh;
	}

	@Override
	public int compareTo( InepEvent object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		case 1:
			return this.getDescription( ).compareTo( object.getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( ( (InepEvent) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepEvent o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).toString( );
		case 1:
			return this.getDescription( );
		default:
			return "";
		}
	}

	public Date getInitDate( )
	{
		return this.initDate;
	}

	public void setInitDate( Date initDate )
	{
		this.initDate = initDate;
	}

	public Date getEndDate( )
	{
		return this.endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}
}