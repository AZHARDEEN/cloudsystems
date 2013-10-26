package br.com.mcampos.controller.anoto.model;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.util.BaseComparator;

public class FormApplicationComparator extends BaseComparator
{
    public FormApplicationComparator( boolean bAsc )
    {
        super( bAsc );
    }

    public int compare( Object o1, Object o2 )
    {
        FormDTO d1 = (FormDTO)o1;
        FormDTO d2 = (FormDTO)o2;

        int nRet = d1.getApplication().compareToIgnoreCase( d2.getApplication() );
        if ( !isAscending() ) nRet *= -1;
        return nRet;
    }
}
