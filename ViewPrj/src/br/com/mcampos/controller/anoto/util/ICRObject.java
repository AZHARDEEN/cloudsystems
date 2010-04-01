package br.com.mcampos.controller.anoto.util;

import org.jawin.COMException;

public class ICRObject
{
    protected static final String tblFile = "C:\\Program Files\\A2iA\\Configuration\\CF_Anoto\\_Anoto.tbl";

    protected Integer channelParamId = 0;
    protected Integer channel = 0;
    protected Integer documentId = 0;
    protected Integer tableId = 0;

    public ICRObject()
    {
        super();

    }


    protected Icr getIcrInstance () throws COMException
    {
        return Icr.getInstance();
    }


    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        // Close all A2iA items in appropriate order
        try {
            if ( documentId != 0 )
                getIcrInstance().invoke ("ScrCloseDocument", documentId);
            //A2iAObj.invoke("ScrFreeAllPersistents", channelId);
            if ( tableId != 0 )
                getIcrInstance().invoke ("ScrCloseDocumentTable", tableId);
            if ( channel != 0 )
                getIcrInstance().invoke ("ScrCloseChannel", channel );
        }
        catch ( Exception e )
        {
            e = null;
        }
    }


    public Integer getChannel () throws COMException
    {
        // Open the channel
        if ( channel == 0 ) {
            channel = (Integer) getIcrInstance().openChannel();
            getTableId();
        }
        return channel;
    }

    protected Integer getTableId () throws COMException
    {
        if ( tableId == 0 ) {
            if ( channel == 0 )
                getChannel();
            tableId = ( Integer ) getIcrInstance().invoke("ScrOpenDocumentTable", tblFile );
        }
        return tableId;
    }


    protected Integer getDocumentId () throws COMException
    {
        //getChannel ();
        if ( documentId == 0 )
            documentId = ( Integer ) getIcrInstance().invoke("ScrGetDefaultDocument", getTableId() );
        return documentId;
    }

    public String process ( String imgType ) throws COMException
    {
        Integer requestId;
        Integer resultId;
        Integer timeout = 60000;
        String out = "";

        //setSource( "Memory" );
        //setInputType ( imgType );
        getIcrInstance().invoke( "ScrDefineImage", getDocumentId(), "JPEG", "FILE", imgType );
        requestId = (Integer)getIcrInstance().invoke ("ScrOpenRequest", getChannel(), getDocumentId());
        resultId = (Integer)getIcrInstance().invoke ("ScrGetResult", getChannel(), requestId, timeout);
        getIcrInstance().invoke( "ScrCloseRequest", requestId);
        return out;
    }

    protected void setSource ( String source ) throws COMException
    {
        getIcrInstance().invoke( "ObjectProperty", getDocumentId(), "additionalPages[1].imageSourceType", source );
    }


    protected void setInputType ( String source ) throws COMException
    {
        getIcrInstance().invoke( "ObjectProperty", getDocumentId(), "additionalPages[1].inputFormat", source );
    }
}
