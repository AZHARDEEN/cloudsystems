package br.com.mcampos.controller.anoto.model;


import br.com.mcampos.dto.anoto.FormDTO;

import java.util.List;

import org.zkoss.zul.ListModelList;


public class FormListModel extends ListModelList
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
