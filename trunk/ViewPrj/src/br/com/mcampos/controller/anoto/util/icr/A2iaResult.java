package br.com.mcampos.controller.anoto.util.icr;

import org.jawin.COMException;

public class A2iaResult extends A2iaBase
{
    private static final String attrPrefix = "documentTypeInfo.CaseCustom.custom.fields";

    public A2iaResult( A2ia a2ia )
    {
        super( a2ia );
    }

    public String getValueString ( Integer index ) throws COMException
    {
        Object obj = getIcrObj().getProperty( getId(), getValueAttrString ( index ) );
        return (String)obj;
    }

    public Integer getScore ( Integer index ) throws COMException
    {
        Object obj = getIcrObj().getProperty( getId(), getScoreAttrString( index ) );
        return (Integer)obj;
    }


    private static String getValueAttrString ( int field )
    {
        String str = attrPrefix + "[" + field + "].fieldTypeInfo.CaseWord.word.result.reco";
        return str;
    }

    private static String getScoreAttrString ( int field )
    {
        String str = attrPrefix + "[" + field + "].fieldTypeInfo.CaseWord.word.result.score";
        return str;
    }
}
