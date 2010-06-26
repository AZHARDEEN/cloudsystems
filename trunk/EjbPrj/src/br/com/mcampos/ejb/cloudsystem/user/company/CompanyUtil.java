package br.com.mcampos.ejb.cloudsystem.user.company;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.UserTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

public class CompanyUtil extends UserUtil
{
    public CompanyUtil()
    {
        super();
    }

    public static Company createEntity( CompanyDTO dto )
    {
        if ( dto == null )
            return null;
        return update( new Company(), dto );
    }

    public static Company update( Company company, CompanyDTO dto )
    {
        update( ( ( Users )company ), ( ( UserDTO )dto ) );
        company.setUserType( UserTypeUtil.createEntity( dto.getUserType() ) );
        company.setCompanyType( CompanyTypeUtil.createEntity( dto.getCompanyType() ) );
        return company;
    }


    public static Company copy( RegisterDTO dto )
    {
        Company Company;

        Company = new Company();
        Company.setName( dto.getName() );
        addDocuments( Company, dto );
        return Company;
    }

    public static CompanyDTO copy( Company company )
    {
        CompanyDTO dto = new CompanyDTO();
        dto.setCompanyType( CompanyTypeUtil.copy( company.getCompanyType() ) );
        update( ( UserDTO )dto, ( Users )company );
        return dto;
    }
}
