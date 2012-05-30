package br.com.mcampos.ejb.cloudsystem.product.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

public class ProductPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public ProductPK()
    {
    }

    public ProductPK( Integer productId, Integer ownerId )
    {
        this.id = productId;
        this.companyId = ownerId;
    }

    public ProductPK( Company company, Integer productId )
    {
        setId( productId );
        setCompanyId( company.getId() );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof ProductPK ) {
            final ProductPK otherProductPK = ( ProductPK )other;
            final boolean areEqual = ( otherProductPK.id.equals( id ) && otherProductPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer prd_id_in )
    {
        this.id = prd_id_in;
    }

    Integer getCompanyId()
    {
        return companyId;
    }

    void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
