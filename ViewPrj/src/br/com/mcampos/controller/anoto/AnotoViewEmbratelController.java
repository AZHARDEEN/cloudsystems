package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.base.BaseSearchEmbratelController;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.io.File;
import java.io.IOException;

import java.util.List;

import jxl.Workbook;

import jxl.write.DateTime;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;


public class AnotoViewEmbratelController extends BaseSearchEmbratelController
{

    public AnotoViewEmbratelController( char c )
    {
        super( c );
    }

    public AnotoViewEmbratelController()
    {
        super();
    }

    public void onClick$btnEmbratelExport()
    {
        File file;
        try {
            file = File.createTempFile( "export", ".xls" );
            file.deleteOnExit();
            WritableWorkbook workbook = Workbook.createWorkbook( file );
            writeToExcell( workbook );
            Filedownload.save( file, "application/vnd.ms-excel" );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onClick$btnEmbratelExportAll()
    {
        File file;
        try {
            file = File.createTempFile( "export", ".xls" );
            file.deleteOnExit();
            WritableWorkbook workbook = Workbook.createWorkbook( file );
            writeToExcell2( workbook );
            Filedownload.save( file, "application/vnd.ms-excel" );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    private void writeToExcell( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
    {
        WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
        int nColumns = setHeader( sheet );
        ListModelList model = getModel();
        boolean bFirst = true;
        for ( int nIndex = 0; nIndex < model.getSize(); nIndex++ ) {
            AnotoResultList dto = ( AnotoResultList )model.get( nIndex );
            if ( dto != null ) {
                if ( bFirst ) {
                    bFirst = false;
                    addHead( sheet, nColumns, dto.getFields() );
                }
                nColumns = 0;
                sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex + 1 ) );
                sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage().getPgc().getInsertDate() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach() ? "SIM" : "" ) );
                addData( sheet, nColumns, nIndex + 1, dto.getFields() );
            }
        }
        workbook.write();
        workbook.close();
    }

    private void writeToExcell2( WritableWorkbook workbook ) throws WriteException, RowsExceededException, IOException
    {
        WritableSheet sheet = workbook.createSheet( "Exported Data", 0 );
        int nColumns = setHeader2( sheet );
        ListModelList model = getModel();
        boolean bFirst = true;
        List<PgcFieldDTO> fields;
        for ( int nIndex = 0; nIndex < model.getSize(); nIndex++ ) {
            AnotoResultList dto = ( AnotoResultList )model.get( nIndex );
            if ( dto != null ) {
                try {
                    fields = getSession().getFields( getLoggedInUser(), dto.getPgcPage() );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage() );
                    return;
                }
                if ( bFirst ) {
                    bFirst = false;
                    addHead( sheet, nColumns, fields );
                }
                nColumns = 0;
                sheet.addCell( new Number( nColumns++, nIndex + 1, nIndex + 1 ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getForm().toString() ) );
                sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage().getBookId() + 1 ) );
                sheet.addCell( new Number( nColumns++, nIndex + 1, dto.getPgcPage().getPageId() + 1 ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getPen().toString() ) );
                sheet.addCell( new DateTime( nColumns++, nIndex + 1, dto.getPgcPage().getPgc().getInsertDate() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getUserName() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getEmail() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getCellNumber() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLatitude() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getLongitude() ) );
                sheet.addCell( new jxl.write.Label( nColumns++, nIndex + 1, dto.getAttach() ? "SIM" : "" ) );
                addData( sheet, nColumns, nIndex + 1, fields );
            }
        }
        workbook.write();
        workbook.close();
    }


    private void addHead( WritableSheet sheet, int nColumns, List<PgcFieldDTO> fields ) throws WriteException,
                                                                                               RowsExceededException
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        for ( PgcFieldDTO field : fields ) {
            sheet.addCell( new jxl.write.Label( nColumns++, 0, field.getName() ) );
        }
    }

    private void addData( WritableSheet sheet, int nColumn, int nRow, List<PgcFieldDTO> fields ) throws WriteException,
                                                                                                        RowsExceededException
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        for ( PgcFieldDTO field : fields ) {
            sheet.addCell( new jxl.write.Label( nColumn++, nRow, field.getValue() ) );
        }
    }
}
