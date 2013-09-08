package br.com.mcampos.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the e_mail_part database table.
 * 
 */
@Entity
@Table( name = "e_mail_part", schema = "public" )
@NamedQueries( { @NamedQuery( name = EMailPart.getParts, query = "select o from EMailPart o where o.email = ?1" ) } )
public class EMailPart implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getParts = "EMailPart.getParts";

	@EmbeddedId
	private EMailPartPK id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "emp_id_in", insertable = true, updatable = true, nullable = false )
	private EMailPartType type;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "eml_id_in", insertable = false, updatable = false, nullable = false )
	private EMail email;

	@Column( name = "emp_text_tx" )
	private String text;

	public EMailPart( )
	{
	}

	public EMailPartPK getId( )
	{
		if ( id == null ) {
			id = new EMailPartPK( );
		}
		return id;
	}

	public void setId( EMailPartPK id )
	{
		this.id = id;
	}

	public String getText( )
	{
		return text;
	}

	public void setText( String empTextTx )
	{
		text = empTextTx;
	}

	public EMailPartType getType( )
	{
		return type;
	}

	public void setType( EMailPartType type )
	{
		this.type = type;
	}

	public EMail getEmail( )
	{
		return email;
	}

	public void setEmail( EMail email )
	{
		this.email = email;
		if ( getEmail( ) != null ) {
			getId( ).set( getEmail( ) );
		}
	}

}