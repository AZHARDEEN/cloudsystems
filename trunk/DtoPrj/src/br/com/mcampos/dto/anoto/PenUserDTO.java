package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

import java.util.Date;


public class PenUserDTO implements Serializable, Comparable<PenUserDTO>
{
    private Integer formId;
    private String penId;

    private ListUserDTO user;
    private Integer sequence;
    private Date fromDate;
    private Date toDate;


    public PenUserDTO()
    {
        super();
    }

    public void setPenPage( AnotoPenPageDTO penPage )
    {
        setPenId( penPage != null ? penPage.getPen().getId() : null );
        setFormId( penPage != null ? penPage.getFormId() : null );
    }

    public void setUser( ListUserDTO user )
    {
        this.user = user;
    }

    public ListUserDTO getUser()
    {
        return user;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setFromDate( Date fromDate )
    {
        this.fromDate = fromDate;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setToDate( Date toDate )
    {
        this.toDate = toDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setFormId( Integer formId )
    {
        this.formId = formId;
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setPenId( String penId )
    {
        this.penId = penId;
    }

    public String getPenId()
    {
        return penId;
    }

    public int compareTo( PenUserDTO o )
    {
        return getPenId().compareTo( o.getPenId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        return getPenId().equals( ( ( PenUserDTO )obj ).getPenId() );
    }
}
