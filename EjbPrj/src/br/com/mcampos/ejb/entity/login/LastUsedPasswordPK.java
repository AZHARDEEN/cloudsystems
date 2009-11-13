package br.com.mcampos.ejb.entity.login;

import java.io.Serializable;

public class LastUsedPasswordPK implements Serializable
{
    private String password;
    private Integer userId;

    public LastUsedPasswordPK()
    {
    }

    public LastUsedPasswordPK( String password, Integer userId )
    {
        this.password = password;
        this.userId = userId;
    }

    public boolean equals( Object other )
    {
        if (other instanceof LastUsedPasswordPK) {
            final LastUsedPasswordPK otherLastUsedPasswordPK = (LastUsedPasswordPK) other;
            final boolean areEqual =
                (otherLastUsedPasswordPK.password.equals(password) && otherLastUsedPasswordPK.userId.equals(userId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getPassword()
    {
        return password;
    }

    void setPassword( String lup_password_ch )
    {
        this.password = lup_password_ch;
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
