package br.com.mcampos.ejb.cloudsystem.user.company.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.session.UserTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CompanySession", mappedName = "CloudSystems-EjbPrj-CompanySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanySessionBean extends Crud<Integer, Company> implements CompanySessionLocal
{
    @EJB
    private UserDocumentSessionLocal documentSession;

    @EJB
    UserTypeSessionLocal userTypeSession;

    @EJB
    CompanyTypeSessionLocal companyType;


    @Override
    public Company get( Integer key ) throws ApplicationException
    {
        return get( Company.class, key );
    }

    @Override
    public Company add( Company entity ) throws ApplicationException
    {
        entity.setUserType( userTypeSession.get( 2 ) );
        if ( entity.getCompanyType() != null )
            entity.setCompanyType( companyType.get( entity.getCompanyType().getId() ) );
        return super.add( entity );
    }

    @Override
    public Company update( Company entity ) throws ApplicationException
    {
        entity.setUserType( userTypeSession.get( 2 ) );
        return super.update( entity );
    }

    public Company get( Company company ) throws ApplicationException
    {
        /*
         * We expeted a not managed entity company.
         */
        if ( company == null )
            return null;

        if ( company.getId() != null )
            return get( company.getId() );
        /*
         * We do not have an Id, so let's figure out if we have a document
         */
        for ( UserDocument doc : company.getDocuments() ) {
            UserDocument d = documentSession.find( doc );
            if ( d != null )
                return ( Company )d.getUser();
        }
        return null;
    }
}
