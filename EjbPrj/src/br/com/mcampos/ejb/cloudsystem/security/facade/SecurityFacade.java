package br.com.mcampos.ejb.cloudsystem.security.facade;


import javax.ejb.Remote;


@Remote
public interface SecurityFacade extends TaskInterface, RoleInterface, PermissionAssignmentInterface, TaskMenuInterface
{
}
