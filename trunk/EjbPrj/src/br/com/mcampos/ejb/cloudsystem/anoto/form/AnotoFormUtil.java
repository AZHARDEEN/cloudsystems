package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnotoFormUtil
{
    public static AnotoForm createEntity( FormDTO source )
    {
        if ( source == null )
            return null;

        AnotoForm target = new AnotoForm( source.getId(), source.getApplication().trim() );
        return update( target, source );
    }


    public static AnotoForm update( AnotoForm target, FormDTO source )
    {
        if ( source == null || target == null )
            return null;

        target.setDescription( source.getDescription().trim() );
        target.setIcrImage( source.getIcrImage() );
        target.setImagePath( source.getImagePath() );
        target.setConcatenatePgc( source.getConcatenatePgc() );
        return target;
    }


    public static FormDTO copy( AnotoForm entity )
    {
        FormDTO dto = new FormDTO( entity.getId() );
        dto.setApplication( entity.getApplication() );
        dto.setConcatenatePgc( entity.getConcatenatePgc() );
        dto.setIcrImage( dto.getIcrImage() );
        dto.setImagePath( dto.getImagePath() );
        return dto;
    }


    public static List<ListUserDTO> toListUserDTO( List<AnotoFormUser> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<ListUserDTO> dtos = new ArrayList<ListUserDTO>( list.size() );
        for ( AnotoFormUser item : list )
            dtos.add( UserUtil.copy( item.getCompany() ) );
        return dtos;
    }
}
