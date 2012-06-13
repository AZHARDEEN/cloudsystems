package br.com.mcampos.ejb.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.usertype.UserType;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@Table( name = "users" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "ust_id_in", discriminatorType = DiscriminatorType.STRING )
public abstract class Users implements Serializable, Comparable<Users>
{
	private static final long serialVersionUID = 2007654781360689494L;

	@Id
	@SequenceGenerator( name = "userIdGenerator", sequenceName = "seq_user", allocationSize = 1 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "userIdGenerator" )
	@Column( name = "usr_id_in", nullable = false )
	private Integer id;

	@Column( name = "usr_insert_dt", nullable = false )
	@Temporal( value = TemporalType.DATE )
	private Date insertDate;

	@Column( name = "usr_name_ch", nullable = false )
	private String name;

	@Column( name = "usr_nick_name_ch" )
	private String nickName;

	@Column( name = "usr_update_dt" )
	@Temporal( value = TemporalType.DATE )
	private Date updateDate;

	@ManyToOne( optional = false )
	@JoinColumn( name = "ust_id_in", referencedColumnName = "ust_id_in", insertable = true, updatable = true, nullable = false )
	private UserType userType;

	@OneToMany( mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
	private List<UserDocument> documents;

	@OneToMany( mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<UserContact> contacts;

	@OneToMany( mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Address> addresses;

	@Column( name = "usr_birth_dt" )
	@Temporal( value = TemporalType.DATE )
	private Date birthDate;

	public Users( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer usr_id_in )
	{
		this.id = usr_id_in;
	}

	public Date getInsertDate( )
	{
		if ( this.insertDate == null ) {
			this.insertDate = new Date( );
		}
		return this.insertDate;
	}

	public void setInsertDate( Date usr_insert_dt )
	{
		this.insertDate = usr_insert_dt;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String usr_name_ch )
	{
		this.name = usr_name_ch;
	}

	public String getNickName( )
	{
		return this.nickName;
	}

	public void setNickName( String usr_nick_name_ch )
	{
		this.nickName = usr_nick_name_ch;
	}

	public Date getUpdateDate( )
	{
		return this.updateDate;
	}

	public void setUpdateDate( Date usr_update_dt )
	{
		this.updateDate = usr_update_dt;
	}

	public List<UserDocument> getDocuments( )
	{
		if ( this.documents == null ) {
			this.documents = new ArrayList<UserDocument>( );
		}
		return this.documents;
	}

	public void setDocuments( List<UserDocument> list )
	{
		getDocuments( ).clear( );
		for ( UserDocument i : list ) {
			add( i );
		}
	}

	public UserDocument add( UserDocument item )
	{
		if ( item != null ) {
			int nIndex = getDocuments( ).indexOf( item );
			if ( nIndex < 0 ) {
				getDocuments( ).add( item );
				item.setUser( this );
			}
		}
		return item;
	}

	public UserDocument remove( UserDocument item )
	{
		SysUtils.remove( getDocuments( ), item );
		if ( item != null ) {
			item.setUser( null );
		}
		return item;
	}

	public List<UserContact> getContacts( )
	{
		if ( this.contacts == null ) {
			this.contacts = new ArrayList<UserContact>( );
		}
		return this.contacts;
	}

	public void setContacts( List<UserContact> list )
	{
		getContacts( ).clear( );
		for ( UserContact i : list ) {
			add( i );
		}
	}

	public UserContact add( UserContact item )
	{
		if ( item != null ) {
			int nIndex = getContacts( ).indexOf( item );
			if ( nIndex < 0 ) {
				getContacts( ).add( item );
				item.setUser( this );
			}
		}
		return item;
	}

	public UserContact remove( UserContact item )
	{
		SysUtils.remove( getContacts( ), item );
		if ( item != null ) {
			item.setUser( null );
		}
		return item;
	}

	public List<Address> getAddresses( )
	{
		if ( this.addresses == null ) {
			this.addresses = new ArrayList<Address>( );
		}
		return this.addresses;
	}

	public void setAddresses( List<Address> list )
	{
		getAddresses( ).clear( );
		for ( Address i : list ) {
			add( i );
		}
	}

	public Address add( Address item )
	{
		if ( item != null ) {
			int nIndex = getAddresses( ).indexOf( item );
			if ( nIndex < 0 ) {
				getAddresses( ).add( item );
				item.setUser( this );
			}
		}
		return item;
	}

	public Address remove( Address address )
	{
		SysUtils.remove( getAddresses( ), address );
		if ( address != null ) {
			address.setUser( null );
		}
		return address;
	}

	public void setUserType( UserType userType )
	{
		this.userType = userType;
	}

	public UserType getUserType( )
	{
		return this.userType;
	}

	public Date getBirthDate( )
	{
		return this.birthDate;
	}

	public void setBirthDate( Date usr_birth_dt )
	{
		this.birthDate = usr_birth_dt;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null ) {
			return false;
		}
		if ( obj instanceof Users ) {
			Users other = (Users) obj;
			return getId( ).equals( other.getId( ) );
		}
		else if ( obj instanceof Integer )
		{
			Integer id = (Integer) obj;
			return getId( ).equals( id );
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo( Users o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Transient
	public String getEmail( )
	{
		for ( UserDocument document : getDocuments( ) )
		{
			if ( document.getType( ).getId( ).equals( UserDocument.typeEmail ) ) {
				return document.getCode( );
			}
		}
		return null;
	}

	@Transient
	public String getDocument( )
	{
		for ( UserDocument document : getDocuments( ) )
		{
			if ( getUserType( ).getId( ).equals( 1 ) ) {
				if ( document.getType( ).getId( ).equals( UserDocument.typeCPF ) ) {
					return document.getCode( );
				}
			}
			else {
				if ( document.getType( ).getId( ).equals( UserDocument.typeCNPJ ) ) {
					return document.getCode( );
				}
			}
		}
		return null;
	}
}
