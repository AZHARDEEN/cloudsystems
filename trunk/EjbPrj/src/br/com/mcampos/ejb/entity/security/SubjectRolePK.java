package br.com.mcampos.ejb.entity.security;

import java.io.Serializable;

import java.sql.Timestamp;

public class SubjectRolePK implements Serializable
{
    private Integer companyId;
    private Integer collaboratorId;
    private Timestamp from;
    private Integer roleId;

    public SubjectRolePK()
    {
    }

    public SubjectRolePK( Integer usr_id_in, Integer col_id_in, Timestamp col_from_dt, Integer rol_id_in )
    {
        this.from = col_from_dt;
        this.collaboratorId = col_id_in;
        this.roleId = rol_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof SubjectRolePK ) {
            final SubjectRolePK otherSubjectRolePK = ( SubjectRolePK )other;
            final boolean areEqual = ( otherSubjectRolePK.from.equals( from ) && otherSubjectRolePK.collaboratorId.equals( collaboratorId ) && otherSubjectRolePK.roleId.equals( roleId ) && otherSubjectRolePK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Timestamp getFrom()
    {
        return from;
    }

    void setFrom( Timestamp col_from_dt )
    {
        this.from = col_from_dt;
    }

    Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    void setCollaboratorId( Integer col_id_in )
    {
        this.collaboratorId = col_id_in;
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
