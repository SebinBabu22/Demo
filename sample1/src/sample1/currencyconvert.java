package sample1;

import postilion.realtime.sdk.eventrecorder.AContext;
import postilion.realtime.sdk.eventrecorder.EventRecorder;
import postilion.realtime.sdk.eventrecorder.contexts.InterchangeContext;
import postilion.realtime.sdk.eventrecorder.events.IntegrationDriverConnectedToRemote;
import postilion.realtime.sdk.message.bitmap.Iso8583Post;
import postilion.realtime.sdk.node.AIntegrationDriver;
import postilion.realtime.sdk.node.AIntegrationDriverEnvironment;
import postilion.realtime.sdk.node.Action;
import postilion.realtime.sdk.util.TraceFile;

public class currencyconvert extends AIntegrationDriver{
	
	/*public void init(AIntegrationDriverEnvironment node_application, String integration_driver_parameters, String custom_class_parameters)
	throws Exception
	{
		EventRecorder.recordEvent(new IntegrationDriverConnectedToRemote(new AContext[]{
				                  new InterchangeContext(node_application.getName())}));    
		
	}*/
		@Override
	public Action processMsgFromRemote(AIntegrationDriverEnvironment node_application, Iso8583Post msg)
			throws Exception {
		// TODO Auto-generated method stub
			TraceFile trace = new TraceFile("C:\\temp_currency");
			trace.start();
			int msg_type = msg.getMsgType();
			trace.log("peocessMessageFromRemote msg_type [" + msg_type + "] ");
			if (Iso8583Post.MsgType._0110_AUTH_REQ_RSP == msg_type)
			{
				String curr_code = msg.getField(Iso8583Post.Bit._049_CURRENCY_CODE_TRAN);
				trace.log("In the if loop curr_code [" + curr_code + "]");
				if(curr_code.equals("356"))
				{
					curr_code = "840";
					String amnt_tran=msg.getField(Iso8583Post.Bit._004_AMOUNT_TRANSACTION);
					
						
					trace.log("In the if loop amnt_tran [" + amnt_tran + "]");
					int amount = Integer.parseInt(amnt_tran);
					  Integer amnt_tran_1 = amount*10;
					  trace.log("In the if loop amnt_tran_1 [" + amnt_tran_1 + "]");
					  msg.putField(Iso8583Post.Bit._004_AMOUNT_TRANSACTION, amnt_tran_1.toString());
					  msg.putField(Iso8583Post.Bit._049_CURRENCY_CODE_TRAN, curr_code );
					 
					 
				}
						
			}
		return super.processMsgFromRemote(node_application, msg);
	}
	
	@Override
	public Action processMessageFromTranmgrSourceNode(AIntegrationDriverEnvironment node_application, Iso8583Post msg)
		throws Exception {
		this.processMsgFromRemote(node_application,  msg);
	// TODO Auto-generated method stub
	return super.processMessageFromTranmgrSourceNode(node_application, msg);
	}

}
