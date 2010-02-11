package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Menu.findAll", query = "select o from Menu o" ) } )
@Table( name = "\"menu\"" )
public class Menu implements Serializable
{
    private String description;
    private Integer id;
    private Integer sequence;
    private String targetURL;
    private Menu menu;
    private List<Menu> menuList;
    private Media media;

    private Boolean separatorBefore;
    private Boolean autocheck;
    private Boolean checked;
    private Boolean checkmark;
    private Boolean disabled;


    public Menu()
    {
    }

    @Column( name = "mnu_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String mnu_description_ch )
    {
        this.description = mnu_description_ch;
    }

    @Id
    @Column( name = "mnu_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer mnu_id_in )
    {
        this.id = mnu_id_in;
    }

    @Column( name = "mnu_sequence_in", nullable = false )
    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer mnu_sequence_in )
    {
        this.sequence = mnu_sequence_in;
    }

    @Column( name = "mnu_url_ch" )
    public String getTargetURL()
    {
        return targetURL;
    }

    public void setTargetURL( String mnu_url_ch )
    {
        this.targetURL = mnu_url_ch;
    }

    @ManyToOne
    @JoinColumn( name = "mnu_parent_id" )
    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu( Menu menu )
    {
        this.menu = menu;
    }

    @OneToMany( mappedBy = "menu" )
    public List<Menu> getMenuList()
    {
        if ( menuList == null )
            menuList = new ArrayList<Menu>();
        return menuList;
    }

    public void setMenuList( List<Menu> menuList )
    {
        this.menuList = menuList;
    }

    public Menu addMenu( Menu menu )
    {
        getMenuList().add( menu );
        menu.setMenu( this );
        return menu;
    }

    public Menu removeMenu( Menu menu )
    {
        getMenuList().remove( menu );
        menu.setMenu( null );
        return menu;
    }

    public void setMedia( Media media )
    {
        this.media = media;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "med_id_in" )
    public Media getMedia()
    {
        return media;
    }

    public void setSeparatorBefore( Boolean mnu_separator_before_bt )
    {
        this.separatorBefore = mnu_separator_before_bt;
    }

    @Column( name = "mnu_separator_before_bt" )
    public Boolean getSeparatorBefore()
    {
        return separatorBefore;
    }

    public void setAutocheck( Boolean mnu_autocheck_bt )
    {
        this.autocheck = mnu_autocheck_bt;
    }

    @Column( name = "mnu_autocheck_bt" )
    public Boolean getAutocheck()
    {
        return autocheck;
    }

    public void setChecked( Boolean mnu_checked_bt )
    {
        this.checked = mnu_checked_bt;
    }

    @Column( name = "mnu_checked_bt" )
    public Boolean getChecked()
    {
        return checked;
    }

    public void setCheckmark( Boolean mnu_checkmark_bt )
    {
        this.checkmark = mnu_checkmark_bt;
    }

    @Column( name = "mnu_checkmark_bt" )
    public Boolean getCheckmark()
    {
        return checkmark;
    }

    public void setDisabled( Boolean mnu_disabled_bt )
    {
        this.disabled = mnu_disabled_bt;
    }

    @Column( name = "mnu_disabled_bt" )
    public Boolean getDisabled()
    {
        return disabled;
    }
}
