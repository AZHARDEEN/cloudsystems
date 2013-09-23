package org.zkoss.poc.gazer.ui;

import org.zkoss.poc.gazer.ctrl.PfmMonitor;
import org.zkoss.poc.gazer.data.EventInfo;
import org.zkoss.poc.gazer.data.ReqDataCarrier;
import org.zkoss.poc.util.TimeFormatter;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class StatsRenderer implements RowRenderer<EventInfo>
{

	@Override
	public void render( Row row, EventInfo eventData, int index ) throws Exception
	{
		ReqDataCarrier reqData =
				PfmMonitor.getReqDataInst
						( PfmMonitor.getRecord( eventData.getReqPath( ) ), eventData.getReqId( ) );

		new Label( eventData.getTargetCompName( ) ).setParent( row );
		new Label( eventData.getEventName( ) ).setParent( row );
		new Label( "" + eventData.getEventProcTime( ) ).setParent( row );
		new Label( "" + reqData.getServerProcessDuration( ) ).setParent( row );
		new Label( "" + reqData.getClientRenderingDuration( ) ).setParent( row );
		new Label( "" + reqData.getNetworkLatency( ) ).setParent( row );
		new Label( "" + eventData.getReqId( ) ).setParent( row );
		new Label( "" + TimeFormatter.timeConverter( eventData.getStartProcTime( ) ) ).setParent( row );
	}
}
