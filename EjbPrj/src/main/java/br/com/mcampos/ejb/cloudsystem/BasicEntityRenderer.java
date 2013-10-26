package br.com.mcampos.ejb.cloudsystem;

import java.io.Serializable;

public interface BasicEntityRenderer<T> extends Serializable
{
    public String getField( Integer field );

    public int compareTo( T object, Integer field );
}
