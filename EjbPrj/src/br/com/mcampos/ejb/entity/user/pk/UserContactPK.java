package br.com.mcampos.ejb.entity.user.pk;

import java.io.Serializable;

public class UserContactPK implements Serializable
{
    private String description;
    private Integer userId;

    public UserContactPK()
    {
    }

    public UserContactPK( String uct_description_ch, Integer usr_id_in )
    {
        this.description = uct_description_ch;
        this.userId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof UserContactPK) {
            final UserContactPK otherUserContactPK = (UserContactPK) other;
            final boolean areEqual =
                (otherUserContactPK.description.equals(description) && otherUserContactPK.userId.equals(userId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getDescription()
    {
        return description;
    }

    void setDescription( String uct_description_ch )
    {
        this.description = uct_description_ch;
    }

    Integer getUserId()
    {
        return userId;
    }

    void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }
}
