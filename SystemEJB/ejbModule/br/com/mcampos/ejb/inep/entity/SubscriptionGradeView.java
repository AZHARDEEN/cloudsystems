package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * The persistent class for the subscriptiongradeview database table.
 * 
 */
@Entity
@NamedNativeQueries( {
		@NamedNativeQuery(
				name = SubscriptionGradeView.getAllByEvent,
				resultClass = SubscriptionGradeView.class,
				query = "select * from inep.subscriptionGradeView where usr_id_in = ? and pct_id_in = ? " )
} )
public class SubscriptionGradeView implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getAllByEvent = "SubscriptionGradeView.getAllByEvent";

	@EmbeddedId
	private InepSubscriptionPK id;

	@Column( name = "tarefa1" )
	private Integer tarefa1;

	@Column( name = "tarefa2" )
	private Integer tarefa2;

	@Column( name = "tarefa3" )
	private Integer tarefa3;

	@Column( name = "tarefa4" )
	private Integer tarefa4;

	public SubscriptionGradeView( )
	{
	}

	public Integer getTarefa1( )
	{
		return this.tarefa1;
	}

	public void setTarefa1( Integer tarefa1 )
	{
		this.tarefa1 = tarefa1;
	}

	public Integer getTarefa2( )
	{
		return this.tarefa2;
	}

	public void setTarefa2( Integer tarefa2 )
	{
		this.tarefa2 = tarefa2;
	}

	public Integer getTarefa3( )
	{
		return this.tarefa3;
	}

	public void setTarefa3( Integer tarefa3 )
	{
		this.tarefa3 = tarefa3;
	}

	public Integer getTarefa4( )
	{
		return this.tarefa4;
	}

	public void setTarefa4( Integer tarefa4 )
	{
		this.tarefa4 = tarefa4;
	}

	public InepSubscriptionPK getId( )
	{
		return this.id;
	}

	public void setId( InepSubscriptionPK id )
	{
		this.id = id;
	}

}