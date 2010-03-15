package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CollaboratorSessionLocal
{
    Integer getBusinessEntityCount( AuthenticationDTO auth ) throws ApplicationException;

    UserDTO getBusinessEntity( AuthenticationDTO auth, Integer businessId ) throws ApplicationException;

    List<ListUserDTO> getBusinessEntityByRange( AuthenticationDTO auth, int firstResult,
                                                int maxResults ) throws ApplicationException;

    List<MenuDTO> getMenuList( AuthenticationDTO auth ) throws ApplicationException;
}
