package br.com.mcampos.util.system;

import java.io.Serializable;

import org.zkoss.zk.ui.event.MouseEvent;


public interface IClickEvent extends Serializable
{
    void onClick( MouseEvent evt );
}
