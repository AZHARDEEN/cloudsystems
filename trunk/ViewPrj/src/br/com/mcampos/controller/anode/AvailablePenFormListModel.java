package br.com.mcampos.controller.anode;

import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

public class AvailablePenFormListModel extends AbstractAnodeListModel
{
    protected PenDTO currentDTO;

    protected List<FormDTO> list;


    public AvailablePenFormListModel( AnodeFacade anodeFacade, AuthenticationDTO auth, PenDTO currentPen )
    {
        super( anodeFacade, auth );
        setCurrentDTO( currentPen );
    }

    public int getTotalSize()
    {
        return list.size();
    }

    protected List<FormDTO> getPageData( int itemStartNumber, int pageSize )
    {
        try {
            list = getSession().getAvailableForms( getAuth(), getCurrentDTO() );
            return list;
        }
        catch ( ApplicationException e ) {
            return Collections.emptyList();
        }
    }

    public void setCurrentDTO( PenDTO currentDTO )
    {
        this.currentDTO = currentDTO;
    }

    public PenDTO getCurrentDTO()
    {
        return currentDTO;
    }
}
