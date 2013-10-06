package br.com.mcampos.jpa.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the collaborator database table.
 * 
 */
@Entity
@Table( name = "collaborator", schema = "public" )
@NamedQueries( {
		@NamedQuery( name = Collaborator.getAllCompanyCollaborator,
				query = "select o from Collaborator o where o.company = ?1 and o.toDate is null" ),
		@NamedQuery( name = Collaborator.getAllCompanyCollaboratorType,
				query = "select o from Collaborator o where o.company = ?1 and o.collaboratorType.id = ?2 and o.toDate is null" ),
		@NamedQuery( name = Collaborator.hasCollaborator,
				query = "select o from Collaborator o where o.company = ?1 and o.person = ?2 and o.toDate is null " ),
		@NamedQuery( name = Collaborator.findCompanies,
				query = "select o from Collaborator o where o.person = ?1 and o.toDate is null" ),
		@NamedQuery( name = Collaborator.maxSequence,
				query = "select max ( o.id.sequence ) + 1 from Collaborator o where o.company = ?1 " ),
} )
public class Collaborator implements Serializable, Comparable<Collaborator>
{
	private static final long serialVersionUID = 1L;
	public static final String findCompanies = "Collaborator.findCompanies";
	public static final String hasCollaborator = "Collaborator.hasCollaborator";
	public static final String getAllCompanyCollaborator = "Collaborator.getAllCompanyCollaborator";
	public static final String getAllCompanyCollaboratorType = "Collaborator.getAllCompanyCollaboratorType";
	public static final String maxSequence = "Collaborator.getMaxSequence";

	@EmbeddedId
	private CollaboratorPK id;

	@Column( name = "clt_id_in", nullable = false, updatable = true, insertable = true, columnDefinition = "Integer NOT NULL" )
	private Integer typeId;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in",
					nullable = false, updatable = false, insertable = false, columnDefinition = "Integer"
			),
			@JoinColumn(
					name = "clt_id_in", referencedColumnName = "clt_id_in",
					nullable = false, updatable = false, insertable = false, columnDefinition = "Integer"
			)
	} )
	private CollaboratorType collaboratorType;

	@Column( name = "col_from_dt", nullable = false )
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date fromDate;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "col_id_in", referencedColumnName = "usr_id_in", nullable = false )
	private Person person;

	@Column( name = "col_to_dt" )
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date toDate;

	@Column( name = "cps_id_in", nullable = false )
	private Integer cpsIdIn;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false )
	private Company company;

	@ManyToMany
	@JoinTable( name = "collaborator_role", schema = "public", joinColumns = {
			@JoinColumn( name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in" ),
			@JoinColumn( name = "col_seq_in", nullable = false, referencedColumnName = "col_seq_in" )
	},
			inverseJoinColumns = { @JoinColumn( name = "rol_id_in", nullable = false ) } )
	private List<Role> roles;

	public Collaborator( )
	{
	}

	public CollaboratorPK getId( )
	{
		if ( this.id == null ) {
			this.id = new CollaboratorPK( );
		}
		return this.id;
	}

	public void setId( CollaboratorPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public void setFromDate( Date colFromDt )
	{
		this.fromDate = colFromDt;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date colToDt )
	{
		this.toDate = colToDt;
	}

	public Integer getCpsIdIn( )
	{
		return this.cpsIdIn;
	}

	public void setCpsIdIn( Integer cpsIdIn )
	{
		this.cpsIdIn = cpsIdIn;
	}

	public CollaboratorType getCollaboratorType( )
	{
		return this.collaboratorType;
	}

	public void setCollaboratorType( CollaboratorType type )
	{
		this.collaboratorType = type;
		this.setTypeId( type != null ? type.getId( ).getId( ) : null );
	}

	public Person getPerson( )
	{
		return this.person;
	}

	public void setPerson( Person person )
	{
		this.person = person;
	}

	public Company getCompany( )
	{
		return this.company;
	}

	public void setCompany( Company company )
	{
		this.company = company;
		if ( this.getCompany( ) != null ) {
			this.getId( ).setCompanyId( company.getId( ) );
		}
	}

	public List<Role> getRoles( )
	{
		return this.roles;
	}

	public void setRoles( List<Role> roles )
	{
		this.roles = roles;
	}

	@Override
	public int compareTo( Collaborator o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	public Role add( Role item )
	{
		if ( item != null ) {
			int nIndex = this.getRoles( ).indexOf( item );
			if ( nIndex < 0 ) {
				this.getRoles( ).add( item );
			}
		}
		return item;
	}

	public Role remove( Role item )
	{
		SysUtils.remove( this.getRoles( ), item );
		return item;
	}

	public Integer getTypeId( )
	{
		return this.typeId;
	}

	public void setTypeId( Integer typeId )
	{
		this.typeId = typeId;
	}

}