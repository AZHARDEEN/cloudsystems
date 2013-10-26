package br.com.mcampos.jpa.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "UserContact.findAll", query = "select o from UserContact o" ) } )
@Table( name = "user_contact", schema = "public" )
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
		return this.getId( ).getDescription( );
	}

	@Override
	public int hashCode( )
	{
		return this.getId( ).hashCode( );
	}

	public void setDescription( String uct_description_ch )
	{
		this.getId( ).setDescription( uct_description_ch );
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
		return this.getId( ).getUserId( );
	}

	public void setUserId( Integer usr_id_in )
	{
		this.getId( ).setUserId( usr_id_in );
	}

	public Users getUser( )
	{
		return this.user;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if( user != null ) {
			this.setUserId( user.getId( ) );
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
		if( o == null ) {
			return 1;
		}
		int nRet = 0;
		if( this.getUser( ) != null ) {
			nRet = this.getUser( ).compareTo( o.getUser( ) );
		}
		if( nRet == 0 && this.getType( ) != null ) {
			nRet = this.getType( ).compareTo( o.getType( ) );
		}
		return nRet;
	}

	@Override
	public boolean equals( Object obj )
	{
		if( obj == null ) {
			return false;
		}
		if( obj instanceof UserContact ) {
			UserContact other = (UserContact) obj;
			if( this.getUser( ) != null && this.getUser( ).equals( other.getUser( ) ) ) {
				return this.getType( ).equals( other.getType( ) );
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
		if( this.id == null ) {
			this.id = new UserContactPK( );
		}
		return this.id;
	}

	public void setId( UserContactPK id )
	{
		this.id = id;
	}
}
