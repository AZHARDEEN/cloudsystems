package br.com.mcampos.controller.anoto.model;


import java.util.Comparator;

import org.zkoss.zul.ListModelList;


public class FormListModel extends ListModelList
{

    @Override
    public void sort( Comparator cmpr, boolean ascending )
    {
        super.sort( cmpr, ascending );
    }
}
