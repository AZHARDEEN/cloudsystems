package br.com.mcampos.controller.anoto.model;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.system.MediaDTO;

import java.util.ArrayList;
import java.util.List;

public class MediaListModel extends AbstractPagingListModel<MediaDTO>
{
    protected List<MediaDTO> dtoList;

    public MediaListModel( List<MediaDTO> list )
    {
        this.dtoList = list;
    }


    public int getTotalSize()
    {
        return dtoList.size();
    }

    protected List<MediaDTO> getPageData( int itemStartNumber, int pageSize )
    {
        return dtoList;
    }

    public List<MediaDTO> getDTOList()
    {
        if ( dtoList == null )
            dtoList = new ArrayList<MediaDTO>();
        return dtoList;
    }
}
