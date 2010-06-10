package br.com.mcampos.ejb.cloudsystem.user.company.facade;


import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.CompanyUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CompanyFacade", mappedName = "CloudSystems-EjbPrj-CompanyFacade" )
@Remote
public class CompanyFacadeBean extends UserFacadeUtil implements CompanyFacade
{
    public static final Integer messageId = 25;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private CompanyTypeSessionLocal companyTypeSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }


    private void update( Company company, CompanyDTO dto ) throws ApplicationException
    {
        CompanyUtil.update( company, dto );
        company = companySession.update( company );
        refreshUserAttributes( company, dto );
    }

    public List<CompanyTypeDTO> getCompanyTypes() throws ApplicationException
    {
        List list = companyTypeSession.getAll();
        return CompanyTypeUtil.toDTOList( list );
    }
}
