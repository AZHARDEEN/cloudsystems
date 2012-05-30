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


@Entity
@NamedQueries( { @NamedQuery( name = "UserContact.findAll", query = "select o from UserContact o" ) } )
@Table( name = "\"user_contact\"" )
@IdClass( UserContactPK.class )
public class UserContact implements Serializable
{
	private static final long serialVersionUID = -8452887413412919630L;

	@Column( name = "cct_id_in", nullable = false )
	private Integer contactId;

	@Id
	@Column( name = "uct_description_ch", nullable = false )
	private String description;

	@Column( name = "uct_observation_tx" )
	private String obs;

	@Id
	@Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
	private Integer userId;

	@ManyToOne
	@JoinColumn( name = "usr_id_in" )
	private Users user;

	public UserContact()
	{
	}

	public UserContact( Users user )
	{
		this.user = user;
	}

	public Integer getContactId()
	{
		return this.contactId;
	}

	public void setContactId( Integer cct_id_in )
	{
		this.contactId = cct_id_in;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String uct_description_ch )
	{
		this.description = uct_description_ch;
	}

	public String getObs()
	{
		return this.obs;
	}

	public void setObs( String uct_observation_tx )
	{
		this.obs = uct_observation_tx;
	}

	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}

	public Users getUser()
	{
		return this.user;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( user != null ) {
			user.add( this );
		}
	}

}
