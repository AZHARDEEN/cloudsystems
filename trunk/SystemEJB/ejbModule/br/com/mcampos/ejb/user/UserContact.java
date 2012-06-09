package br.com.mcampos.ejb.user;

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

import br.com.mcampos.ejb.user.contact.type.ContactType;

@Entity
@NamedQueries( { @NamedQuery( name = "UserContact.findAll", query = "select o from UserContact o" ) } )
@Table( name = "\"user_contact\"" )
@IdClass( UserContactPK.class )
public class UserContact implements Serializable, Comparable<UserContact>
{
	private static final long serialVersionUID = -8452887413412919630L;

	@ManyToOne
	@JoinColumn( name = "cct_id_in", nullable = false, insertable = true, updatable = true )
	private ContactType type;

	@Id
	@Column( name = "uct_description_ch", nullable = false )
	private String description;

	@Column( name = "uct_observation_tx" )
	private String obs;

	@Id
	@Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
	private Integer userId;

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
		return this.description;
	}

	public void setDescription( String uct_description_ch )
	{
		this.description = uct_description_ch;
	}

	public String getObs( )
	{
		return this.obs;
	}

	public void setObs( String uct_observation_tx )
	{
		this.obs = uct_observation_tx;
	}

	public Integer getUserId( )
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}

	public Users getUser( )
	{
		return this.user;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( user != null ) {
			setUserId( user.getId( ) );
			user.add( this );
		}
	}

	public ContactType getType( )
	{
		return this.type;
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
}
