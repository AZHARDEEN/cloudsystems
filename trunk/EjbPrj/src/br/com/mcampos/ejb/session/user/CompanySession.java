package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.CompanyDTO;

import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;

import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.entity.user.Company;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CompanySession
{
    Company add ( CompanyDTO dto );
    Company update ( CompanyDTO dto );
    Boolean isManager( Integer companyId, Integer personId );
    Boolean hasManagers ( Integer companyId );


    Company addBusinessEntity ( CompanyDTO dto, LoginDTO login );
    Company updateBusinessEntity ( CompanyDTO dto, LoginDTO login );
    
    void removeCollaborator ( Integer companyId, Integer personId );
    void addCollaborator ( Integer companyId, Integer personId, Integer collaboratorType );
}
