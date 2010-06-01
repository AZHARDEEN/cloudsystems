package br.com.mcampos.ejb.cloudsystem.anoto.pad;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PadSessionLocal extends Serializable
{
    public Pad get( PadPK key ) throws ApplicationException;

    List<AnotoPage> getPages( Pad pad ) throws ApplicationException;

    List<AnotoPage> getPages() throws ApplicationException;

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

    List<AnotoPage> getPages( AnotoForm param ) throws ApplicationException;

    void add( AnotoPage page, List<AnotoPageFieldDTO> fields ) throws ApplicationException;

    void update( AnotoPage page ) throws ApplicationException;
}
