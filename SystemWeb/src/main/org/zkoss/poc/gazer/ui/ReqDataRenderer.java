package org.zkoss.poc.gazer.ui;

import org.zkoss.poc.gazer.data.ReqDataCarrier;
import org.zkoss.poc.util.TimeFormatter;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class ReqDataRenderer implements RowRenderer<ReqDataCarrier>
{
	@Override
	public void render( Row row, ReqDataCarrier reqData, int index ) throws Exception
	{
		new Label( reqData.getReqId( ) ).setParent( row );
		new Label( "" + reqData.getServerProcessDuration( ) ).setParent( row );
		new Label( "" + reqData.getClientRenderingDuration( ) ).setParent( row );
		new Label( "" + reqData.getNetworkLatency( ) ).setParent( row );
		new Label( "" + TimeFormatter.timeConverter( reqData.getTime( 1 ) ) ).setParent( row );
	}

}
