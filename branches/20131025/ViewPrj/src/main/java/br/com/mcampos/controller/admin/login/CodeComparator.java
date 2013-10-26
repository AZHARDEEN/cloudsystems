package br.com.mcampos.controller.admin.login;

import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.util.BaseComparator;

public class CodeComparator extends BaseComparator
{
    public CodeComparator( boolean b )
    {
        super( b );
    }

    public CodeComparator()
    {
        super();
    }

    public int compare( Object o1, Object o2 )
    {
        ListLoginDTO d1 = ( ListLoginDTO )o1;
        ListLoginDTO d2 = ( ListLoginDTO )o2;

        if ( isAscending() )
            return d1.getId().compareTo( d2.getId() );
        else
            return d2.getId().compareTo( d1.getId() );

    }
}
