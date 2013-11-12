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
		if ( this.observerGrade == null ) {
			this.observerGrade = new Integer[ MAX_OBSERVER_GRADE ];
		}
		return this.observerGrade;
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
		this.getObserverGrade( )[ nIndex ] = value;
		this.setIsMising( false );
	}

	public Integer[ ] getElements( )
	{
		if ( this.elements == null ) {
			this.elements = new Integer[ MAX_ELEMENTS ];
		}
		return this.elements;
	}

	public void setElements( Integer[ ] elements )
	{
		this.elements = elements;
	}

	public Integer getInterviewerGrade( )
	{
		return this.interviewerGrade;
	}

	public void setInterviewerGrade( Integer interviewerGrade )
	{
		this.interviewerGrade = interviewerGrade;
		this.setIsMising( false );
	}

	public void set( int nIndex, String value )
	{
		if ( SysUtils.isEmpty( value ) ) {
			return;
		}
		switch ( nIndex ) {
		case 3:
			this.setSubscription( value );
			break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			this.setObserverGrade( nIndex - 4, Integer.valueOf( value ) );
			break;
		case 11:
			this.setInterviewerGrade( Integer.valueOf( value ) );
			break;
		}
	}

	public String getSubscription( )
	{
		return this.subscription;
	}

	public void setSubscription( String subscription )
	{
		this.subscription = subscription;
	}

	public Boolean getIsMising( )
	{
		return this.isMising;
	}

	public void setIsMising( Boolean isMising )
	{
		this.isMising = isMising;
	}

}
