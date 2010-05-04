package br.com.mcampos.dto.user;

import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;

public class CompanyDTO extends UserDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7380523821618482931L;
	private CompanyTypeDTO companyType;
    
    

    public CompanyDTO()
    {
        super();
    }

    public void setCompanyType( CompanyTypeDTO companyType )
    {
        this.companyType = companyType;
    }

    public CompanyTypeDTO getCompanyType()
    {
        return companyType;
    }
}
