import java.rmi.RemoteException;
import java.util.Calendar;

import org.apache.axis2.AxisFault;

import au.edu.unsw.sltf.client.SummaryMarketDataFaultException;
import au.edu.unsw.sltf.client.SummaryMarketDataServiceStub;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument.SummaryMarketData;
import au.edu.unsw.sltf.services.SummaryMarketDataFaultDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataFaultDocument.SummaryMarketDataFault;
import au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument.SummaryMarketDataResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web service client that connects to the TopDownSimpleServices
 * Web service.
 */
public class SummaryDataWebServiceClient {

    public static SummaryMarketDataResponse main(String[] args) {
    	String wsURL = "http://hvee350.srvr:8080/axis2/services/SummaryMarketDataService";
       
            SummaryMarketDataServiceStub stub;
			try {
				stub = new SummaryMarketDataServiceStub(wsURL);
				try {
					return (callImportMarketDataOperation(stub,args[0]));
				} catch (SummaryMarketDataFaultException ex) {
		            ex.printStackTrace();
		            SummaryMarketDataFaultDocument smdFaultDoc = ex.getFaultMessage();
		            SummaryMarketDataFault smdFault = smdFaultDoc.getSummaryMarketDataFault();
		        } catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (AxisFault e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
		return null;
    }

    private static SummaryMarketDataResponse callImportMarketDataOperation(SummaryMarketDataServiceStub stub,String eventSetId) throws Exception {
        SummaryMarketDataDocument smdReqDoc = SummaryMarketDataDocument.Factory.newInstance();
        SummaryMarketData smdReq = smdReqDoc.addNewSummaryMarketData();
        smdReq.setEventSetId(eventSetId);
        
    	SummaryMarketDataResponseDocument smdRespDoc = stub.summaryMarketData(smdReqDoc);
        SummaryMarketDataResponse smdResp = smdRespDoc.getSummaryMarketDataResponse();
        return smdResp;
    
    }
    
    private static String stringifyDate(Calendar time) {
		StringBuilder sbf = new StringBuilder();
		sbf.append(time.get(Calendar.YEAR)).append("-");
		sbf.append((time.get(Calendar.MONTH) + 1)).append("-");
		sbf.append(time.get(Calendar.DATE));
		sbf.append("T");
		sbf.append(time.get(Calendar.HOUR_OF_DAY)).append(":");
		sbf.append(time.get(Calendar.MINUTE)).append(":");
		sbf.append(time.get(Calendar.SECOND)).append(".");
		sbf.append(time.get(Calendar.MILLISECOND));
		
		return sbf.toString();
	}
}