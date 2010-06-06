package br.com.mcampos.ejb.cloudsystem.user.collaborator.entity;

import java.io.Serializable;


public class CollaboratorPK implements Serializable
{
	private Integer collaboratorSequence;
	private Integer companyId;

	public CollaboratorPK()
	{
	}

	public CollaboratorPK( Integer companyId, Integer collaboratorId )
	{
		this.collaboratorSequence = collaboratorId;
		this.companyId = companyId;
	}

	public boolean equals( Object other )
	{
		if ( other instanceof CollaboratorPK ) {
			final CollaboratorPK otherCollaboratorPK = ( CollaboratorPK )other;
			final boolean areEqual =
						 ( otherCollaboratorPK.collaboratorSequence.equals( collaboratorSequence ) && otherCollaboratorPK.companyId.equals( companyId ) );
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

	void setCollaboratorSequence( Integer collaboratorId )
	{
		this.collaboratorSequence = collaboratorId;
	}

	Integer getCompanyId()
	{
		return companyId;
	}

	void setCompanyId( Integer companyId )
	{
		this.companyId = companyId;
	}
}
