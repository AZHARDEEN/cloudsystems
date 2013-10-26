package br.com.mcampos.ejb.cloudsystem.user.collaborator;


import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
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

    public static List<CollaboratorDTO> toDTOList( List<Collaborator> fromList )
    {
        if ( SysUtils.isEmpty( fromList ) )
            return Collections.emptyList();
        ArrayList<CollaboratorDTO> toList = new ArrayList<CollaboratorDTO>( fromList.size() );
        for ( Collaborator m : fromList ) {
            toList.add( copy( m ) );
        }
        return toList;
    }


    public static CollaboratorDTO copy( Collaborator entity )
    {
        CollaboratorDTO dto = new CollaboratorDTO();

        dto.setCompany( UserUtil.copy( entity.getCompany() ) );
        dto.setCollaborator( UserUtil.copy( entity.getPerson() ) );
        dto.setSequence( entity.getSequence() );
        return dto;
    }
}
