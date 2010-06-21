package br.com.mcampos.ejb.cloudsystem.user.media.entity;

import java.io.Serializable;

public class UserMediaPK implements Serializable
{
    public UserMediaPK()
    {
    }

    public UserMediaPK( Integer med_id_in, Integer usr_id_in )
    {
        this.med_id_in = med_id_in;
        this.usr_id_in = usr_id_in;
    }
    private Integer med_id_in;
    private Integer usr_id_in;

    public boolean equals( Object other )
    {
        if (other instanceof UserMediaPK) {
            final UserMediaPK otherUserMediaPK = (UserMediaPK) other;
            final boolean areEqual = (otherUserMediaPK.med_id_in.equals(med_id_in) && otherUserMediaPK.usr_id_in.equals(usr_id_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMed_id_in()
    {
        return med_id_in;
    }

    void setMed_id_in( Integer med_id_in )
    {
        this.med_id_in = med_id_in;
    }

    Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }
}
