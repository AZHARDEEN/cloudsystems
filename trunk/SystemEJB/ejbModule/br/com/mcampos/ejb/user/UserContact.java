package br.com.mcampos.ejb.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.user.contact.type.ContactType;

@Entity
@NamedQueries( { @NamedQuery( name = "UserContact.findAll", query = "select o from UserContact o" ) } )
@Table( name = "user_contact" )
public class UserContact implements Serializable, Comparable<UserContact>
{
	private static final long serialVersionUID = -8452887413412919630L;

	@EmbeddedId
	private UserContactPK id;

	@ManyToOne
	@JoinColumn( name = "cct_id_in", nullable = false, insertable = true, updatable = true )
	private ContactType type;

	@Column( name = "uct_observation_tx" )
	private String obs;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false )
	private Users user;

	public UserContact( )
	{
	}

	public UserContact( Users user )
	{
		this.user = user;
	}

	public String getDescription( )
	{
		return getId( ).getDescription( );
	}

	public void setDescription( String uct_description_ch )
	{
		getId( ).setDescription( uct_description_ch );
	}

	public String getObs( )
	{
		return obs;
	}

	public void setObs( String uct_observation_tx )
	{
		obs = uct_observation_tx;
	}

	public Integer getUserId( )
	{
		return getId( ).getUserId( );
	}

	public void setUserId( Integer usr_id_in )
	{
		getId( ).setUserId( usr_id_in );
	}

	public Users getUser( )
	{
		return user;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( user != null ) {
			setUserId( user.getId( ) );
		}
	}

	public ContactType getType( )
	{
		return type;
	}

	public void setType( ContactType type )
	{
		this.type = type;
	}

	@Override
	public int compareTo( UserContact o )
	{
		if ( o == null ) {
			return 1;
		}
		int nRet = 0;
		if ( getUser( ) != null ) {
			nRet = getUser( ).compareTo( o.getUser( ) );
		}
		if ( nRet == 0 && getType( ) != null ) {
			nRet = getType( ).compareTo( o.getType( ) );
		}
		return nRet;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null ) {
			return false;
		}
		if ( obj instanceof UserContact ) {
			UserContact other = (UserContact) obj;
			if ( getUser( ) != null && getUser( ).equals( other.getUser( ) ) ) {
				return getType( ).equals( other.getType( ) );
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public UserContactPK getId( )
	{
		if ( id == null )
			id = new UserContactPK( );
		return id;
	}

	public void setId( UserContactPK id )
	{
		this.id = id;
	}
}
