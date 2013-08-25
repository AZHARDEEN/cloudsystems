package br.com.mcampos.web.core.dbwidgets;

import java.io.Serializable;

import org.zkoss.zk.ui.WrongValueException;


public interface DBWidget extends Serializable
{
    public static final String primeryKeyAttributeName = "PrimaryKey";

    public void setPrimaryKey( Boolean bSet );

    public Boolean isPrimaryKey();

    public void setText( String value ) throws WrongValueException;

    public void setDisabled( boolean disabled );

    public String getId();

    public String getText();
}
