package br.com.mcampos.ejb.user;

import java.io.Serializable;
import java.sql.Timestamp;
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

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.usertype.UserType;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@Table( name = "users" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "ust_id_in", discriminatorType = DiscriminatorType.STRING )
public abstract class Users implements Serializable
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
	private Timestamp birthDate;

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
			this.documents = new ArrayList<UserDocument> ();
		}
		return this.documents;
	}

	public void setDocuments( List<UserDocument> userDocumentList )
	{
		this.documents = userDocumentList;
	}

	public UserDocument add( UserDocument userDocument )
	{
		if ( userDocument != null ) {
			userDocument.setUser( this );
			int nIndex = getDocuments( ).indexOf( userDocument );
			if ( nIndex < 0 ) {
				getDocuments( ).add( userDocument );
			}
		}
		return userDocument;
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
			this.contacts = new ArrayList<UserContact> ();
		}
		return this.contacts;
	}

	public void setContacts( List<UserContact> userContactList )
	{
		this.contacts = userContactList;
	}

	public UserContact add( UserContact userContact )
	{
		getContacts( ).add( userContact );
		return userContact;
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
			this.addresses = new ArrayList<Address> ();
		}
		return this.addresses;
	}

	public void setAddresses( List<Address> addressList )
	{
		this.addresses = addressList;
	}

	public Address add( Address address )
	{
		if ( address != null ) {
			address.setUser( this );
		}
		getAddresses( ).add( address );
		return address;
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

	public Timestamp getBirthDate( )
	{
		return this.birthDate;
	}

	public void setBirthDate( Timestamp usr_birth_dt )
	{
		this.birthDate = usr_birth_dt;
	}
}
