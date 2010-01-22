package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.entity.user.UserDocument;

import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless( name = "UserDocumentSession", mappedName = "CloudSystems-EjbPrj-UserDocumentSession" )
public class UserDocumentSessionBean implements UserDocumentSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public UserDocumentSessionBean ()
    {
    }

    /**<code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code>
     * 
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     * 
     * @param dto DTO com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    public UserDocument find ( UserDocumentDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException ( "O parametro nao pode ser nulo ou vazio " );
        return find ( dto.getCode(), dto.getDocumentType().getId() );
    }


    /**<code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code>
     * 
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     * 
     * @param entity Entity Bean com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    public UserDocument find ( UserDocument entity )
    {
        if ( entity == null )
            throw new InvalidParameterException ( "O parametro nao pode ser nulo ou vazio " );
        return find ( entity.getCode(), entity.getDocumentType().getId() );
    }
    
    
    /**<code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code>
     * 
     * Este é a função privada que realiza a busca por um documento de usuario no banco de dados.
     * 
     * @param document Código (Número, ID) do documento
     * @param docTypeId Código sequencial inteiro do tipo do documento
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    protected UserDocument find ( String document, Integer docTypeId )
    {
        if ( SysUtils.isEmpty( document ) || docTypeId == null )
            throw new InvalidParameterException ( "O parametro nao pode ser nulo ou vazio " );
        
        try {
            return ( UserDocument ) em.createNamedQuery("UserDocument.findDocument")
                .setParameter("document", document )
                .setParameter("docType", docTypeId ).getSingleResult();
        }
        catch ( NoResultException e ) 
        {
            e = null;
            return null;
        }
    }

}
