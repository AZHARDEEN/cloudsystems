package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PadPK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PadSessionLocal
{
    public Pad get( PadPK key ) throws ApplicationException;

    List<AnotoPage> getPages( Pad pad ) throws ApplicationException;

    AnotoPage getPage( AnotoPagePK key );

    List<Media> getImages( AnotoPage page ) throws ApplicationException;

    Media removeImage( AnotoPage pageEntity, Media image ) throws ApplicationException;

    Media addImage( AnotoPage page, Media image ) throws ApplicationException;

    List<AnotoPen> getAvailablePens( AnotoPage page ) throws ApplicationException;

    List<AnotoPen> getPens( AnotoPage page ) throws ApplicationException;

    void addPens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException;

    void removePens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException;
}
