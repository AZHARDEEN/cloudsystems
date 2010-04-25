package br.com.mcampos.controller.anoto.util.icr;


import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.jawin.COMException;


public class ICRObject
{
    public static final String TBLFileName = "C:\\Program Files\\A2iA\\Configuration\\CFAnoto\\Anoto.tbl";
    public static final String testeImage = "T:\\temp\\anoto\\21.117.12.1_icr.tif";

    public static Map<String, IcrField> processImage ( String a2iaTableDef, String imageFilenamePath, Integer imageType )
    {
        // Variables necessary for A2iA functionality
        A2iaTemplate template;
        A2iaChannel channel;
        A2iaRequest request;


        try
        {
            A2ia icrObj = A2ia.getInstance();

            channel = icrObj.createChannel();
            template = new A2iaTemplate ( icrObj, a2iaTableDef );
            template.getDefaultDocument().setImage( imageFilenamePath, imageType );

            request = new A2iaRequest ( icrObj, channel, template );
            try
            {
                request.process();
            }

            catch(Exception e)
            {
                // Write error to error file
                System.out.println("Cannot get result for image at " + imageFilenamePath + ". Trying next document");
                System.out.println(e);
            }

            // Proceed with data extraction only if no error occurred at GetResult stage of application
            if( request.getResult() != null )
            {
                Integer nField ;
                String value;
                Integer score;
                String name;
                IcrField field;

                System.out.println("Results for image :");
                for ( nField = 1; nField <= request.getFields().size(); nField ++ )
                {
                    name = template.getFieldName( nField );
                    value = request.getResult().getValueString( nField );
                    score = request.getResult().getScore( nField );
                    request.setFieldValue ( nField, value, score );
                    System.out.println( name.toString() + "Value: " + value + "; Score: " + score.toString());
                }
            }

            // Closes a request after processing or after error occurs at GetResult phase
            request.close ();
            template.close();
            channel.close ();
            return request.getFields();
        }
        // Global error catch routine. Handles all fatal application errors
        catch (Exception e)
        {
            // Output error to console
            System.out.println("Application error: " + e);
        }
        return Collections.emptyMap();
    }

    protected void getResultList ( A2iaRequest request ) throws COMException
    {
        if( request.getResult() != null )
        {
            Integer nField ;
            String value;
            Integer score;
            String name;
            IcrField field;

            System.out.println("Results for image :");
            for ( nField = 1; nField <= request.getFields().size(); nField ++ )
            {
                name = request.getTemplate().getFieldName( nField );
                value = request.getResult().getValueString( nField );
                score = request.getResult().getScore( nField );
                request.setFieldValue ( nField, value, score );
                System.out.println( name.toString() + "; Value: " + value + "; Score: " + score.toString());
            }
        }
    }


    public static void main ( String [] args )
    {
        Map resultList = ICRObject.processImage(  TBLFileName, testeImage, A2iaDocument.typeTIFF );
        Iterator it = resultList.keySet().iterator();
        while ( it != null && it.hasNext() ) {
            IcrField field = ( IcrField )resultList.get( it.next() );
            if ( field != null )
            {

            }
        }
    }
}
