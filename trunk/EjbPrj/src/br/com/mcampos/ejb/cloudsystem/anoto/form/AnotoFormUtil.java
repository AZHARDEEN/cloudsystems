package br.com.mcampos.ejb.cloudsystem.anoto.form;

import br.com.mcampos.dto.anoto.FormDTO;

public class AnotoFormUtil
{
    public AnotoFormUtil()
    {
        super();
    }


    public static AnotoForm createEntity( FormDTO source )
    {
        if ( source == null )
            return null;

        AnotoForm target = new AnotoForm( source.getId(), source.getApplication().trim() );
        return update( target, source );
    }


    public static AnotoForm update( AnotoForm target, FormDTO source )
    {
        if ( source == null || target == null )
            return null;

        target.setDescription( source.getDescription().trim() );
        target.setIcrImage( source.getIcrImage() );
        target.setImagePath( source.getImagePath() );
        target.setConcatenatePgc( source.getConcatenatePgc() );
        return target;
    }

}
