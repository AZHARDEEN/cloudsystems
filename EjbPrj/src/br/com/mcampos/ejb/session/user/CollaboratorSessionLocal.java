package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CollaboratorSessionLocal
{
    CollaboratorType persistCollaboratorType( CollaboratorType collaboratorType );

    CollaboratorType mergeCollaboratorType( CollaboratorType collaboratorType );

    void removeCollaboratorType( CollaboratorType collaboratorType );

    List<CollaboratorType> getCollaboratorTypeByCriteria( String jpqlStmt, int firstResult, int maxResults );

    List<CollaboratorType> getCollaboratorTypeFindAll();

    List<CollaboratorType> getCollaboratorTypeFindAllByRange( int firstResult, int maxResults );

    Collaborator persistCollaborator( Collaborator collaborator );

    Collaborator mergeCollaborator( Collaborator collaborator );

    void removeCollaborator( Collaborator collaborator );

    List<Collaborator> getCollaboratorByCriteria( String jpqlStmt, int firstResult, int maxResults );

    List<Collaborator> getCollaboratorFindAll();

    List<Collaborator> getCollaboratorFindAllByRange( int firstResult, int maxResults );

    Integer getCollaboratorHasManager( Object companyId );

    Boolean getCollaboratorIsManager( Object companyId, Object personId );

    List<Collaborator> getCollaboratorHasCollaborator( Object companyId, Object personId );

    List<Collaborator> getCompanies( Integer personId );

    Integer getBusinessEntityCount( AuthenticationDTO auth ) throws ApplicationException;

    UserDTO getBusinessEntity( AuthenticationDTO auth, Integer businessId ) throws ApplicationException;

    List<ListUserDTO> getBusinessEntityByRange( AuthenticationDTO auth, int firstResult, int maxResults );


    /*
     * Esta funcao foi adicionada manualmente.
     * Não existe a necessidade de apagá-la (Refactor)
     */

    /**
     * Obtem todas as roles do usuário autenticado.
     * As roles são a base para todo o esquema de segurança do sistema.
     * Inclusive para obter o menu de acesso ao sistema.
     *
     * @param auth DTO do usuário autenticado.
     * @return A lista de roles do usuário ou null.
     */
    List<Role> getRoles( AuthenticationDTO auth );

    List<MenuDTO> getMenuList( AuthenticationDTO auth, Integer companyId );
}
