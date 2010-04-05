package br.com.mcampos.dto.anoto;

import java.io.Serializable;

public class AnotoResultList implements Comparable<AnotoResultList>, Serializable
{
    FormDTO form;
    PenDTO pen;
    PgcPageDTO pgcPage;

    public AnotoResultList()
    {
        super();
    }

    public AnotoResultList( FormDTO form, PenDTO pen, PgcPageDTO pgcPage )
    {
        super();
        setForm( form );
        setPen ( pen );
        setPgcPage( pgcPage );
    }


    public int compareTo( AnotoResultList o )
    {
        int nRet;

        nRet = getForm().compareTo( o.getForm () );
        if ( nRet != 0 )
            return nRet;
        nRet = getPen().compareTo( o.getPen() );
        if ( nRet != 0)
            return nRet;
        return getPgcPage().compareTo( o.getPgcPage() );
    }

    public void setForm( FormDTO form )
    {
        this.form = form;
    }

    public FormDTO getForm()
    {
        return form;
    }

    public void setPen( PenDTO pen )
    {
        this.pen = pen;
    }

    public PenDTO getPen()
    {
        return pen;
    }

    @Override
    public boolean equals( Object obj )
    {
        AnotoResultList other = (AnotoResultList)obj;
        return ( getForm().equals( other.getForm() ) && getPgcPage().equals( other.getPgcPage() ) && getPen().equals( other.getPen() ) );
    }

    public void setPgcPage( PgcPageDTO pgcPage )
    {
        this.pgcPage = pgcPage;
    }

    public PgcPageDTO getPgcPage()
    {
        return pgcPage;
    }
}
