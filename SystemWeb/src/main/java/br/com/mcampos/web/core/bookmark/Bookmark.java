package br.com.mcampos.web.core.bookmark;

import java.io.Serializable;

import java.util.HashMap;


public class Bookmark implements Serializable
{
	private static final long serialVersionUID = -6342330891431749547L;
	private String uri;
    private HashMap<String, Object> parameters;

    public Bookmark()
    {
        super();
    }

    public Bookmark( String uri, HashMap<String, Object> parameters )
    {
        super();
        setUri( uri );
        setParameters( parameters );
    }

    public void setUri( String uri )
    {
        this.uri = uri;
    }

    public String getUri()
    {
        return uri;
    }


    public void setParameters( HashMap<String, Object> parameters )
    {
        this.parameters = parameters;
    }

    public HashMap<String, Object> getParameters()
    {
        return parameters;
    }
}
