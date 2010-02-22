package br.com.mcampos.controller.anode.model;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.system.MediaDTO;

import java.util.List;

public class MediaListModel extends AbstractPagingListModel<MediaDTO>
{
    protected List<MediaDTO> list;

    public MediaListModel( List<MediaDTO> list )
    {
        this.list = list;
    }


    public int getTotalSize()
    {
        return list.size();
    }

    protected List<MediaDTO> getPageData( int itemStartNumber, int pageSize )
    {
        return list;
    }
}
