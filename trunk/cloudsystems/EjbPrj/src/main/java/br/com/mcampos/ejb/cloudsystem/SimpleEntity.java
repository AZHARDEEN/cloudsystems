package br.com.mcampos.ejb.cloudsystem;


import java.io.Serializable;

public interface SimpleEntity<ENTITY> extends Serializable, BasicEntityRenderer<ENTITY>, Comparable<ENTITY>
{
    public String getDescription();

    public void setDescription( String description );

    public Integer getId();

    public void setId( Integer id );

}
