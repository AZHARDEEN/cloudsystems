package br.com.mcampos.ejb.entity.user.pk;

import java.io.Serializable;

import java.sql.Timestamp;

public class CollaboratorPK implements Serializable
{
    private Timestamp fromDate;
    private Integer collaboratorId;
    private Integer companyId;

    public CollaboratorPK()
    {
    }

    public CollaboratorPK( Integer companyId, Integer collaboratorId, Timestamp fromDate )
    {
        this.fromDate = fromDate;
        this.collaboratorId = collaboratorId;
        this.companyId = companyId;
    }

    public boolean equals( Object other )
    {
        if (other instanceof CollaboratorPK) {
            final CollaboratorPK otherCollaboratorPK = (CollaboratorPK) other;
            final boolean areEqual =
                (otherCollaboratorPK.fromDate.equals(fromDate) && otherCollaboratorPK.collaboratorId.equals(collaboratorId) && otherCollaboratorPK.companyId.equals(companyId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Timestamp getFromDate()
    {
        return fromDate;
    }

    void setFromDate( Timestamp fromDate )
    {
        this.fromDate = fromDate;
    }

    Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    void setCollaboratorId( Integer collaboratorId )
    {
        this.collaboratorId = collaboratorId;
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
