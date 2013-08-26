package br.com.mcampos.ejb.user.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.mcampos.ejb.locale.city.City;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.person.civilstate.CivilState;
import br.com.mcampos.ejb.user.person.gender.Gender;
import br.com.mcampos.ejb.user.person.title.Title;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@Table( name = "\"person\"" )
@DiscriminatorValue( "1" )
public class Person extends Users implements Serializable
{
	private static final long serialVersionUID = 8776906464314360656L;

	@ManyToOne( fetch = FetchType.EAGER, optional = true )
	@JoinColumn( name = "cst_id_in", referencedColumnName = "cst_id_in", insertable = true, updatable = true, nullable = true )
	private CivilState civilState;

	@ManyToOne( fetch = FetchType.EAGER, optional = true )
	@JoinColumn( name = "gnd_id_in", referencedColumnName = "gnd_id_in", insertable = true, updatable = true, nullable = true )
	private Gender gender;

	@ManyToOne( fetch = FetchType.EAGER, optional = true )
	@JoinColumn( name = "ttl_id_in", referencedColumnName = "ttl_id_in", insertable = true, updatable = true, nullable = true )
	private Title title;

	@ManyToOne( fetch = FetchType.EAGER, optional = true )
	@JoinColumn( name = "usr_born_city_in", nullable = true, insertable = true, updatable = true )
	private City bornCity;

	@Column( name = "usr_father_name_ch" )
	private String fatherName;

	@Column( name = "usr_mother_name_ch" )
	private String motherName;

	@Column( name = "usr_first_name_ch" )
	private String firstName;

	@Column( name = "usr_last_name_ch" )
	private String lastName;

	@Column( name = "usr_middle_name_ch" )
	private String middleName;

	public Person( )
	{
	}

	public CivilState getCivilState( )
	{
		return civilState;
	}

	public void setCivilState( CivilState state )
	{
		civilState = state;
	}

	public Gender getGender( )
	{
		return gender;
	}

	public void setGender( Gender gender )
	{
		this.gender = gender;
	}

	public Title getTitle( )
	{
		return title;
	}

	public void setTitle( Title title )
	{
		this.title = title;
	}

	public City getBornCity( )
	{
		return bornCity;
	}

	public void setBornCity( City bornCity )
	{
		this.bornCity = bornCity;
	}

	public String getFatherName( )
	{
		return fatherName;
	}

	public void setFatherName( String usr_father_name_ch )
	{
		fatherName = usr_father_name_ch;
	}

	public String getFirstName( )
	{
		return firstName;
	}

	public void setFirstName( String usr_first_name_ch )
	{
		firstName = usr_first_name_ch;
	}

	public String getLastName( )
	{
		return lastName;
	}

	public void setLastName( String usr_last_name_ch )
	{
		lastName = usr_last_name_ch;
	}

	public String getMiddleName( )
	{
		return middleName;
	}

	public void setMiddleName( String usr_middle_name_ch )
	{
		middleName = usr_middle_name_ch;
	}

	public String getMotherName( )
	{
		return motherName;
	}

	public void setMotherName( String usr_mother_name_ch )
	{
		motherName = usr_mother_name_ch;
	}

	@Transient
	public String getFriendlyName( )
	{
		String strWho;
		strWho = getNickName( );
		if ( SysUtils.isEmpty( strWho ) ) {
			strWho = getFirstName( );
			if ( SysUtils.isEmpty( strWho ) )
				strWho = getName( );
		}
		return strWho;
	}
}
