package br.com.mcampos.ejb.cloudsystem.anoto.form.user;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AnotoFormUserUtil
{
    public static List<AnotoForm> toListAnotoForm( List<AnotoFormUser> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoForm> dtos = new ArrayList<AnotoForm>( list.size() );
        for ( AnotoFormUser item : list )
            dtos.add( item.getForm() );
        return dtos;
    }
}
