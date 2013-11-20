package br.com.mcampos.dto.inep;

import java.io.Serializable;

import br.com.mcampos.sysutils.SysUtils;

public class StationGradeDTO implements Serializable
{
	private static final long serialVersionUID = 695389858869111266L;
	public static final Integer MAX_OBSERVER_GRADE = 6;
	public static final Integer MAX_ELEMENTS = 3;

	private String subscription;

	private Integer[ ] observerGrade;
	private Integer[ ] elements;
	private Integer interviewerGrade;

	private Boolean isMising = true;

	public Integer[ ] getObserverGrade( )
	{
		if ( observerGrade == null ) {
			observerGrade = new Integer[ MAX_OBSERVER_GRADE ];
		}
		return observerGrade;
	}

	public void setObserverGrade( Integer[ ] observerGrade )
	{
		this.observerGrade = observerGrade;
	}

	public void setObserverGrade( int nIndex, int value )
	{
		if ( nIndex < 0 || nIndex > 5 ) {
			throw new IndexOutOfBoundsException( "Observer Grade " + nIndex + " out of bouds" );
		}
		getObserverGrade( )[ nIndex ] = value;
		setIsMising( false );
	}

	public Integer[ ] getElements( )
	{
		if ( elements == null ) {
			elements = new Integer[ MAX_ELEMENTS ];
		}
		return elements;
	}

	public void setElements( Integer[ ] elements )
	{
		this.elements = elements;
	}

	public Integer getInterviewerGrade( )
	{
		return interviewerGrade;
	}

	public void setInterviewerGrade( Integer interviewerGrade )
	{
		this.interviewerGrade = interviewerGrade;
		setIsMising( false );
	}

	public void set( int nIndex, String value )
	{
		if ( SysUtils.isEmpty( value ) ) {
			return;
		}
		switch ( nIndex ) {
		case 2:
			setSubscription( value );
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			this.setObserverGrade( nIndex - 3, Integer.valueOf( value ) );
			break;
		case 10:
			setInterviewerGrade( Integer.valueOf( value ) );
			break;
		}
	}

	public String getSubscription( )
	{
		return subscription;
	}

	public void setSubscription( String subscription )
	{
		this.subscription = subscription;
	}

	public Boolean getIsMising( )
	{
		return isMising;
	}

	public void setIsMising( Boolean isMising )
	{
		this.isMising = isMising;
	}

	public boolean compareGrades( StationGradeDTO other )
	{
		if( other == null ) {
			return false;
		}
		if( !getSubscription( ).equals( other.getSubscription( ) ) ) {
			return false;
		}
		if( !getIsMising( ).equals( other.getIsMising( ) ) ) {
			return false;
		}
		if( getIsMising( ) ) {
			return true;
		}
		if( !getInterviewerGrade( ).equals( other.getInterviewerGrade( ) ) ) {
			return false;
		}
		for( int nIndex = 0; nIndex < getObserverGrade( ).length; nIndex++ ) {
			if( !getObserverGrade( )[nIndex].equals( other.getObserverGrade( )[nIndex] ) ) {
				return false;
			}
		}
		return true;
	}

	public String export ( )
	{
		String out = String.format( "%s;%d;%d;%d;%d;%d;%d;%.02f;%d;%.02f;%.02f",
				getSubscription( ),
				getObserverGrade( )[0],
				getObserverGrade( )[1],
				getObserverGrade( )[2],
				getObserverGrade( )[3],
				getObserverGrade( )[4],
				getObserverGrade( )[5],
				0D,
				getInterviewerGrade( ),
				0D, 0D );
		return out;
	}

}
