package br.com.mcampos.ejb.cloudsystem.user.collaborator.role;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.Collaborator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = CollaboratorRole.getAll,
							  query = "select o from CollaboratorRole o where o.collaborator = ?1" ) } )
@Table( name = "collaborator_role" )
@IdClass( CollaboratorRolePK.class )
public class CollaboratorRole implements Serializable
{
	public static final String getAll = "CollaboratorRole.getAll";

	@Id
	@Column( name = "col_seq_in", nullable = false, updatable = false, insertable = false )
	private Integer collaboratorSequence;

	@Id
	@Column( name = "rol_id_in", nullable = false, updatable = false, insertable = false )
	private Integer roleId;

	@Id
	@Column( name = "usr_id_in", nullable = false, updatable = false, insertable = false )
	private Integer companyId;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "rol_id_in", nullable = false, insertable = true, updatable = true )
	private Role role;


	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumns( { @JoinColumn( name = "col_seq_in", referencedColumnName = "col_seq_in", nullable = false, insertable = true,
								 updatable = true ),
					@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false, insertable = true,
								 updatable = true ) } )
	private Collaborator collaborator;

	public CollaboratorRole()
	{
	}


	public Integer getCollaboratorSequence()
	{
		return collaboratorSequence;
	}

	public void setCollaboratorSequence( Integer col_seq_in )
	{
		this.collaboratorSequence = col_seq_in;
	}

	public Integer getRoleId()
	{
		return roleId;
	}

	public void setRoleId( Integer rol_id_in )
	{
		this.roleId = rol_id_in;
	}

	public Integer getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId( Integer usr_id_in )
	{
		this.companyId = usr_id_in;
	}

	public void setRole( Role role )
	{
		this.role = role;
	}

	public Role getRole()
	{
		return role;
	}

	public void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
	}

	public Collaborator getCollaborator()
	{
		return collaborator;
	}
}
