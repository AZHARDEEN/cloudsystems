package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.entity.user.UserDocument;

import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "UserDocumentSession", mappedName = "CloudSystems-EjbPrj-UserDocumentSession" )
public class UserDocumentSessionBean implements UserDocumentSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public UserDocumentSessionBean ()
    {
    }

    /** <code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code> */
    public UserDocument find ( UserDocumentDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException ( "O parametro nao pode ser nulo ou vazio " );
        if ( SysUtils.isEmpty( dto.getCode() ) )
            throw new InvalidParameterException ( "O codigo do documento nao pode ser vazio" );
        
        return ( UserDocument ) em.createNamedQuery("UserDocument.findDocument")
            .setParameter("document", dto.getCode())
            .setParameter("docType", dto.getDocumentType().getId() ).getSingleResult();
    }
}
