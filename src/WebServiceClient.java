
import java.util.Calendar;

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
public class WebServiceClient {

    public static void main(String[] args) {
        String wsURL = "http://localhost:8080/axis2/services/SummaryMarketData";
        try {
            SummaryMarketDataServiceStub stub = new SummaryMarketDataServiceStub(wsURL);
            System.out.println("The output of summaryMarketData operation is: ");
            System.out.println(callImportMarketDataOperation(stub));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String callImportMarketDataOperation(SummaryMarketDataServiceStub stub) throws Exception {
        SummaryMarketDataDocument smdReqDoc = SummaryMarketDataDocument.Factory.newInstance();
        SummaryMarketData smdReq = smdReqDoc.addNewSummaryMarketData();
        smdReq.setEventSetId("3");
        
        StringBuilder sbf = new StringBuilder();
        try {
        	SummaryMarketDataResponseDocument smdRespDoc = stub.summaryMarketData(smdReqDoc);
            SummaryMarketDataResponse smdResp = smdRespDoc.getSummaryMarketDataResponse();
            
            sbf.append("EventSetId: ").append(smdResp.getEventSetId()).append("\n");
            sbf.append("Security code: ").append(smdResp.getSec()).append("\n");
            sbf.append("Start Date: ").append(stringifyDate(smdResp.getStartDate())).append("\n");
            sbf.append("End Date: ").append(stringifyDate(smdResp.getEndDate())).append("\n");
            sbf.append("Market Type: ").append(smdResp.getMarketType()).append("\n");
            sbf.append("Currency code: ").append(smdResp.getCurrencyCode()).append("\n");
            sbf.append("File size: ").append(smdResp.getFileSize()).append("\n");
        } catch (SummaryMarketDataFaultException se) {
        	SummaryMarketDataFaultDocument faultDoc = se.getFaultMessage();
        	SummaryMarketDataFault fault = faultDoc.getSummaryMarketDataFault();
        	au.edu.unsw.sltf.services.SummaryMarketDataFaultType.Enum faultType = fault.getFaultType();
        	String faultMsg = fault.getFaultMessage();
        	
        	sbf.append("Fault type: ").append(faultType).append("\n");
        	sbf.append("Fault message: ").append(faultMsg).append("\n");
        }
        
		return sbf.toString();
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

