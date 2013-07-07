package in.wptrafficanalyzer.listviewwithxmlpullparser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class CountryXmlParser {
	
	private static final String ns = null;
	
	/** This is the only function need to be called from outside the class */
	public List<HashMap<String, String>> parse(Reader reader) 
    		throws XmlPullParserException, IOException{
    	try{    		
    		XmlPullParser parser = Xml.newPullParser();
    		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);    		
    		parser.setInput(reader);
    		parser.nextTag();    		
    		return readCountries(parser);    		
    	}finally{
    		
    	}
    }        
    
	/** This method read each country in the xml data and add it to List */
    private List<HashMap<String, String>> readCountries(XmlPullParser parser) 
    		throws XmlPullParserException,IOException{
    	
    	List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();    	
    	
    	parser.require(XmlPullParser.START_TAG, ns, "countries");
    	
    	while(parser.next() != XmlPullParser.END_TAG){    		
    		if(parser.getEventType() != XmlPullParser.START_TAG){
    			continue;
    		}
    		
    		String name = parser.getName();    		
    		if(name.equals("country")){
    			list.add(readCountry(parser));    			
    		}
    		else{
    			skip(parser);
    		}
    	}    	
    	return list;
    }
    
    /** This method read a country and returns its corresponding HashMap construct */
    private HashMap<String, String> readCountry(XmlPullParser parser) 
    		throws XmlPullParserException, IOException{
    	
    	parser.require(XmlPullParser.START_TAG, ns, "country");
    	
        String countryName = parser.getAttributeValue(ns, "name");
        String flag = parser.getAttributeValue(ns, "flag");
        String language="";
        String capital="";
        String currencyCode="";
        String currency="";
        
        while(parser.next() != XmlPullParser.END_TAG){
        	if(parser.getEventType() != XmlPullParser.START_TAG){
        		continue;
        	}
        	
        	String name = parser.getName();
    		
        	if(name.equals("language")){
        		language = readLanguage(parser);
        	}else if(name.equals("capital")){
        		capital = parser.getAttributeValue(ns, "city");
        		readCapital(parser);        		
        	}else if(name.equals("currency")){
        		currencyCode = parser.getAttributeValue(ns, "code");
        		currency = readCurrency(parser);
        	}else{
        		skip(parser);
        	}
        }
        
        String details = 	"Language : " + language + "\n" +
        		   			"Capital : " + capital + "\n" + 
        		   			"Currency : " + currency + "(" + currencyCode + ")";	            			

        
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("country", countryName);
        hm.put("flag", flag);
        hm.put("details",details);       
        
    	return hm;
    }
    
    /** Process language tag in the xml data */    
    private String readLanguage(XmlPullParser parser) 
    		throws IOException, XmlPullParserException {    
    	parser.require(XmlPullParser.START_TAG, ns, "language");
    	String language = readText(parser);
    	return language;
    }
    
    /** Process Capital tag in the xml data */    
    private void readCapital(XmlPullParser parser) 
    		throws IOException, XmlPullParserException {    
    	parser.require(XmlPullParser.START_TAG, ns, "capital");
    	parser.nextTag();
    }
    
    /** Process Currency tag in the xml data */    
    private String readCurrency(XmlPullParser parser) 
    		throws IOException, XmlPullParserException {    
    	parser.require(XmlPullParser.START_TAG, ns, "currency");
    	String currency = readText(parser);
    	return currency;
    }
    
    /** Getting Text from an element */
    private String readText(XmlPullParser parser) 
    		throws IOException, XmlPullParserException{
    	String result = "";
    	if(parser.next()==XmlPullParser.TEXT){
    		result = parser.getText();
    		parser.nextTag();
    	}
    	return result;    	
    }
    
    private void skip(XmlPullParser parser) 
    		throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
}
