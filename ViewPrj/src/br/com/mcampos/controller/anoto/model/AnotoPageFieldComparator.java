package br.com.mcampos.controller.anoto.model;

import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.util.BaseComparator;

public class AnotoPageFieldComparator extends BaseComparator
{
    public static final int headerName = 1;
    public static final int headerType = 2;
    public static final int headerIcr = 3;

    private Integer type;

    public AnotoPageFieldComparator( boolean b, Integer type )
    {
        super( b );
        this.type = type;
    }

    public AnotoPageFieldComparator()
    {
        super();
    }

    public int compare( Object o1, Object o2 )
    {
        AnotoPageFieldDTO d1 = ( AnotoPageFieldDTO )o1;
        AnotoPageFieldDTO d2 = ( AnotoPageFieldDTO )o2;
        int nRet = 0;

        switch ( type ) {
        case headerName: nRet = d1.getName().compareTo( d2.getName() );
            break;
        case headerType: nRet = d1.getType().compareTo( d2.getType() );
            break;
        case headerIcr: nRet = d1.getIcr().compareTo( d2.getIcr() );
            break;
        }
        if ( isAscending() == false )
            nRet *= -1;
        return nRet;
    }
}
