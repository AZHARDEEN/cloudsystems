package br.com.mcampos.jpa.inep;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import br.com.mcampos.jpa.BaseCompanyEntity;

@MappedSuperclass
public abstract class BaseInepEvent extends BaseCompanyEntity
{
	private static final long serialVersionUID = 8156819802714513039L;
	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn( name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false )
	} )
	private InepEvent event;

	@Override
	public abstract BaseInepEventPK getId( );

	public BaseInepEvent( )
	{

	}

	public BaseInepEvent( @NotNull InepEvent evt )
	{
		super( evt.getCompany( ) );
	}

	public InepEvent getEvent( )
	{
		return event;
	}

	public void setEvent( InepEvent event )
	{
		this.event = event;
		if ( event != null ) {
			setCompany( event.getCompany( ) );
			getId( ).setEventId( event.getId( ).getId( ) );
		}
		else {
			setCompany( null );
			getId( ).setEventId( null );

		}
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj != null ) {
			return getId( ).equals( ( (BaseInepEvent) obj ).getId( ) );
		}
		else {
			return false;
		}
	}

	public int compareTo( BaseInepEvent other )
	{
		return getId( ).compareTo( other.getId( ) );
	}

}
