package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PadPK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PadSessionLocal
{
    public Pad get( PadPK key ) throws ApplicationException;

    List<AnotoPage> getPages( Pad pad ) throws ApplicationException;

    List<AnotoPage> getPages( ) throws ApplicationException;

    AnotoPage getPage( AnotoPagePK key );

    List<Media> getImages( AnotoPage page ) throws ApplicationException;

    Media removeImage( AnotoPage pageEntity, Media image ) throws ApplicationException;

    Media addImage( AnotoPage page, Media image ) throws ApplicationException;

    List<AnotoPen> getAvailablePens( AnotoPage page ) throws ApplicationException;

    List<AnotoPen> getPens( AnotoPage page ) throws ApplicationException;

    void addPens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException;

    void removePens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException;


    List<AnotoPage> getPages( String address ) throws ApplicationException;

    AnotoPenPage getPenPage( AnotoPen pen, AnotoPage page ) throws ApplicationException;
}
