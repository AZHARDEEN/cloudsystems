package br.com.mcampos.ejb.cloudsystem.user.collaborator;


import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.CompanyUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollaboratorUtil
{
    public CollaboratorUtil()
    {
        super();
    }


    public static List<CompanyDTO> toCompanyDTOList( List<Collaborator> fromList )
    {
        if ( SysUtils.isEmpty( fromList ) )
            return Collections.emptyList();
        ArrayList<CompanyDTO> toList = new ArrayList<CompanyDTO>( fromList.size() );
        for ( Collaborator m : fromList ) {
            toList.add( CompanyUtil.copy( m.getCompany() ) );
        }
        return toList;
    }
}
