package br.com.mcampos.controller.anoto.renderer;

import java.util.ArrayList;
import java.util.List;

public class GridProperties
{
    public String name;
    public List<Object> values;

    public GridProperties()
    {
        super();
        values = new ArrayList<Object>();
    }

    public void add( Object value )
    {
        values.add( value );
    }
}


