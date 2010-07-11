package br.com.mcampos.dto.anoto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


public class AnotoResultList implements Comparable<AnotoResultList>, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -3719897012683155546L;
    private FormDTO form;
    private PenDTO pen;
    private PgcPageDTO pgcPage;
    private String userName;
    private String barcodeValue;
    private String email;
    private String cellNumber;
    private String latitude;
    private String longitude;
    private Boolean attach;

    private List<PgcFieldDTO> fields = new ArrayList<PgcFieldDTO>();

    public AnotoResultList()
    {
        super();
    }

    public AnotoResultList( FormDTO form, PenDTO pen, PgcPageDTO pgcPage )
    {
        super();
        setForm( form );
        setPen( pen );
        setPgcPage( pgcPage );
    }


    public int compareTo( AnotoResultList o )
    {
        int nRet;

        nRet = getForm().compareTo( o.getForm() );
        if ( nRet != 0 )
            return nRet;
        nRet = getPen().compareTo( o.getPen() );
        if ( nRet != 0 )
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
        AnotoResultList other = ( AnotoResultList )obj;
        return ( getForm().equals( other.getForm() ) && getPgcPage().equals( other.getPgcPage() ) && getPen()
                 .equals( other.getPen() ) );
    }

    public void setPgcPage( PgcPageDTO pgcPage )
    {
        this.pgcPage = pgcPage;
    }

    public PgcPageDTO getPgcPage()
    {
        return pgcPage;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setBarcodeValue( String barcodeValue )
    {
        this.barcodeValue = barcodeValue;
    }

    public String getBarcodeValue()
    {
        return barcodeValue;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setCellNumber( String cellNumber )
    {
        this.cellNumber = cellNumber;
    }

    public String getCellNumber()
    {
        return cellNumber;
    }

    public void setLatitude( String latitude )
    {
        this.latitude = latitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLongitude( String longitude )
    {
        this.longitude = longitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setAttach( Boolean attach )
    {
        this.attach = attach;
    }

    public Boolean getAttach()
    {
        return attach;
    }

    public List<PgcFieldDTO> getFields()
    {
        return fields;
    }
}
