package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;
import br.com.mcampos.ejb.entity.security.PermissionAssignmentPK;
import br.com.mcampos.ejb.session.core.Crud;

import javax.ejb.Stateless;

@Stateless( name = "PermissionAssignmentSession", mappedName = "CloudSystems-EjbPrj-PermissionAssignmentSession" )
public class PermissionAssignmentSessionBean extends Crud<PermissionAssignmentPK, PermissionAssignment > implements PermissionAssignmentSessionLocal
{
    public PermissionAssignmentSessionBean()
    {
    }

    public void add( Role role, Task task )
    {
        PermissionAssignment entity = new PermissionAssignment ( role, task );
        getEntityManager().persist( entity );
    }
}
