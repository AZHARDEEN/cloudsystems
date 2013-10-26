package br.com.mcampos.ejb.cloudsystem.user.company.role.entity;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = CompanyRole.getAll, query = "select o from CompanyRole o where o.company = ?1" ) } )
@Table( name = "company_role" )
@IdClass( CompanyRolePK.class )
public class CompanyRole implements Serializable
{
    public static final String getAll = "CompanyRole.findAll";

    @Id
    @Column( name = "rol_id_in", nullable = false, insertable = false, updatable = false )
    private Integer roleId;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "rol_id_in", nullable = false, insertable = true, updatable = true )
    private Role role;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, insertable = true, updatable = true )
    private Company company;

    public CompanyRole()
    {
    }

    public CompanyRole( Role role, Company company )
    {
        setRole( role );
        setCompany( company );
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

    public void setRole( Role role )
    {
        this.role = role;
        setRoleId( this.role != null ? this.role.getId() : null );
    }

    public Role getRole()
    {
        return role;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        setCompanyId( this.company != null ? this.company.getId() : null );
    }

    public Company getCompany()
    {
        return company;
    }
}
