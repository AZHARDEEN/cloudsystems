package br.com.mcampos.ejb.cloudsystem.anode.utils;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnotoUtils
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


    public static List<PgcAttachmentDTO> toPgcAttachmentList( List<PgcAttachment> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcAttachmentDTO> dtoList = new ArrayList<PgcAttachmentDTO>( list.size() );
        for ( PgcAttachment f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }

}
