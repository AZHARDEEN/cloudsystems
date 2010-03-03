package br.com.mcampos.controller.anoto.renderer;

import java.util.ArrayList;
import java.util.List;

public class GridProperties
{
    public String name;
    public List<String> values;

    public GridProperties()
    {
        super();
        values = new ArrayList<String>();
    }

    public void add( String value )
    {
        values.add( value );
    }
}


