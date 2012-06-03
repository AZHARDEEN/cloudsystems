package br.com.mcampos.ejb.security.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.security.core.SelfRelationInterface;
import br.com.mcampos.ejb.security.core.TaskRelationInterface;
import br.com.mcampos.ejb.security.task.Task;

/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table( name = "menu" )
@NamedQueries( { @NamedQuery( name = Menu.getTopMenu, query = "select o from Menu o where o.parent is null" ) } )
public class Menu implements Serializable, TaskRelationInterface, SelfRelationInterface<Menu>, Comparable<Menu>
{
	private static final long serialVersionUID = 1L;

	public static final String getTopMenu = "Menu.getTopMenu";

	@Id
	@Column( name = "mnu_id_in", unique = true, nullable = false )
	private Integer id;

	@ManyToOne
	@JoinColumn( name = "med_id_in" )
	private Media media;

	@Column( name = "mnu_autocheck_bt" )
	private Boolean autocheck;

	@Column( name = "mnu_checked_bt" )
	private Boolean checked;

	@Column( name = "mnu_checkmark_bt" )
	private Boolean checkmark;

	@Column( name = "mnu_description_ch", nullable = false, length = 64 )
	private String description;

	@Column( name = "mnu_disabled_bt" )
	private Boolean disabled;

	@Column( name = "mnu_separator_before_bt" )
	private Boolean separatorBefore;

	@Column( name = "mnu_sequence_in", nullable = false )
	private Integer sequence;

	@Column( name = "mnu_url_ch", length = 1024 )
	private String url;

	@Column( name = "mnu_image_path_ch", length = 256, nullable = true )
	private String imagePath;

	// bi-directional many-to-one association to Menu
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "mnu_parent_id" )
	private Menu parent;

	// bi-directional many-to-one association to Menu
	@OneToMany( mappedBy = "parent", fetch = FetchType.EAGER )
	private List<Menu> childs;

	@ManyToMany( mappedBy = "menus" )
	List<Task> tasks;

	public Menu( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer mnuIdIn )
	{
		this.id = mnuIdIn;
	}

	public Boolean getAutocheck( )
	{
		return this.autocheck;
	}

	public void setAutocheck( Boolean mnuAutocheckBt )
	{
		this.autocheck = mnuAutocheckBt;
	}

	public Boolean getChecked( )
	{
		return this.checked;
	}

	public void setChecked( Boolean mnuCheckedBt )
	{
		this.checked = mnuCheckedBt;
	}

	public Boolean getCheckmark( )
	{
		return this.checkmark;
	}

	public void setCheckmark( Boolean mnuCheckmarkBt )
	{
		this.checkmark = mnuCheckmarkBt;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String mnuDescriptionCh )
	{
		this.description = mnuDescriptionCh;
	}

	public Boolean getDisabled( )
	{
		return this.disabled;
	}

	public void setDisabled( Boolean mnuDisabledBt )
	{
		this.disabled = mnuDisabledBt;
	}

	public Boolean getSeparatorBefore( )
	{
		return this.separatorBefore;
	}

	public void setSeparatorBefore( Boolean mnuSeparatorBeforeBt )
	{
		this.separatorBefore = mnuSeparatorBeforeBt;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer mnuSequenceIn )
	{
		this.sequence = mnuSequenceIn;
	}

	public String getUrl( )
	{
		return this.url;
	}

	public void setUrl( String mnuUrlCh )
	{
		this.url = mnuUrlCh;
	}

	public Menu getParent( )
	{
		return this.parent;
	}

	@Override
	public void setParent( Menu parent )
	{
		if ( parent != null ) {
			if ( parent.equals( getParent( ) ) == false ) {
				if ( parent.getParent( ) != null ) {
					parent.getParent( ).remove( this );
				}
				this.parent = parent;
				getParent( ).add( this );
			}
		}
		else {
			this.parent = null;
		}
	}

	public List<Menu> getChilds( )
	{
		if ( this.childs == null ) {
			this.childs = new ArrayList<Menu>( );
		}
		return this.childs;
	}

	public void setChilds( List<Menu> menus )
	{
		this.childs = menus;
	}

	public void setMedia( Media media )
	{
		this.media = media;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	@Override
	public List<Task> getTasks( )
	{
		return this.tasks;
	}

	@Override
	public void setTasks( List<Task> tasks )
	{
		this.tasks = tasks;
	}

	@Override
	public int compareTo( Menu o )
	{
		if ( o == null ) {
			return 1;
		}
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Menu ) {
			return getId( ).equals( ( (Menu) obj ).getId( ) );
		}
		else if ( obj instanceof Integer ) {
			return getId( ).equals( obj );
		}
		else {
			return false;
		}
	}

	@Override
	public Task add( Task task )
	{
		if ( task != null && getTasks( ).contains( task ) == false ) {
			getTasks( ).add( task );
			task.add( this );
		}
		return task;
	}

	@Override
	public Task remove( Task task )
	{
		if ( task != null )
		{
			int nIndex = getTasks( ).indexOf( task );
			if ( nIndex >= 0 ) {
				getTasks( ).remove( nIndex );
				task.remove( this );
			}
		}
		return task;

	}

	@Override
	public void add( Menu child )
	{
		if ( child != null && getChilds( ).contains( child ) == false ) {
			assert ( getChilds( ).add( child ) );
			child.setParent( this );
		}
	}

	@Override
	public Menu remove( Menu child )
	{
		if ( child != null && getChilds( ).contains( child ) ) {
			assert ( getChilds( ).remove( child ) );
			child.setParent( null );
		}
		return child;
	}

	@Override
	public String toString( )
	{
		return getId( ).toString( ) + " - " + getDescription( );
	}

	public String getImagePath( )
	{
		return this.imagePath;
	}

	public void setImagePath( String imagePath )
	{
		this.imagePath = imagePath;
	}

}