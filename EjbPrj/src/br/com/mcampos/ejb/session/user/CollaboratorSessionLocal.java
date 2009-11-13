package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

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
    
    Long getBusinessEntityCount( Integer personId );
    
    UserDTO getBusinessEntity( Integer businessId, Integer currentUserId );
    
    List<ListUserDTO> getBusinessEntityByRange( Integer currentUserId, int firstResult, int maxResults );
}
