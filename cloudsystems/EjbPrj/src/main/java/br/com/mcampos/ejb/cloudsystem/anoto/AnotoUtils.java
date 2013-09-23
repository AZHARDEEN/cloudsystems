package br.com.mcampos.ejb.cloudsystem.anoto;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AnotoUtils
{
    public AnotoUtils()
    {
        super();
    }

    public static List<AnotoPen> getPenListFromPenPage( List<AnotoPenPage> list, boolean bUnique )
    {
        List<AnotoPen> pens = Collections.emptyList();
        if ( SysUtils.isEmpty( list ) == false ) {
            pens = new ArrayList<AnotoPen>( list.size() );
            for ( AnotoPenPage item : list ) {
                if ( bUnique ) {
                    if ( pens.contains( item.getPen() ) )
                        continue;
                }
                pens.add( item.getPen() );
            }
        }
        return pens;
    }

    public static List<FormDTO> toFormList( List<AnotoForm> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FormDTO> dtoList = new ArrayList<FormDTO>( list.size() );
        for ( AnotoForm f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<MediaDTO> toMediaList( List<Media> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtoList = new ArrayList<MediaDTO>( list.size() );
        for ( Media f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

    public static List<MediaDTO> toMediaListFromFormMedia( List<FormMedia> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtoList = new ArrayList<MediaDTO>( list.size() );
        for ( FormMedia f : list ) {
            dtoList.add( f.getMedia().toDTO() );
        }
        return dtoList;
    }


    public static List<PenDTO> toPenList( List<AnotoPen> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PenDTO> dtoList = new ArrayList<PenDTO>( list.size() );
        for ( AnotoPen f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<PadDTO> toPadList( List<Pad> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PadDTO> dtoList = new ArrayList<PadDTO>( list.size() );
        for ( Pad f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<PgcPenPageDTO> toPgcPenPageList( List<PgcPenPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcPenPageDTO> dtoList = new ArrayList<PgcPenPageDTO>( list.size() );
        for ( PgcPenPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<AnotoPenPageDTO> toPenPageList( List<AnotoPenPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPenPageDTO> dtoList = new ArrayList<AnotoPenPageDTO>( list.size() );
        for ( AnotoPenPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

    public static List<AnotoPageDTO> toPageList( List<AnotoPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPageDTO> dtoList = new ArrayList<AnotoPageDTO>( list.size() );
        for ( AnotoPage f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<PgcFieldDTO> toPgcFieldList( List<PgcField> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcFieldDTO> dtoList = new ArrayList<PgcFieldDTO>( list.size() );
        for ( PgcField f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<PgcAttachmentDTO> toPgcAttachmentList( List<PgcPageAttachment> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcAttachmentDTO> dtoList = new ArrayList<PgcAttachmentDTO>( list.size() );
        for ( PgcPageAttachment f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

    public static List<PGCDTO> toPgcList( List<Pgc> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PGCDTO> dtoList = new ArrayList<PGCDTO>( list.size() );
        for ( Pgc f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

    public static List<AnotoPageFieldDTO> toAnotoPageFieldDTO( List<AnotoPageField> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPageFieldDTO> dtoList = new ArrayList<AnotoPageFieldDTO>( list.size() );
        for ( AnotoPageField f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public static List<AnotoPageField> toAnotoPageField( List<AnotoPageFieldDTO> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoPageField> dtoList = new ArrayList<AnotoPageField>( list.size() );
        for ( AnotoPageFieldDTO f : list ) {
            dtoList.add( DTOFactory.copy( f ) );
        }
        return dtoList;
    }
}
