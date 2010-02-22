package br.com.mcampos.controller.anode.model;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

public class FormListModel extends AbstractPagingListModel<FormDTO>
{
    protected List<FormDTO> list;

    public FormListModel( List<FormDTO> list )
    {
        this.list = list;
    }


    public int getTotalSize()
    {
        return list.size();
    }

    protected List<FormDTO> getPageData( int itemStartNumber, int pageSize )
    {
        return list;
    }
}
