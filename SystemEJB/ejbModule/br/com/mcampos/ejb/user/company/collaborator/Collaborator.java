package br.com.mcampos.ejb.user.company.collaborator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.security.role.Role;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.collaborator.type.CollaboratorType;
import br.com.mcampos.ejb.user.person.Person;


/**
 * The persistent class for the collaborator database table.
 * 
 */
@Entity
@Table(name="collaborator")
@NamedQueries( {
	@NamedQuery( name = Collaborator.getAllCompanyCollaborator,
			query = "select o from Collaborator o where o.company = ?1 and o.toDate is null" ),
			@NamedQuery( name = Collaborator.getAllCompanyCollaboratorType,
			query = "select o from Collaborator o where o.company = ?1 and o.collaboratorType.id = ?2 and o.toDate is null" ),
			@NamedQuery( name = Collaborator.hasCollaborator,
			query = "select o from Collaborator o where o.company = ?1 and o.person = ?2 and o.toDate is null " ),
			@NamedQuery( name = Collaborator.findCompanies,
			query = "from Collaborator o where o.person = ?1 and o.toDate is null" ),
} )
public class Collaborator implements Serializable, Comparable<Collaborator>
{
	private static final long serialVersionUID = 1L;
	public static final String findCompanies = "Collaborator.findCompanies";
	public static final String hasCollaborator = "Collaborator.hasCollaborator";
	public static final String getAllCompanyCollaborator = "Collaborator.getAllCompanyCollaborator";
	public static final String getAllCompanyCollaboratorType = "Collaborator.getAllCompanyCollaboratorType";


	@EmbeddedId
	private CollaboratorPK id;

	@ManyToOne
	@JoinColumn( name = "clt_id_in" )
	private CollaboratorType collaboratorType;

	@Column(name="col_from_dt", nullable=false)
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date fromDate;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "col_id_in", referencedColumnName = "usr_id_in", nullable = false )
	private Person person;

	@Column(name="col_to_dt")
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date toDate;

	@Column(name="cps_id_in", nullable=false)
	private Integer cpsIdIn;

	@ManyToOne
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false )
	private Company company;

	@ManyToMany
	@JoinTable( name = "collaborator_role", joinColumns = {
			@JoinColumn( name = "usr_id_in", nullable = false ),
			@JoinColumn( name = "col_seq_in", nullable = false )
	},
	inverseJoinColumns = { @JoinColumn( name = "rol_id_in", nullable = false ) } )
	private List<Role> roles;


	public Collaborator() {
	}

	public CollaboratorPK getId() {
		if ( this.id == null ) {
			this.id = new CollaboratorPK();
		}
		return this.id;
	}

	public void setId(CollaboratorPK id) {
		this.id = id;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date colFromDt) {
		this.fromDate = colFromDt;
	}


	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date colToDt) {
		this.toDate = colToDt;
	}

	public Integer getCpsIdIn() {
		return this.cpsIdIn;
	}

	public void setCpsIdIn(Integer cpsIdIn) {
		this.cpsIdIn = cpsIdIn;
	}

	public CollaboratorType getCollaboratorType( )
	{
		return this.collaboratorType;
	}

	public void setCollaboratorType( CollaboratorType collaboratorType )
	{
		this.collaboratorType = collaboratorType;
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
		if ( getCompany() != null ) {
			getId( ).setCompanyId( company.getId() );
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
		return getId( ).compareTo( o.getId( ) );
	}

}