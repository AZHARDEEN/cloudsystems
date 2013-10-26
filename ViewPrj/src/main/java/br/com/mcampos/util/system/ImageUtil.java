package br.com.mcampos.util.system;


import br.com.mcampos.dto.system.MediaDTO;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.zkoss.zul.Image;


public final class ImageUtil implements Serializable
{
    public static BufferedImage loadImage( MediaDTO media )
    {
        if ( media == null )
            return null;
        return loadImage( media.getObject() );
    }

    public static BufferedImage loadImage( byte[] byteImage )
    {
        if ( byteImage == null )
            return null;

        ByteArrayInputStream is;

        try {
            is = new ByteArrayInputStream( byteImage );
            return ImageIO.read( is );
        }
        catch ( Exception e ) {
            return null;
        }

    }

    public static void loadImage( Image image, MediaDTO dto )
    {
        if ( image != null ) {
            BufferedImage img = loadImage( dto );
            if ( img != null )
                image.setContent( img );
        }
    }

    public static void loadImage( Image image, byte[] byteImage )
    {
        if ( image != null ) {
            BufferedImage img = loadImage( byteImage );
            if ( img != null )
                image.setContent( img );
        }
    }

}
