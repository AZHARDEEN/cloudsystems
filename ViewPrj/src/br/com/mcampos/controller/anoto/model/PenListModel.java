package br.com.mcampos.controller.anoto.model;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenDTO;

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
