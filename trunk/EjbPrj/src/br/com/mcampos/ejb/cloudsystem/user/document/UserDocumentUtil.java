package br.com.mcampos.ejb.cloudsystem.user.document;


import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.DocumentTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDocumentUtil
{
    public UserDocumentUtil()
    {
        super();
    }

    public static UserDocumentDTO copy( UserDocument entity )
    {
        UserDocumentDTO dto = new UserDocumentDTO();
        dto.setDocumentType( DocumentTypeUtil.copy( entity.getDocumentType() ) );
        dto.setCode( entity.getCode() );
        dto.setAdditionalInfo( entity.getAdditionalInfo() );
        return dto;
    }

    public static UserDocument createEntity( UserDocumentDTO dto, Users user )
    {
        UserDocument entity = new UserDocument();
        entity.setUser( user );
        return update( entity, dto );
    }

    public static UserDocument update( UserDocument entity, UserDocumentDTO dto )
    {
        String code = applyDocumentRules( dto.getCode(), dto.getDocumentType().getId() );
        entity.setCode( code );
        entity.setAdditionalInfo( dto.getAdditionalInfo() );
        entity.setDocumentType( DocumentTypeUtil.createEntity( dto.getDocumentType() ) );
        return entity;
    }

    protected static String applyDocumentRules( String document, int type )
    {
        String code;


        code = document.trim();
        switch ( type ) {
        case UserDocument.typeCPF:
            code = code.replaceAll( "[\\-.\\/]", "" );
            break;
        case UserDocument.typeEmail:
            code = code.toLowerCase();
            break;
        }
        return code;
    }

    public static List<UserDocument> toEntityList( Users user, List<UserDocumentDTO> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<UserDocument> listDTO = new ArrayList<UserDocument>( list.size() );
        for ( UserDocumentDTO m : list ) {
            listDTO.add( createEntity( m, user ) );
        }
        return listDTO;
    }

}
