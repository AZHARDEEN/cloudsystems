package br.com.mcampos.ejb.cloudsystem.security.menu.locale;


import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.entity.address.Country;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = MenuLocale.getAll, query = "select o from MenuLocale o" ),
                 @NamedQuery( name = MenuLocale.getAllByCountry, query = "select o from MenuLocale o where o.country = ?1" ),
                 @NamedQuery( name = MenuLocale.getAllByMenu, query = "select o from MenuLocale o where o.menu = ?1" ) } )
@Table( name = "menu_locale" )
@IdClass( MenuLocalePK.class )
public class MenuLocale implements Serializable
{
    public static final String getAll = "MenuLocale.findAll";
    public static final String getAllByCountry = "MenuLocale.getAllByCountry";
    public static final String getAllByMenu = "MenuLocale.getAllByMenu";

    @Id
    @Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
    private String countryId;

    @Id
    @Column( name = "mnu_id_in", nullable = false, insertable = false, updatable = false )
    private Integer menuId;

    @Column( name = "mnu_description_ch", nullable = false )
    private String description;


    @ManyToOne
    @JoinColumn( name = "ctr_code_ch", nullable = false )
    private Country country;

    @ManyToOne
    @JoinColumn( name = "mnu_id_in", nullable = false )
    private Menu menu;


    public MenuLocale()
    {
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId( String ctr_code_ch )
    {
        this.countryId = ctr_code_ch;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String mnu_description_ch )
    {
        this.description = mnu_description_ch;
    }

    public Integer getMenuId()
    {
        return menuId;
    }

    public void setMenuId( Integer mnu_id_in )
    {
        this.menuId = mnu_id_in;
    }

    public void setCountry( Country country )
    {
        this.country = country;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setMenu( Menu menu )
    {
        this.menu = menu;
    }

    public Menu getMenu()
    {
        return menu;
    }
}
