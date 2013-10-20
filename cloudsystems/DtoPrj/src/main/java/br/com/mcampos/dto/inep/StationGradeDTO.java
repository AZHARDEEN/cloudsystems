package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class StationGradeDTO implements Serializable
{
	private static final long serialVersionUID = 695389858869111266L;

	private Integer[ ] observerGrade;
	private Integer[ ] elements;
	private Integer interviewerGrade;

	public static final Integer MAX_OBSERVER_GRADE = 6;
	public static final Integer MAX_ELEMENTS = 3;

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
	}

}
