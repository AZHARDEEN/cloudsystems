package br.com.mcampos.controller.admin.tables.AddressType;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Borderlayout;

public class AddressTypeController extends LoggedBaseController
{
    public AddressTypeController( char c )
    {
        super( c );
    }

    public AddressTypeController()
    {
        super();
    }


}


