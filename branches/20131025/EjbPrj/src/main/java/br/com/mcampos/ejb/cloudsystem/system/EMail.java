package br.com.mcampos.ejb.cloudsystem.system;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "EMail.findAll", query = "select o from EMail o" ) } )
@Table( name = "e_mail" )
public class EMail implements Serializable
{
	@Column( name = "eml_description_ch", nullable = false )
	private String description;
	@Id
	@Column( name = "eml_id_in", nullable = false )
	private Integer id;

	/*
	@OneToMany( mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<EMailPart> EMailPartList;
	*/

	public EMail( )
	{
	}

	public EMail( String eml_description_ch, Integer eml_id_in )
	{
		description = eml_description_ch;
		id = eml_id_in;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String eml_description_ch )
	{
		description = eml_description_ch;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer eml_id_in )
	{
		id = eml_id_in;
	}

	public List<EMailPart> getEMailPartList( )
	{
		/*
		if ( EMailPartList == null )
			EMailPartList = new ArrayList<EMailPart>( );
		return EMailPartList;
		*/
		return null;
	}

	public void setEMailPartList( List<EMailPart> EMailPartList )
	{
		// this.EMailPartList = EMailPartList;
	}

	public EMailPart addEMailPart( EMailPart EMailPart )
	{
		getEMailPartList( ).add( EMailPart );
		EMailPart.setEmail( this );
		return EMailPart;
	}

	public EMailPart removeEMailPart( EMailPart EMailPart )
	{
		getEMailPartList( ).remove( EMailPart );
		EMailPart.setEmail( null );
		return EMailPart;
	}
}
