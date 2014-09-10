
import au.edu.unsw.sltf.client.SummaryMarketDataServiceStub;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument.SummaryMarketData;
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
        
        SummaryMarketDataResponseDocument smdRespDoc = stub.summaryMarketData(smdReqDoc);
        SummaryMarketDataResponse smdResp = smdRespDoc.getSummaryMarketDataResponse();
        
        //return resp.getReturn();
        
        //TODO: do I have to abstract the response away from the client?
        //TODO: above doesn't work
   	 	StringBuilder sbf = new StringBuilder();
        sbf.append("EventSetId: ").append(smdResp.getEventSetId()).append("\n");
        sbf.append("Security code: ").append(smdResp.getSec()).append("\n");
        sbf.append("Start Date: ").append(smdResp.getStartDate()).append("\n");
        sbf.append("End Date: ").append(smdResp.getEndDate()).append("\n");
        sbf.append("Market Type: ").append(smdResp.getMarketType()).append("\n");
        sbf.append("Currency code: ").append(smdResp.getCurrencyCode()).append("\n");
        sbf.append("File size: ").append(smdResp.getFileSize()).append("\n");
           	 
		return sbf.toString();
    }
}

