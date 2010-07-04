package br.com.mcampos.ejb.session.user;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface UserSessionLocal extends Serializable
{
    Integer getRecordCount( AuthenticationDTO auth ) throws ApplicationException;

    List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, int firstResult, int maxResults ) throws ApplicationException;

    UserDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    Users get( Integer id ) throws ApplicationException;

    /*
     * Procura por um registro na tabela de documentos de usuarios (userdocuments).
     * O único truque desta funcao é testar se o mesmo usuario é o dono de todos os
     * documentos. Por exemplo: cpf x, identidade y e email z: x, y e z devem ser do mesmo usuário ou
     * uma exceção será lancada.
     * A interface para esta função é somente local, isto significa que somente dentro do
     * container poderá ser usada.
     *
     * @param list Lista de documentos a serem procurados, pois um usuário pode ter mais
     * de um documento.
     *
     * @exception ApplicationException
     * @return Users - Entity de usuario
     * @see br.com.mcampos.ejb.entity.user.Users
     *
     */

    Users findByDocumentList( List<UserDocumentDTO> list ) throws ApplicationException;


    /**
     * Procura por um usuario baseado em um documento do usuário ( CPF, ID...) específico
     *
     * @param dto DocumentTypeDTO
     * @return Users - Entity userSession.
     */
    Users getUserByDocument( UserDocumentDTO dto );

    /**
     * Procura por um usuario baseado em um documento do usuário ( CPF, ID...) específico
     *
     * @param entity DocumentTypeDTO
     * @return Users - Entity userSession.
     */
    Users getUserByDocument( UserDocument entity );
}
