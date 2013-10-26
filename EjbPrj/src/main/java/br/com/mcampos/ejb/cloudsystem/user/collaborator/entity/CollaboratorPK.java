package br.com.mcampos.ejb.cloudsystem.user.collaborator.entity;

import java.io.Serializable;


public class CollaboratorPK implements Serializable
{
    private Integer sequence;
    private Integer companyId;

    public CollaboratorPK()
    {
    }

    public CollaboratorPK( Integer companyId, Integer sequence )
    {
        setSequence( sequence );
        setCompanyId( companyId );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof CollaboratorPK ) {
            final CollaboratorPK otherCollaboratorPK = ( CollaboratorPK )other;
            final boolean areEqual =
                ( otherCollaboratorPK.sequence.equals( sequence ) && otherCollaboratorPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer sequence )
    {
        this.sequence = sequence;
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
