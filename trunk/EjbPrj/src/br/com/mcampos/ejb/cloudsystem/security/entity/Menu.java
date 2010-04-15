package br.com.mcampos.ejb.cloudsystem.security.entity;


import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

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

    private String description;
    private Integer id;
    private Integer sequence;
    private String targetURL;
    private Menu parentMenu;
    private List<Menu> subMenus;
    private Media media;

    private Boolean separatorBefore;
    private Boolean autocheck;
    private Boolean checked;
    private Boolean checkmark;
    private Boolean disabled;

    private List<TaskMenu> tasks;


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

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "mnu_parent_id" )
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

    @OneToMany( mappedBy = "parentMenu", cascade = { CascadeType.REFRESH } )
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

    @OneToMany( mappedBy = "menu", cascade = { CascadeType.REFRESH } )
    public List<TaskMenu> getTasks()
    {
        return tasks;
    }

    public void remove( TaskMenu tm )
    {
        if ( tasks != null )
            tasks.remove( tm );
    }


    public void add( TaskMenu tm )
    {
        if ( tasks != null )
            tasks.add( tm );
    }

    public MenuDTO toDTO()
    {
        MenuDTO target = new MenuDTO();

        target.setId( getId() );
        target.setDescription( getDescription() );
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
}
