package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.entity.user.UserDocument;

import br.com.mcampos.ejb.entity.user.Users;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserDocumentSessionLocal
{
    /**<code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code>
     * 
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     * 
     * @param dto DTO com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find ( UserDocumentDTO dto );

    /**<code>select o from UserDocument o where o.code = :document and o.documentType.id = :docType</code>
     * 
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     * 
     * @param entity Entity Bean com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find ( UserDocument entity );
    
}
