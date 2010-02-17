package br.com.mcampos.controller.admin.tables.AccessLogType;

import br.com.mcampos.controller.admin.tables.core.SimpleTableLocator;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.core.AccessLogTypeInterface;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

public class AccessLogLocator extends SimpleTableLocator implements AccessLogTypeInterface
{
    public AccessLogLocator()
    {
        super();
    }

    public List getList( AuthenticationDTO currentUser )
    {
        try {
            return getAccessLogTypes( currentUser );
        }
        catch ( Exception e ) {
            e = null;
            return Collections.emptyList();
        }
    }

    public List<AccessLogTypeDTO> getAccessLogTypes( AuthenticationDTO auth ) throws ApplicationException
    {
        return get().getAccessLogTypes( auth );
    }

    public Integer getNextAccessLogTypeId( AuthenticationDTO auth ) throws ApplicationException
    {
        return get().getNextAccessLogTypeId( auth );
    }

    public AccessLogTypeDTO update( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        return get().update( auth, dto );
    }

    public AccessLogTypeDTO add( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        return get().add( auth, dto );
    }

    public Boolean validate( AccessLogTypeDTO dto, Boolean isNew ) throws ApplicationException
    {
        return get().validate( dto, isNew );
    }

    public void delete( AuthenticationDTO auth, AccessLogTypeDTO id ) throws ApplicationException
    {
        get().delete( auth, id );
    }
}
