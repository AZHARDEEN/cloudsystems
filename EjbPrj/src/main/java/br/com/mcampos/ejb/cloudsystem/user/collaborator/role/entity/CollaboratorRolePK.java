package br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity;

import java.io.Serializable;

public class CollaboratorRolePK implements Serializable
{
    private Integer collaboratorSequence;
    private Integer roleId;
    private Integer companyId;

    public CollaboratorRolePK()
    {
    }

    public CollaboratorRolePK( CollaboratorRole cr )
    {
        setCollaboratorSequence( cr.getCollaboratorSequence() );
        setCompanyId( cr.getCompanyId() );
        setRoleId( cr.getRoleId() );
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

    public void setCollaboratorSequence( Integer col_seq_in )
    {
        this.collaboratorSequence = col_seq_in;
    }

    Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
    }

    Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
