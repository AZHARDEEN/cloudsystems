package br.com.mcampos.controller.anoto.model;

import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.util.BaseComparator;

public class FormDescriptionApplication extends BaseComparator
{
    public FormDescriptionApplication( boolean b )
    {
        super( b );
    }

    public int compare( Object o1, Object o2 )
    {
        FormDTO d1 = (FormDTO)o1;
        FormDTO d2 = (FormDTO)o2;

        int nRet = d1.getDescription().compareToIgnoreCase( d2.getDescription() );
        if ( !isAscending() ) nRet *= -1;
        return nRet;
    }
}
