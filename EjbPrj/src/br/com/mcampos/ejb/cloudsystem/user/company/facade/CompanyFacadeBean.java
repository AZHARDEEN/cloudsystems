package br.com.mcampos.ejb.cloudsystem.user.company.facade;


import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeUtil;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CompanyFacade", mappedName = "CloudSystems-EjbPrj-CompanyFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CompanyFacadeBean extends UserFacadeUtil implements CompanyFacade
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2924864980663940995L;

	public static final Integer messageId = 25;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

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

    /*
    private void update( Company company, CompanyDTO dto ) throws ApplicationException
    {
        CompanyUtil.update( company, dto );
        company = companySession.update( company );
        refreshUserAttributes( company, dto );
    }
    */

    public List<CompanyTypeDTO> getCompanyTypes() throws ApplicationException
    {
        List<CompanyType> list = companyTypeSession.getAll();
        return CompanyTypeUtil.toDTOList( list );
    }
}
