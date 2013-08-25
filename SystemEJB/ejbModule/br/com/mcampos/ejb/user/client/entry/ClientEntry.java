package br.com.mcampos.ejb.user.client.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.user.client.Client;

/**
 * The persistent class for the client_entry database table.
 * 
 */
@Entity
@Table( name = "client_entry" )
public class ClientEntry implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "etr_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "etr_value_mn", nullable = false, precision = 12, scale = 4 )
	private BigDecimal value;

	@Column( name = "etr_cycle_in", nullable = false )
	private Integer cycle;

	@Column( name = "col_seq_in", nullable = false )
	private Integer collaboratorId;

	@Column( name = "med_id_in", nullable = false )
	private Integer mediaId;

	@Column( name = "etr_line_number_in", nullable = false )
	private Integer lineNumber;

	@Column( name = "etr_insert_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date insertDate;

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
			@JoinColumn( name = "cli_seq_in", referencedColumnName = "cli_seq_in" )
	} )
	private Client client;

	public ClientEntry( )
	{
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer etrIdIn )
	{
		id = etrIdIn;
	}

	public BigDecimal getValue( )
	{
		return value;
	}

	public void setValue( BigDecimal etfValueMn )
	{
		value = etfValueMn;
	}

	public Integer getCycle( )
	{
		return cycle;
	}

	public void setCycle( Integer etrCycleIn )
	{
		cycle = etrCycleIn;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setInsertDate( Date etrInsertDt )
	{
		insertDate = etrInsertDt;
	}

	public Client getClient( )
	{
		return client;
	}

	public void setClient( Client client )
	{
		this.client = client;
	}

	public Integer getCollaboratorId( )
	{
		return collaboratorId;
	}

	public void setCollaboratorId( Integer collaboratorId )
	{
		this.collaboratorId = collaboratorId;
	}

	public Integer getMediaId( )
	{
		return mediaId;
	}

	public void setMediaId( Integer mediaId )
	{
		this.mediaId = mediaId;
	}

	public Integer getLineNumber( )
	{
		return lineNumber;
	}

	public void setLineNumber( Integer lineNumber )
	{
		this.lineNumber = lineNumber;
	}
}