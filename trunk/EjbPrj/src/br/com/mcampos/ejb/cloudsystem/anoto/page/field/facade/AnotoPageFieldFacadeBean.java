package br.com.mcampos.ejb.cloudsystem.anoto.page.field.facade;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.session.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.session.FieldTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoPageFieldFacade", mappedName = "CloudSystems-EjbPrj-AnotoPageFieldFacade" )
@TransactionAttribute( value = TransactionAttributeType.REQUIRES_NEW )
public class AnotoPageFieldFacadeBean extends AbstractSecurity implements AnotoPageFieldFacade
{
    protected static final int SystemMessageTypeId = 33;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private AnotoFormSessionLocal formSession;

    @EJB
    private PageFieldSessionLocal fieldSession;

    @EJB
    private FieldTypeSessionLocal fieldTypeSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        return AnotoUtils.toFormList( formSession.getAll( company ) );
    }

    public List<AnotoPageFieldDTO> getFields( AuthenticationDTO auth, Integer formId ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm form = formSession.get( formId );
        return AnotoUtils.toAnotoPageFieldDTO( fieldSession.getAll( form ) );
    }

    public List<FieldTypeDTO> getFieldTypes() throws ApplicationException
    {
        return fieldTypeSession.toDTOList( fieldTypeSession.getAll() );
    }

    public void update( AuthenticationDTO auth, AnotoPageFieldDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPageField entity = DTOFactory.copy( dto );
        fieldSession.update( entity );
    }
}
