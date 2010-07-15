package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.Query;


@Stateless( name = "PgcFieldSession", mappedName = "CloudSystems-EjbPrj-PgcFieldSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcFieldSessionBean extends Crud<PgcFieldPK, PgcField> implements PgcFieldSessionLocal
{
    public void delete( PgcFieldPK key ) throws ApplicationException
    {
        PgcField entity = get( key );
        PgcPage parent = null;

        if ( entity != null ) {
            parent = entity.getPgcPage();
            delete( PgcField.class, key );
            if ( parent != null )
                getEntityManager().refresh( parent );
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PgcField get( PgcFieldPK key ) throws ApplicationException
    {
        return get( PgcField.class, key );
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcField> getAll( PgcPage pgcPage ) throws ApplicationException
    {
        pgcPage = getEntityManager().merge( pgcPage );
        return ( List<PgcField> )getResultList( PgcField.findPageFields, pgcPage );
    }


    public static final String formParameterName = "form";
    public static final String penParameterName = "pen";
    public static final String initDateParameterName = "initDate";
    public static final String endDateParameterName = "endDate";
    public static final String barCodeParameterName = "barCode";
    public static final String bookIdFromParameterName = "bookIdFrom";
    public static final String bookIdToParameterName = "bookIdTo";
    public static final String fieldValueParameterName = "pgcFieldValue";
    public static final String revisedStatusParameterName = "revisedStatus";

    public List<PgcField> getAll( Properties props ) throws ApplicationException
    {
        StringBuffer jpaQuery = getSummarySQL();
        StringBuffer jpaWhere = new StringBuffer();

        addFormFilter( props, jpaWhere );
        addPenFilter( props, jpaWhere );
        addBarcodeFilter( props, jpaWhere );
        addDateRangeFilter( props, jpaWhere );
        addFieldValueFilter( props, jpaWhere );
        addCustomFieldFilter( props, jpaWhere );

        jpaQuery.append( jpaWhere );
        Query query = getEntityManager().createNativeQuery( jpaQuery.toString(), PgcField.class );
        List<PgcField> list = ( List<PgcField> )query.getResultList();
        return list;
    }

    private StringBuffer addCustomFieldFilter( Properties props, StringBuffer jpaWhere )
    {
        String fieldValue = "";
        Properties custom = ( Properties )props.get( "custom_fields" );
        if ( custom != null ) {
            Set<String> fields = custom.stringPropertyNames();
            String sqlFieldValue = " exists ( select a.pgc_id_in from pgc_field a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and a.ppg_book_id = pgc_page.ppg_book_id " + "   and a.ppg_page_id = pgc_page.ppg_page_id \n";
            for ( String field : fields ) {
                sqlFieldValue += " and pfl_name_ch = '" + field + "'";
                String value = custom.getProperty( field );
                if ( value.indexOf( "*" ) >= 0 ) {
                    value = fieldValue.replace( '*', '%' );
                    sqlFieldValue += " and coalesce ( pfl_revised_tx, pfl_icr_tx ) LIKE '" + value + "' ";
                }
                else
                    sqlFieldValue += " and coalesce ( pfl_revised_tx, pfl_icr_tx ) = '" + value + "' ";
            }
            jpaWhere.append( " AND " );
            jpaWhere.append( sqlFieldValue + ")" );
        }
        return jpaWhere;
    }

    private StringBuffer addFieldValueFilter( Properties props, StringBuffer jpaWhere )
    {
        String fieldValue = ( String )( props != null ? props.get( fieldValueParameterName ) : "" );
        if ( SysUtils.isEmpty( fieldValue ) == false ) {
            String sqlFieldValue = " exists ( select a.pgc_id_in from pgc_field a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and a.ppg_book_id = pgc_page.ppg_book_id " + "   and a.ppg_page_id = pgc_page.ppg_page_id \n";
            jpaWhere.append( " AND " );
            if ( fieldValue.indexOf( "*" ) >= 0 ) {
                fieldValue = fieldValue.replace( '*', '%' );
                jpaWhere.append( sqlFieldValue + " and coalesce ( pfl_revised_tx, pfl_icr_tx ) LIKE '" + fieldValue + "')" );
            }
            else
                jpaWhere.append( sqlFieldValue + " and coalesce ( pfl_revised_tx, pfl_icr_tx ) = '" + fieldValue + "' )" );
        }
        return jpaWhere;
    }

    private StringBuffer addDateRangeFilter( Properties props, StringBuffer jpaWhere )
    {
        Date initDate = ( Date )( props != null ? props.get( initDateParameterName ) : "" );
        SimpleDateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmss" );

        if ( initDate != null ) {
            jpaWhere.append( " AND " );
            jpaWhere.append( "pgc.pgc_insert_dt >= TO_TIMESTAMP ( '" + df.format( initDate ) + "', 'YYYYMMDD HH24MISS' ) " );
        }

        Date endDate = ( Date )( props != null ? props.get( endDateParameterName ) : "" );
        if ( endDate != null ) {
            jpaWhere.append( " AND " );
            jpaWhere.append( "pgc.pgc_insert_dt <= TO_TIMESTAMP ( '" + df.format( endDate ) + "', 'YYYYMMDD HH24MISS' ) " );
        }
        return jpaWhere;
    }


    private StringBuffer addFormFilter( Properties props, StringBuffer jpaWhere )
    {
        AnotoForm form;

        form = ( AnotoForm )( props != null ? props.get( formParameterName ) : null );
        if ( form != null ) {
            jpaWhere.append( " AND " );
            jpaWhere.append( " anoto_form.frm_id_in = " + form.getId() );
        }
        return jpaWhere;
    }


    private StringBuffer addPenFilter( Properties props, StringBuffer jpaWhere )
    {
        String aux = ( String )( props != null ? props.get( penParameterName ) : "" );
        if ( SysUtils.isEmpty( aux ) == false ) {
            jpaWhere.append( " AND " );
            if ( aux.indexOf( "*" ) >= 0 ) {
                aux = aux.replace( '*', '%' );
                jpaWhere.append( "pgc.pgc_pen_id LIKE '" + aux + "'" );
            }
            else
                jpaWhere.append( "pgc.pgc_pen_id = '" + aux + "'" );
        }
        return jpaWhere;
    }

    private StringBuffer addBarcodeFilter( Properties props, StringBuffer jpaWhere )
    {
        String barCode = ( String )( props != null ? props.get( barCodeParameterName ) : "" );
        if ( SysUtils.isEmpty( barCode ) == false ) {
            String sqlBarCode = " exists ( select a.pgc_id_in from pgc_attachment a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and " + " a.ppg_book_id = pgc_page.ppg_book_id and " + " a.ppg_page_id = pgc_page.ppg_page_id \n";
            jpaWhere.append( " AND " );
            if ( barCode.indexOf( "*" ) >= 0 ) {
                barCode = barCode.replace( '*', '%' );
                jpaWhere.append( sqlBarCode + " and a.pat_value_ch LIKE '" + barCode + "')" );
            }
            else
                jpaWhere.append( sqlBarCode + " and a.pat_value_ch = '" + barCode + "' )" );
        }
        return jpaWhere;
    }


    private StringBuffer getSummarySQL()
    {
        StringBuffer sql = new StringBuffer();

        sql.append( "SELECT pgc_field.* FROM " );
        sql.append( "    pgc_field, " );
        sql.append( "    pgc_page, " );
        sql.append( "    pgc, " );
        sql.append( "    anoto_form " );
        sql.append( "WHERE " );
        sql.append( "    pgc_field.pgc_id_in = pgc_page.pgc_id_in AND " );
        sql.append( "    pgc_field.ppg_book_id = pgc_page.ppg_book_id AND " );
        sql.append( "    pgc_field.ppg_page_id = pgc_page.ppg_page_id AND " );
        sql.append( "    pgc_page.pgc_id_in = pgc.pgc_id_in AND " );
        sql.append( "    pgc_page.frm_id_in = anoto_form.frm_id_in AND " );
        sql.append( "    pgc_field.pfl_name_ch in ( 'PAP', 'CVM', 'Dinheiro', 'Deposito_Identificado', 'Boleto_Bancario', 'Reposicao_Pre', 'Reposicao_Pos' ) " );
        return sql;
    }
}

