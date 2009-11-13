package br.com.mcampos.controller.admin.users.model;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.AbstractListModel;

public abstract class AbstractPagingListModel<T> extends AbstractListModel
{
    public AbstractPagingListModel()
    {
        super();
    }
    

    /**
     * 
     */
    protected static final long serialVersionUID = 6613208067174831719L;
    
    protected int _startPageNumber;
    protected int _pageSize;
    protected int _itemStartNumber;
    
    //internal use only
    protected List<T> _items = new ArrayList<T>();
    
    public AbstractPagingListModel(int startPageNumber, int pageSize) {
        super();
        loadPage ( startPageNumber, pageSize );        
    }
    
    public void loadPage ( int startPageNumber, int pageSize )
    {
        this._startPageNumber = startPageNumber;
        this._pageSize = pageSize;
        this._itemStartNumber = startPageNumber * pageSize;
        
        _items = getPageData(_itemStartNumber, _pageSize);
    }
    
    public abstract int getTotalSize();
    protected abstract List<T> getPageData(int itemStartNumber, int pageSize);
    
    @Override
    public Object getElementAt(int index) {
        return _items.get(index);
    }

    @Override
    public int getSize() {
        return _items.size();
    }
    
    public int getStartPageNumber() {
        return this._startPageNumber;
    }
    
    public int getPageSize() {
        return this._pageSize;
    }
    
    public int getItemStartNumber() {
        return _itemStartNumber;
    }
    
}
