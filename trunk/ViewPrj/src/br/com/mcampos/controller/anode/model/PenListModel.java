package br.com.mcampos.controller.anode.model;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;

import java.util.Collections;
import java.util.List;

public class PenListModel extends AbstractPagingListModel<PenDTO>
{
    protected List<PenDTO> list;

    public PenListModel( List<PenDTO> list )
    {
        this.list = list;
    }


    public int getTotalSize()
    {
        return list.size();
    }

    protected List<PenDTO> getPageData( int itemStartNumber, int pageSize )
    {
        return list;
    }
}
