package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String image;
    private Integer sequence;
    private String url;
    private Menu menu;
    private List<Menu> menuList;

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

    @Column( name = "mnu_image_bin" )
    public String getImage()
    {
        return image;
    }

    public void setImage( String mnu_image_bin )
    {
        this.image = mnu_image_bin;
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
    public String getUrl()
    {
        return url;
    }

    public void setUrl( String mnu_url_ch )
    {
        this.url = mnu_url_ch;
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
}
