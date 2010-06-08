package br.com.mcampos.ejb.cloudsystem.user.document.session;


import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface UserDocumentSessionLocal extends Serializable
{
    /**<id>select o from UserDocument o where o.id = :document and o.documentType.formId = :docType</id>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param dto DTO com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find( UserDocumentDTO dto );

    /**<id>select o from UserDocument o where o.id = :document and o.documentType.formId = :docType</id>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param entity Entity Bean com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find( UserDocument entity );

}