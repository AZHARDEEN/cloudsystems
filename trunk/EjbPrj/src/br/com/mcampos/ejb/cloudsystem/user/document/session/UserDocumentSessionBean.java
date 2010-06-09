package br.com.mcampos.ejb.cloudsystem.user.document.session;


import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.session.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocumentPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.Query;


@Stateless( name = "UserDocumentSession", mappedName = "CloudSystems-EjbPrj-UserDocumentSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserDocumentSessionBean extends Crud<UserDocumentPK, UserDocument> implements UserDocumentSessionLocal
{
    @EJB
    DocumentTypeSessionLocal docmentTypeSession;


    /**<id>select o from UserDocument o where o.id = :document and o.documentType.formId = :docType</id>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param dto DTO com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    public UserDocument find( UserDocumentDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException( "O parametro nao pode ser nulo ou vazio " );
        return find( dto.getCode(), dto.getDocumentType().getId() );
    }


    /**<id>select o from UserDocument o where o.id = :document and o.documentType.formId = :docType</id>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param entity Entity Bean com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    public UserDocument find( UserDocument entity )
    {
        if ( entity == null )
            throw new InvalidParameterException( "O parametro nao pode ser nulo ou vazio " );
        return find( entity.getCode(), entity.getDocumentType().getId() );
    }


    /**<id>select o from UserDocument o where o.id = :document and o.documentType.formId = :docType</id>
     *
     * Este é a função privada que realiza a busca por um documento de usuario no banco de dados.
     *
     * @param document Código (Número, ID) do documento
     * @param docTypeId Código sequencial inteiro do tipo do documento
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    protected UserDocument find( String document, Integer docTypeId )
    {
        UserDocument doc = null;

        if ( SysUtils.isEmpty( document ) || docTypeId == null )
            throw new InvalidParameterException( "O parametro nao pode ser nulo ou vazio " );

        try {
            Query query = getEntityManager().createNamedQuery( UserDocument.getByDocument );
            query.setParameter( "document", document );
            query.setParameter( "docType", docTypeId );
            doc = ( UserDocument )query.getSingleResult();
        }
        catch ( Exception e ) {
            e.printStackTrace();
            e = null;
        }
        return doc;
    }

    public void delete( Users user ) throws ApplicationException
    {
        Query query = getEntityManager().createNamedQuery( UserDocument.deleteFromUser );
        query.setParameter( 1, user ).executeUpdate();
    }

    @Override
    public UserDocument add( UserDocument entity ) throws ApplicationException
    {
        if ( entity.getDocumentType() != null )
            entity.setDocumentType( docmentTypeSession.get( entity.getDocumentType().getId() ) );
        entity = super.add( entity );
        entity.getUser().addDocument( entity );
        return entity;
    }
}
