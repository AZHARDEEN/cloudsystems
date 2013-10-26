package br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.sysutils.SysUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;


@Stateless( name = "PgcPenPageSession", mappedName = "PgcPenPageSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class PgcPenPageSessionBean extends Crud<PgcPenPagePK, PgcPenPage> implements PgcPenPageSessionLocal
{
    public static final String formParameterName = "form";
    public static final String penParameterName = "pen";
    public static final String initDateParameterName = "initDate";
    public static final String endDateParameterName = "endDate";
    public static final String barCodeParameterName = "barCode";
    public static final String bookIdFromParameterName = "bookIdFrom";
    public static final String bookIdToParameterName = "bookIdTo";
    public static final String fieldValueParameterName = "pgcFieldValue";
    public static final String revisedStatusParameterName = "revisedStatus";


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcPage> getAll( Properties props, Integer maxRecords, Boolean bNewFirst ) throws ApplicationException
    {
        String jpaQuery = "select distinct \n" +
            "	pgc_page.* \n" +
            "from \n" +
            "	pgc_page JOIN Pgc ON ( pgc.pgc_id_in = pgc_page.pgc_id_in ) \n" +
            "	JOIN \n" +
            "	pgc_pen_page ON ( pgc_pen_page.pgc_id_in = pgc.pgc_id_in ) \n" +
            "	JOIN\n" +
            "	anoto_form ON ( anoto_form.frm_id_in = pgc_pen_page.frm_id_in ) \n  ";
        String jpaWhere = "";
        AnotoForm form;
        String pen;
        String revisedStatus;
        String barCode;
        String fieldValue;
        Integer bookIdFrom, bookIdTo;
        SimpleDateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmss" );

        form = ( AnotoForm )( props != null ? props.get( formParameterName ) : null );
        if ( form != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += " anoto_form.frm_id_in = " + form.getId();
        }

        bookIdFrom = ( Integer )( props != null ? props.get( bookIdFromParameterName ) : null );
        bookIdTo = ( Integer )( props != null ? props.get( bookIdToParameterName ) : null );
        if ( bookIdFrom != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( bookIdTo == null )
                jpaWhere += " pgc_page.ppg_book_id = " + bookIdFrom.toString();
            else
                jpaWhere += " pgc_page.ppg_book_id >= " + bookIdFrom.toString();
        }

        if ( bookIdTo != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += " pgc_page.ppg_book_id <= " + bookIdTo.toString();
        }

        pen = ( String )( props != null ? props.get( penParameterName ) : "" );
        if ( SysUtils.isEmpty( pen ) == false ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( pen.indexOf( "*" ) >= 0 ) {
                pen = pen.replace( '*', '%' );
                jpaWhere += "pgc.pgc_pen_id LIKE '" + pen + "'";
            }
            else
                jpaWhere += "pgc.pgc_pen_id = '" + pen + "'";
        }

        revisedStatus = ( String )( props != null ? props.get( revisedStatusParameterName ) : "" );
        if ( SysUtils.isEmpty( revisedStatus ) == false ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "pgc_page.rst_id_in = " + revisedStatus;
        }

        barCode = ( String )( props != null ? props.get( barCodeParameterName ) : "" );
        if ( SysUtils.isEmpty( barCode ) == false ) {
            String sqlBarCode = " exists ( select a.pgc_id_in from pgc_attachment a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and " + " a.ppg_book_id = pgc_page.ppg_book_id and " +
                " a.ppg_page_id = pgc_page.ppg_page_id \n";
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( barCode.indexOf( "*" ) >= 0 ) {
                barCode = barCode.replace( '*', '%' );
                jpaWhere += sqlBarCode + " and a.pat_value_ch LIKE '" + barCode + "')";
            }
            else
                jpaWhere += sqlBarCode + " and a.pat_value_ch = '" + barCode + "' )";
        }


        fieldValue = ( String )( props != null ? props.get( fieldValueParameterName ) : "" );
        if ( SysUtils.isEmpty( fieldValue ) == false ) {
            String sqlFieldValue = " exists ( select a.pgc_id_in from pgc_field a \n" +
                "	where a.pgc_id_in = pgc_page.pgc_id_in and a.ppg_book_id = pgc_page.ppg_book_id " +
                "   and a.ppg_page_id = pgc_page.ppg_page_id \n";
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( fieldValue.indexOf( "*" ) >= 0 ) {
                fieldValue = fieldValue.replace( '*', '%' );
                jpaWhere += sqlFieldValue + " and coalesce ( pfl_revised_tx, pfl_icr_tx ) LIKE '" + fieldValue + "')";
            }
            else
                jpaWhere += sqlFieldValue + " and coalesce ( pfl_revised_tx, pfl_icr_tx ) = '" + fieldValue + "' )";
        }

        fieldValue = ( String )( props != null ? props.get( "noBackOffice" ) : "" );
        if ( SysUtils.isEmpty( fieldValue ) == false ) {
            String sqlFieldValue = " pgc_page.pgc_id_in not in ( select a.pgc_id_in from pgc_field a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and a.ppg_book_id = pgc_page.ppg_book_id " +
                "   and a.ppg_page_id = pgc_page.ppg_page_id and a.pfl_name_ch = 'Backoffice Responsavel' )\n";
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += sqlFieldValue;
        }


        Date initDate = ( Date )( props != null ? props.get( initDateParameterName ) : "" );
        if ( initDate != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "pgc.pgc_insert_dt >= TO_TIMESTAMP ( '" + df.format( initDate ) + "', 'YYYYMMDD HH24MISS' ) ";
        }

        Date endDate = ( Date )( props != null ? props.get( endDateParameterName ) : "" );
        if ( endDate != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "pgc.pgc_insert_dt <= TO_TIMESTAMP ( '" + df.format( endDate ) + "', 'YYYYMMDD HH24MISS' ) ";
        }
        Properties custom = ( Properties )props.get( "custom_fields" );
        if ( custom != null ) {
            Set<String> fields = custom.stringPropertyNames();
            String sqlFieldValue = " exists ( select a.pgc_id_in from pgc_field a \n" +
                " where a.pgc_id_in = pgc_page.pgc_id_in and a.ppg_book_id = pgc_page.ppg_book_id " +
                "   and a.ppg_page_id = pgc_page.ppg_page_id \n";
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
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += sqlFieldValue + ")";
        }

        if ( jpaWhere.length() > 0 )
            jpaQuery += " WHERE " + jpaWhere;
        if ( bNewFirst )
            jpaQuery += " ORDER BY PGC_ID_IN DESC, PPG_BOOK_ID ASC, PPG_PAGE_ID ASC ";
        else
            jpaQuery += " ORDER BY PGC_ID_IN ASC, PPG_BOOK_ID ASC, PPG_PAGE_ID ASC ";

        Query query = getEntityManager().createNativeQuery( jpaQuery, PgcPage.class );
        if ( SysUtils.isZero( maxRecords ) == false )
            query.setMaxResults( maxRecords );
        List<PgcPage> list = ( List<PgcPage> )query.getResultList();
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        return list;
    }

    public void delete( PgcPenPagePK key ) throws ApplicationException
    {
        delete( PgcPenPage.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PgcPenPage get( PgcPenPagePK key ) throws ApplicationException
    {
        return get( PgcPenPage.class, key );
    }

    public void delete( Pgc entity ) throws ApplicationException
    {
        Query query = getEntityManager().createNamedQuery( PgcPenPage.deleteFromPgc );
        query.setParameter( 1, entity );
        query.executeUpdate();
    }
}
