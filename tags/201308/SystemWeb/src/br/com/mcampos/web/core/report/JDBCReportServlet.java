package br.com.mcampos.web.core.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JDBCReportServlet
 */
@WebServlet( urlPatterns = { JDBCReportServlet.reportUrl } )
public class JDBCReportServlet extends BaseReportServlet
{
	private static final long serialVersionUID = -9032073228600422396L;
	public static final String reportUrl = "/jreport";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JDBCReportServlet( )
	{
		super( );
	}

	@Override
	protected void doGetPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		ReportItem item = getReportItem( request );
		if ( item == null ) {
			return;
		}
		switch ( item.getReportFormat( ) ) {
		case 1:
			exportToPDF( request, response );
			break;
		case 2:
			exportToXML( request, response );
			break;
		case 3:
			exportToHTML( request, response );
			break;
		case 4:
			exportToXLS( request, response );
			break;
		case 5:
			exportToRTF( request, response );
			break;
		case 6:
			exportToCsv( request, response );
			break;
		case 7:
			exportToTxt( request, response );
			break;
		case 8:
			exportToODS( request, response );
			break;
		case 9:
			exportToOdt( request, response );
			break;
		}
	}
}
