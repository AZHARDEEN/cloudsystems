package br.com.mcampos.controller.anoto.util.icr;


import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jawin.COMException;


public class A2iaTemplate extends A2iaBase
{
    public static final String defaultFieldName = "field_";

    private static final String attrPrefix = "defaultDefinition.doc.documentTypeInfo.CaseCustom.custom.";

    private String filename;
    private Map<String, IcrField> fields;

    private A2iaDocument defaultDocument;

    public A2iaTemplate( A2ia obj )
    {
        super( obj );
    }

    public A2iaTemplate( A2ia obj, String filename ) throws COMException
    {
        super( obj );
        setFilename( filename );
    }


    public void setFilename( String filename ) throws COMException
    {
        if ( SysUtils.isEmpty( filename ) )
            throw new InvalidParameterException( "O template não pode ser nulo" );
        this.filename = filename;
        open();
    }

    public String getFilename()
    {
        return filename;
    }

    private void open() throws COMException
    {
        setId( getIcrObj().openTemplate( this ) );
        fields = loadFields();
        createDefaultDocument();
    }

    private void createDefaultDocument() throws COMException
    {
        Integer documentId = getIcrObj().createDefaultDocument( this );
        defaultDocument = new A2iaDocument( getIcrObj() );
        defaultDocument.setId( documentId );
    }

    private Map<String, IcrField> loadFields() throws COMException
    {
        Integer nFields = getFieldCount();
        String aux;
        Map<String, IcrField> fields;
        IcrField icrField;

        if ( nFields <= 0 )
            return Collections.emptyMap();
        fields = new HashMap<String, IcrField>( nFields );
        for ( int nIndex = 1; nIndex <= nFields; nIndex++ ) {
            icrField = new IcrField();
            aux = getFieldName( nIndex );
            icrField.setId( aux );
            icrField.setStrType( getFieldType( nIndex ) );
            icrField.setFieldIndex( nIndex );
            fields.put( icrField.getId(), icrField );
        }
        return fields;
    }

    private Integer getFieldCount() throws COMException
    {
        Integer nFields;

        try {
            nFields = ( Integer )getProperty( attrPrefix + "fields" );
        }
        catch ( ClassCastException e ) {
            nFields = 0;
        }
        return nFields;
    }

    public String getFieldName( Integer fieldIndex ) throws COMException
    {
        if ( fieldIndex < 1 )
            throw new InvalidParameterException( "O indice do campo DEVE ser maior ou igual a 1" );
        String aux = ( String )getProperty( getNameAttrString( fieldIndex ) );
        if ( SysUtils.isEmpty( aux ) )
            aux = defaultFieldName + fieldIndex.toString();
        return aux;
    }

    private String getFieldType( Integer fieldIndex ) throws COMException
    {
        if ( fieldIndex < 1 )
            throw new InvalidParameterException( "O indice do campo DEVE ser maior ou igual a 1" );
        return ( String )getProperty( getTypeAttrString( fieldIndex ) );
    }

    private Object getProperty( String name ) throws COMException
    {
        return getIcrObj().getProperty( getId(), name );
    }

    private static String getNameAttrString( int field )
    {
        return attrPrefix + "fields[" + field + "].id";
    }

    private static String getTypeAttrString( int field )
    {
        return attrPrefix + "fields[" + field + "].fieldType";
    }

    public A2iaDocument getDefaultDocument()
    {
        return defaultDocument;
    }

    public void close() throws COMException
    {
        if ( getDefaultDocument() != null )
            getDefaultDocument().close();
        if ( SysUtils.isZero( getId() ) == false ) {
            getIcrObj().close( this );
            setId( null );
            System.out.println( "template is finalized!" );
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        close();
        System.out.println( "Template is finalized!" );
        super.finalize();
    }

    public Map<String, IcrField> getFields()
    {
        Map<String, IcrField> copy = new HashMap<String, IcrField>( this.fields );
        ;
        return copy;
    }
}
