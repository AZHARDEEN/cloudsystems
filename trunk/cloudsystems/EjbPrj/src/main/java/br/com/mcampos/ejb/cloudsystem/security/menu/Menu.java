package br.com.mcampos.ejb.cloudsystem.security.menu;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( {
		@NamedQuery( name = Menu.findaAll, query = "select o from Menu o where o.parentMenu is null" ),
		@NamedQuery( name = Menu.nextId, query = "select max(o.id) from Menu o " ),
		@NamedQuery( name = Menu.nextSequence, query = "select coalesce ( max(o.sequence), 0 ) + 1 from Menu o where coalesce ( o.parentMenu.id, 0 ) = ?1" ),
		@NamedQuery( name = Menu.findSequence, query = "select o from Menu o where coalesce ( o.id, 0 ) = ?1 and o.sequence = ?2 " )
} )
@Table( name = "menu" )
public class Menu implements Serializable, Comparable<Menu>, EntityCopyInterface<MenuDTO>
{
	public static final String findaAll = "Menu.findAll";
	public static final String nextSequence = "Menu.nexSequence";
	public static final String findSequence = "Menu.findSequence";
	public static final String nextId = "Menu.nextId";

	private static final long serialVersionUID = 2653881688634707914L;

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

	// @OneToMany( mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	// private List<Menu> subMenus;

	@ManyToOne
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

	// @OneToMany( mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	// private List<TaskMenu> tasks;

	public Menu( )
	{
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String mnu_description_ch )
	{
		description = SysUtils.trim( mnu_description_ch );
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer mnu_id_in )
	{
		id = mnu_id_in;
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer mnu_sequence_in )
	{
		sequence = mnu_sequence_in;
	}

	public String getTargetURL( )
	{
		return targetURL;
	}

	public void setTargetURL( String mnu_url_ch )
	{
		targetURL = SysUtils.trim( mnu_url_ch );
	}

	public Menu getParentMenu( )
	{
		return parentMenu;
	}

	protected boolean isMyParentMenu( Menu parentMenu )
	{
		return ( getParentMenu( ) != null && getParentMenu( ).equals( parentMenu ) );
	}

	public void setParentMenu( Menu parentMenu )
	{
		if ( isMyParentMenu( parentMenu ) == false ) {
			this.parentMenu = parentMenu;
			if ( parentMenu != null )
				parentMenu.addMenu( this );
		}
	}

	public List<Menu> getSubMenus( )
	{
		// return subMenus;
		return null;
	}

	public void setSubMenus( List<Menu> menuList )
	{
		// subMenus = menuList;
	}

	public Menu addMenu( Menu childMenu )
	{
		if ( getSubMenus( ).contains( childMenu ) )
			return childMenu;
		getSubMenus( ).add( childMenu );
		childMenu.setParentMenu( this );
		return childMenu;
	}

	public Menu removeMenu( Menu childMenu )
	{
		Integer nIndex;

		nIndex = getSubMenus( ).indexOf( childMenu );
		if ( nIndex >= 0 ) {
			getSubMenus( ).remove( nIndex );
			childMenu.setParentMenu( null );
		}
		return childMenu;
	}

	public void setMedia( Media media )
	{
		this.media = media;
	}

	public Media getMedia( )
	{
		return media;
	}

	public void setSeparatorBefore( Boolean mnu_separator_before_bt )
	{
		separatorBefore = mnu_separator_before_bt;
	}

	public Boolean getSeparatorBefore( )
	{
		return separatorBefore;
	}

	public void setAutocheck( Boolean mnu_autocheck_bt )
	{
		autocheck = mnu_autocheck_bt;
	}

	public Boolean getAutocheck( )
	{
		return autocheck;
	}

	public void setChecked( Boolean mnu_checked_bt )
	{
		checked = mnu_checked_bt;
	}

	public Boolean getChecked( )
	{
		return checked;
	}

	public void setCheckmark( Boolean mnu_checkmark_bt )
	{
		checkmark = mnu_checkmark_bt;
	}

	public Boolean getCheckmark( )
	{
		return checkmark;
	}

	public void setDisabled( Boolean mnu_disabled_bt )
	{
		disabled = mnu_disabled_bt;
	}

	public Boolean getDisabled( )
	{
		return disabled;
	}

	@Override
	public int compareTo( Menu o )
	{
		if ( o == null )
			return -1;
		else if ( this.getId( ) == null )
			return 1;
		else
			return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( this.getId( ) == null )
			return false;
		return this.getId( ).equals( ( (Menu) obj ).getId( ) );
	}

	public void setTasks( List<TaskMenu> tasks )
	{
		// this.tasks = tasks;
	}

	public List<TaskMenu> getTasks( )
	{
		return null;// tasks;
	}

	public void remove( TaskMenu tm )
	{
		if ( getTasks( ) != null )
			getTasks( ).remove( tm );
	}

	public void add( TaskMenu tm )
	{
		if ( getTasks( ) != null )
			getTasks( ).add( tm );
	}

	@Override
	public MenuDTO toDTO( )
	{
		MenuDTO target = new MenuDTO( );

		target.setId( getId( ) );
		target.setDescription( getLocaleDescription( ) );
		target.setSequence( getSequence( ) );
		target.setTargetURL( getTargetURL( ) );
		target.setAutocheck( getAutocheck( ) );
		target.setChecked( getChecked( ) );
		target.setCheckmark( getCheckmark( ) );
		target.setDisabled( getDisabled( ) );
		target.setSeparatorBefore( getSeparatorBefore( ) );
		if ( getParentMenu( ) != null )
			target.setParent( getParentMenu( ).toDTO( ) );
		return target;
	}

	public void setLocaleDescription( String localeName )
	{
		localeDescription = SysUtils.trim( localeName );
	}

	public String getLocaleDescription( )
	{
		return ( SysUtils.isEmpty( localeDescription ) ) ? getDescription( ) : localeDescription;
	}
}
