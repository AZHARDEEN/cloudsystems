package br.com.mcampos.ejb.entity.user.pk;

import java.io.Serializable;

public class UserDocumentPK implements Serializable
{
    private Integer documentId;
    private Integer userId;

    public UserDocumentPK()
    {
        super ();
    }

    public UserDocumentPK( Integer documentTypeId, Integer userId  )
    {
        super ();
        this.documentId = documentTypeId;
        this.userId = userId;
    }

    public boolean equals( Object other )
    {
        if (other instanceof UserDocumentPK) {
            final UserDocumentPK otherUserDocumentPK = (UserDocumentPK) other;
            final boolean areEqual =
                (otherUserDocumentPK.documentId.equals(documentId) && otherUserDocumentPK.userId.equals(userId) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getDocumentId()
    {
        return documentId;
    }

    void setDocumentId( Integer documentTypeId )
    {
        this.documentId = documentTypeId;
    }

    Integer getUserId()
    {
        return userId;
    }

    void setUserId( Integer userId )
    {
        this.userId = userId;
    }
}
