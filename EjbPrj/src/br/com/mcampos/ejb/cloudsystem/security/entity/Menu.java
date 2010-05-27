package br.com.mcampos.ejb.cloudsystem.security.entity;


import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@NamedQueries( { @NamedQuery( name = Menu.findaAll, query = "select o from Menu o where o.parentMenu is null" ) } )
@NamedNativeQueries( { @NamedNativeQuery( name = Menu.nextSequence,
                                          query = "select coalesce ( max (  mnu_sequence_in ), 0 ) + 1 from menu where coalesce ( mnu_parent_id, 0 ) = ?" ),
                       @NamedNativeQuery( name = Menu.findSequence,
                                          query = "select 1 from Menu where coalesce ( mnu_parent_id, 0 ) = ? and mnu_sequence_in = ?" ) } )
@Table( name = "menu" )
public class Menu implements Serializable, Comparable<Menu>, EntityCopyInterface<MenuDTO>
{
    public static final String findaAll = "Menu.findAll";
    public static final String nextSequence = "Menu.nexSequence";
    public static final String findSequence = "Menu.findSequence";

    @Column( name = "mnu_description_ch", nullable = false, length = 64 )
    private String description;

    @Transient
    private String localeDescription;

    @Id
    @Column( name = "mnu_id_in", nullable = false )
    private Integer id;

    @Column( name = "mnu_sequence_in", nullable = false )
    private Integer sequence;

    @Column( name = "mnu_url_ch", length = 1024 )
    private String targetURL;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "mnu_parent_id" )
    private Menu parentMenu;

    @OneToMany( mappedBy = "parentMenu", cascade = { CascadeType.REFRESH } )
    private List<Menu> subMenus;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "med_id_in" )
    private Media media;

    @Column( name = "mnu_separator_before_bt" )
    private Boolean separatorBefore;

    @Column( name = "mnu_autocheck_bt" )
    private Boolean autocheck;

    @Column( name = "mnu_checked_bt" )
    private Boolean checked;

    @Column( name = "mnu_checkmark_bt" )
    private Boolean checkmark;

    @Column( name = "mnu_disabled_bt" )
    private Boolean disabled;

    @OneToMany( mappedBy = "menu", cascade = { CascadeType.REFRESH } )
    private List<TaskMenu> tasks;


    public Menu()
    {
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String mnu_description_ch )
    {
        this.description = mnu_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer mnu_id_in )
    {
        this.id = mnu_id_in;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer mnu_sequence_in )
    {
        this.sequence = mnu_sequence_in;
    }

    public String getTargetURL()
    {
        return targetURL;
    }

    public void setTargetURL( String mnu_url_ch )
    {
        this.targetURL = mnu_url_ch;
    }

    public Menu getParentMenu()
    {
        return parentMenu;
    }

    protected boolean isMyParentMenu( Menu parentMenu )
    {
        return ( getParentMenu() != null && getParentMenu().equals( parentMenu ) );
    }

    public void setParentMenu( Menu parentMenu )
    {
        if ( isMyParentMenu( parentMenu ) == false ) {
            this.parentMenu = parentMenu;
            if ( parentMenu != null )
                parentMenu.addMenu( this );
        }
    }

    public List<Menu> getSubMenus()
    {
        return subMenus;
    }

    public void setSubMenus( List<Menu> menuList )
    {
        this.subMenus = menuList;
    }

    public Menu addMenu( Menu childMenu )
    {
        if ( getSubMenus().contains( childMenu ) )
            return childMenu;
        getSubMenus().add( childMenu );
        childMenu.setParentMenu( this );
        return childMenu;
    }

    public Menu removeMenu( Menu childMenu )
    {
        Integer nIndex;

        nIndex = getSubMenus().indexOf( childMenu );
        if ( nIndex >= 0 ) {
            getSubMenus().remove( nIndex );
            childMenu.setParentMenu( null );
        }
        return childMenu;
    }

    public void setMedia( Media media )
    {
        this.media = media;
    }

    public Media getMedia()
    {
        return media;
    }

    public void setSeparatorBefore( Boolean mnu_separator_before_bt )
    {
        this.separatorBefore = mnu_separator_before_bt;
    }

    public Boolean getSeparatorBefore()
    {
        return separatorBefore;
    }

    public void setAutocheck( Boolean mnu_autocheck_bt )
    {
        this.autocheck = mnu_autocheck_bt;
    }

    public Boolean getAutocheck()
    {
        return autocheck;
    }

    public void setChecked( Boolean mnu_checked_bt )
    {
        this.checked = mnu_checked_bt;
    }

    public Boolean getChecked()
    {
        return checked;
    }

    public void setCheckmark( Boolean mnu_checkmark_bt )
    {
        this.checkmark = mnu_checkmark_bt;
    }

    public Boolean getCheckmark()
    {
        return checkmark;
    }

    public void setDisabled( Boolean mnu_disabled_bt )
    {
        this.disabled = mnu_disabled_bt;
    }

    public Boolean getDisabled()
    {
        return disabled;
    }

    public int compareTo( Menu o )
    {
        if ( o == null )
            return -1;
        else if ( this.getId() == null )
            return 1;
        else
            return this.getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        if ( this.getId() == null )
            return false;
        return this.getId().equals( ( ( Menu )obj ).getId() );
    }

    public void setTasks( List<TaskMenu> tasks )
    {
        this.tasks = tasks;
    }

    public List<TaskMenu> getTasks()
    {
        return tasks;
    }

    public void remove( TaskMenu tm )
    {
        if ( getTasks() != null )
            getTasks().remove( tm );
    }


    public void add( TaskMenu tm )
    {
        if ( getTasks() != null )
            getTasks().add( tm );
    }

    public MenuDTO toDTO()
    {
        MenuDTO target = new MenuDTO();

        target.setId( getId() );
        target.setDescription( getLocaleDescription() );
        target.setSequence( getSequence() );
        target.setTargetURL( getTargetURL() );
        target.setAutocheck( getAutocheck() );
        target.setChecked( getChecked() );
        target.setCheckmark( getCheckmark() );
        target.setDisabled( getDisabled() );
        target.setSeparatorBefore( getSeparatorBefore() );
        if ( getParentMenu() != null )
            target.setParent( getParentMenu().toDTO() );
        return target;
    }

    public void setLocaleDescription( String localeName )
    {
        this.localeDescription = localeName;
    }

    public String getLocaleDescription()
    {
        return ( SysUtils.isEmpty( localeDescription ) ) ? getDescription() : localeDescription;
    }
}
