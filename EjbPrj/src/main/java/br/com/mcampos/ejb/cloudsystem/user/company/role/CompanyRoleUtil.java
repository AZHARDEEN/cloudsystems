package br.com.mcampos.ejb.cloudsystem.user.company.role;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRole;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CompanyRoleUtil
{
    public static List<Role> toRoleList( List<CompanyRole> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<Role> listDTO = new ArrayList<Role>( list.size() );
        for ( CompanyRole m : list ) {
            listDTO.add( m.getRole() );
        }
        return listDTO;
    }
}
