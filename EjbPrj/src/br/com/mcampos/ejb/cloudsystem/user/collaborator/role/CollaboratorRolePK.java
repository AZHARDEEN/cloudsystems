package br.com.mcampos.ejb.cloudsystem.user.collaborator.role;

import java.io.Serializable;

public class CollaboratorRolePK implements Serializable
{
	private Integer collaboratorSequence;
	private Integer roleId;
	private Integer companyId;

	public CollaboratorRolePK()
	{
	}

	public CollaboratorRolePK( Integer col_seq_in, Integer rol_id_in, Integer usr_id_in )
	{
		this.collaboratorSequence = col_seq_in;
		this.roleId = rol_id_in;
		this.companyId = usr_id_in;
	}

	public boolean equals( Object other )
	{
		if ( other instanceof CollaboratorRolePK ) {
			final CollaboratorRolePK otherCollaboratorRolePK = ( CollaboratorRolePK )other;
			final boolean areEqual =
						 ( otherCollaboratorRolePK.collaboratorSequence.equals( collaboratorSequence ) && otherCollaboratorRolePK.roleId.equals( roleId ) &&
						   otherCollaboratorRolePK.companyId.equals( companyId ) );
			return areEqual;
		}
		return false;
	}

	public int hashCode()
	{
		return super.hashCode();
	}

	Integer getCollaboratorSequence()
	{
		return collaboratorSequence;
	}

	void setCollaboratorSequence( Integer col_seq_in )
	{
		this.collaboratorSequence = col_seq_in;
	}

	Integer getRoleId()
	{
		return roleId;
	}

	void setRoleId( Integer rol_id_in )
	{
		this.roleId = rol_id_in;
	}

	Integer getCompanyId()
	{
		return companyId;
	}

	void setCompanyId( Integer usr_id_in )
	{
		this.companyId = usr_id_in;
	}
}
