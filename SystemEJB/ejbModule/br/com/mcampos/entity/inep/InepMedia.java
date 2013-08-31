package br.com.mcampos.entity.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mcampos.entity.system.Media;

/**
 * The persistent class for the inep_media database table.
 * 
 */
@Entity
@Table( name = "inep_media", schema = "inep" )
public class InepMedia implements Serializable, Comparable<InepMedia>
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepMediaPK id;

	@Column( name = "tsk_id_in", nullable = true, updatable = true, insertable = true )
	private Integer task;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", updatable = false, insertable = false, nullable = false )
	private Media media;

	// bi-directional many-to-one association to InepSubscription
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "isc_id_ch", referencedColumnName = "isc_id_ch", insertable = false, updatable = false, nullable = false ),
			@JoinColumn( name = "pct_id_in", referencedColumnName = "pct_id_in", insertable = false, updatable = false, nullable = false ),
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false, nullable = false )
	} )
	private InepSubscription inepSubscription;

	public InepMedia( )
	{
	}

	public InepMedia( InepSubscription parent )
	{
		getId( ).set( parent.getId( ) );
	}

	public InepMediaPK getId( )
	{
		if ( id == null )
			id = new InepMediaPK( );
		return id;
	}

	public void setId( InepMediaPK id )
	{
		this.id = id;
	}

	public Integer getTask( )
	{
		return task;
	}

	public void setTask( Integer tskIdIn )
	{
		task = tskIdIn;
	}

	public Media getMedia( )
	{
		return media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		getId( ).setMediaId( media != null ? media.getId( ) : null );
	}

	public InepSubscription getInepSubscription( )
	{
		return inepSubscription;
	}

	public void setInepSubscription( InepSubscription inepSubscription )
	{
		this.inepSubscription = inepSubscription;
		if ( inepSubscription != null ) {
			getId( ).set( inepSubscription.getId( ) );
			if ( inepSubscription.getMedias( ).contains( this ) == false )
				inepSubscription.add( this );
		}
		else
			getId( ).set( null );
	}

	@Override
	public int compareTo( InepMedia o )
	{
		if ( o == null )
			return -1;

		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( obj instanceof InepMedia ) {
			InepMedia other = (InepMedia) obj;
			return getId( ).equals( other.getId( ) );
		}
		else if ( obj instanceof InepMediaPK ) {
			InepMediaPK other = (InepMediaPK) obj;
			return getId( ).equals( other );
		}
		return getId( ).equals( obj );
	}
}