package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PadPK;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PadSessionLocal
{
    public Pad get( PadPK key ) throws ApplicationException;

    List<AnotoPage> getPages( Pad pad ) throws ApplicationException;
}
