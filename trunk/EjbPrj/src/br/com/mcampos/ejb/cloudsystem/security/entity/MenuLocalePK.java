package br.com.mcampos.ejb.cloudsystem.security.entity;

import java.io.Serializable;

public class MenuLocalePK implements Serializable
{
    private String countryId;
    private Integer menuId;

    public MenuLocalePK()
    {
    }

    public MenuLocalePK( String ctr_code_ch, Integer mnu_id_in )
    {
        this.countryId = ctr_code_ch;
        this.menuId = mnu_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof MenuLocalePK ) {
            final MenuLocalePK otherMenuLocalePK = ( MenuLocalePK )other;
            final boolean areEqual =
                ( otherMenuLocalePK.countryId.equals( countryId ) && otherMenuLocalePK.menuId.equals( menuId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getCountryId()
    {
        return countryId;
    }

    void setCountryId( String ctr_code_ch )
    {
        this.countryId = ctr_code_ch;
    }

    Integer getMenuId()
    {
        return menuId;
    }

    void setMenuId( Integer mnu_id_in )
    {
        this.menuId = mnu_id_in;
    }
}
