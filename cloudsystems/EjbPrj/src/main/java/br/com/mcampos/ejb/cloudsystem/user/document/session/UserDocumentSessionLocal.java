package br.com.mcampos.ejb.cloudsystem.user.document.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface UserDocumentSessionLocal extends Serializable
{
    /**<penPageSequence>select o from UserDocument o where o.penPageSequence = :document and o.documentType.formId = :docType</penPageSequence>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param dto DTO com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find( UserDocumentDTO dto );

    UserDocument findEmail( String email );

    UserDocument findCPF( String cpf );

    UserDocument findCNPJ( String cnpj );

    /**<penPageSequence>select o from UserDocument o where o.penPageSequence = :document and o.documentType.formId = :docType</penPageSequence>
     *
     * tenta localizar um registro no banco de dados com os dados do documento e o tipo do documento
     *
     * @param entity Entity Bean com os dados a serem localizados no banco de dados
     * @return UserDocument Entity Bean
     * @exception InvalidParameterException
     */
    UserDocument find( UserDocument entity );


    void delete( Users user ) throws ApplicationException;

    UserDocument add( UserDocument doc ) throws ApplicationException;

    void refresh( Users user, List<UserDocument> documents ) throws ApplicationException;
}
