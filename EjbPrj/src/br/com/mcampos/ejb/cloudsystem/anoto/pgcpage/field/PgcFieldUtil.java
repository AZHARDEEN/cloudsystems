package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;

import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageUtil;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.FieldTypeUtil;

public class PgcFieldUtil
{
    public PgcFieldUtil()
    {
        super();
    }


    public static PgcField update( PgcField entity, PgcFieldDTO dto )
    {
        entity.setRevisedText( dto.getRevisedText() );
        return entity;
    }

    public static PgcField createEntity( PgcFieldDTO dto )
    {
        PgcField entity = new PgcField();

        entity.setIcrText( dto.getIrcText() );
        entity.setName( dto.getName() );
        entity.setRevisedText( dto.getRevisedText() );
        entity.setPgcPage( PgcPageUtil.createEntity( dto.getPgcPage() ) );
        entity.setHasPenstrokes( dto.getHasPenstrokes() );
        entity.setStartTime( dto.getStartTime() );
        entity.setEndTime( dto.getEndTime() );
        entity.setType( FieldTypeUtil.createEntity( dto.getType() ) );
        return entity;
    }

}
