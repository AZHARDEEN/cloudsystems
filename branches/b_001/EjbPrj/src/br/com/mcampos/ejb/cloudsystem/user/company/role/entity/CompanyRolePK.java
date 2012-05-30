package br.com.mcampos.ejb.cloudsystem.user.company.role.entity;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

public class CompanyRolePK implements Serializable
{
    private Integer roleId;
    private Integer companyId;

    public CompanyRolePK()
    {
    }

    public CompanyRolePK( Integer rol_id_in, Integer usr_id_in )
    {
        setRoleId( rol_id_in );
        setCompanyId( usr_id_in );
    }

    public CompanyRolePK( Role role, Company company )
    {
        setRoleId( role.getId() );
        setCompanyId( company.getId() );
    }


    public boolean equals( Object other )
    {
        if ( other instanceof CompanyRolePK ) {
            final CompanyRolePK otherCompanyRolePK = ( CompanyRolePK )other;
            final boolean areEqual =
                ( otherCompanyRolePK.roleId.equals( roleId ) && otherCompanyRolePK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
