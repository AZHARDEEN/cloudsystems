package br.com.mcampos.controller.core;

import java.io.Serializable;

import java.util.Map;

import org.zkoss.zk.ui.Component;

public class PageBrowseHistory implements Serializable
{
    protected String uri;
    protected Component root;
    protected Map parameter;
    
    public final static String historyParamName = "history";
    
    public PageBrowseHistory()
    {
        super();
        uri = null;
        root = null;
        parameter = null;
    }

    public PageBrowseHistory( String uri, Component root, Map parameter )
    {
        super();
        this.uri = uri;
        this.root = root;
        this.parameter = parameter;
    }


    public void setUri( String uri )
    {
        this.uri = uri;
    }

    public String getUri()
    {
        return uri;
    }

    public void setRoot( Component root )
    {
        this.root = root;
    }

    public Component getRoot()
    {
        return root;
    }

    public void setParameter( Map parameter )
    {
        this.parameter = parameter;
    }

    public Map getParameter()
    {
        return parameter;
    }
}
