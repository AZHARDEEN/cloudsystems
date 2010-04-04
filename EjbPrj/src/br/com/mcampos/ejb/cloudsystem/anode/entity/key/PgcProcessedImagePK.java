package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import java.io.Serializable;

public class PgcProcessedImagePK implements Serializable
{
    private Integer med_id_in;
    private Integer pgc_id_in;
    private Integer ppi_book_id;
    private Integer ppi_page_id;

    public PgcProcessedImagePK()
    {
    }

    public PgcProcessedImagePK( Integer med_id_in, Integer pgc_id_in,
                                Integer ppi_book_id, Integer ppi_page_id )
    {
        this.med_id_in = med_id_in;
        this.pgc_id_in = pgc_id_in;
        this.ppi_book_id = ppi_book_id;
        this.ppi_page_id = ppi_page_id;
    }

    public boolean equals( Object other )
    {
        if (other instanceof PgcProcessedImagePK) {
            final PgcProcessedImagePK otherPgcProcessedImagePK = (PgcProcessedImagePK) other;
            final boolean areEqual =
                (otherPgcProcessedImagePK.med_id_in.equals(med_id_in) && otherPgcProcessedImagePK.pgc_id_in.equals(pgc_id_in) && otherPgcProcessedImagePK.ppi_book_id.equals(ppi_book_id) && otherPgcProcessedImagePK.ppi_page_id.equals(ppi_page_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMed_id_in()
    {
        return med_id_in;
    }

    void setMed_id_in( Integer med_id_in )
    {
        this.med_id_in = med_id_in;
    }

    Integer getPgc_id_in()
    {
        return pgc_id_in;
    }

    void setPgc_id_in( Integer pgc_id_in )
    {
        this.pgc_id_in = pgc_id_in;
    }

    Integer getPpi_book_id()
    {
        return ppi_book_id;
    }

    void setPpi_book_id( Integer ppi_book_id )
    {
        this.ppi_book_id = ppi_book_id;
    }

    Integer getPpi_page_id()
    {
        return ppi_page_id;
    }

    void setPpi_page_id( Integer ppi_page_id )
    {
        this.ppi_page_id = ppi_page_id;
    }
}
