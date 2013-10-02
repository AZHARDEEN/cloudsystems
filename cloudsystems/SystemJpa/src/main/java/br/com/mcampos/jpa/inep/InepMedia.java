package br.com.mcampos.jpa.inep;

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

import br.com.mcampos.jpa.system.Media;

/**
 * The persistent class for the inep_media database table.
 * 
 */
@Entity
@Table( name = "inep_media", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepMedia.getAudios, query = "select o from InepMedia o where o.subscription = ?1 and o.type = 2" ),
		@NamedQuery( name = InepMedia.LookupForMediaName, query = "select o from InepMedia o where o.subscription.event = ?1 and o.media.name = ?2 "
				+ " order by o.subscription" )

} )
public class InepMedia extends BaseInepSubscription implements Serializable, Comparable<InepMedia>
{
	private static final long serialVersionUID = 1L;

	public static final String getAudios = "InepMedia.getAudios";
	public static final String LookupForMediaName = "InepMedia.LookupForMediaName";

	public static final Integer TYPE_TEST = 1;
	public static final Integer TYPE_AUDIO = 2;

	@EmbeddedId
	private InepMediaPK id;

	@Column( name = "tsk_id_in", nullable = true, updatable = true, insertable = true )
	private Integer task;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", updatable = false, insertable = false, nullable = false )
	private Media media;

	@Column( name = "imt_id_in", nullable = true, updatable = true, insertable = true, columnDefinition = "integer" )
	private Integer type;

	public InepMedia( )
	{
	}

	public InepMedia( InepSubscription parent )
	{
		this.getId( ).set( parent.getId( ) );
	}

	@Override
	public InepMediaPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepMediaPK( );
		}
		return this.id;
	}

	public void setId( InepMediaPK id )
	{
		this.id = id;
	}

	public Integer getTask( )
	{
		return this.task;
	}

	public void setTask( Integer tskIdIn )
	{
		this.task = tskIdIn;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		this.getId( ).setMediaId( media != null ? media.getId( ) : null );
	}

	@Override
	public int compareTo( InepMedia o )
	{
		if ( o == null ) {
			return -1;
		}

		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null ) {
			return false;
		}
		if ( obj instanceof InepMedia ) {
			InepMedia other = (InepMedia) obj;
			return this.getId( ).equals( other.getId( ) );
		}
		else if ( obj instanceof InepMediaPK ) {
			InepMediaPK other = (InepMediaPK) obj;
			return this.getId( ).equals( other );
		}
		return this.getId( ).equals( obj );
	}

	public Integer getType( )
	{
		return this.type;
	}

	public void setType( Integer type )
	{
		this.type = type;
	}
}