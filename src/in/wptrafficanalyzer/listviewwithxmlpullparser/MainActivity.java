package in.wptrafficanalyzer.listviewwithxmlpullparser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /** This is the XML data to be parsed */
        String xmlData = "<countries>" +
        
	        				"<country name='India' flag='"+Integer.toString(R.drawable.india) +"'>" +	        					
	        					"<language>Hindi</language>" +
	        				 	"<capital city='New Delhi' />" +
	        				 	"<currency code='INR'>Indian Rupee</currency>" +
	        				"</country>" +
	        				 	
							"<country name='Pakistan' flag='"+Integer.toString(R.drawable.pakistan) +"'>" +
								"<language>Urdu</language>" +
								"<capital city='Islamabad' />" +
								"<currency code='PKR'>Pakistani Rupee</currency>" +
							"</country>" +
								
							"<country name='Sri Lanka' flag='"+Integer.toString(R.drawable.srilanka) +"'>" + 
								"<language>Sinhala</language>" +
								"<capital city='Sri Jayawardenapura Kotte' />" +
								"<currency code='LKR'>Sri Lankan Rupee</currency>" +
							"</country>" +
							
							"<country name='China' flag='"+Integer.toString(R.drawable.china) +"'>" +
								"<language>Chineese</language>" +
								"<capital city='Beijing' />" +
								"<currency code='CNY'>Renminbi</currency>" +
							"</country>" +
							
							"<country name='Bangladesh' flag='"+Integer.toString(R.drawable.bangladesh) +"'>" +
								"<language>Bangla</language>" +
								"<capital city='Dhaka' />" +
								"<currency code='BDT'>Taka</currency>" +
							"</country>" +
								
							"<country name='Nepal' flag='"+Integer.toString(R.drawable.nepal) +"'>" +
								"<language>Nepal Bhasa</language>" +
								"<capital city='Kathmandu' />" +
								"<currency code='NPR'>Nepalese rupee</currency>" +
							"</country>" +
								
							"<country name='Afghanistan' flag='"+Integer.toString(R.drawable.afghanistan) +"'>" +
								"<language>Dari Persian</language>" +
								"<capital city='Kabul' />" +
								"<currency code='AFN'>Afghani</currency>" +
							"</country>" +
								
							"<country name='North Korea' flag='"+Integer.toString(R.drawable.nkorea) +"'>" +
								"<language>Korean</language>" +
								"<capital city='Pyongyang' />" +
								"<currency code='KPW'>North Korean won</currency>" +
							"</country>" +
							
							"<country name='South Korea' flag='"+Integer.toString(R.drawable.skorea) +"'>" +
								"<language>Korean</language>" +
								"<capital city='Seoul' />" +
								"<currency code='KRW'>South Korean won</currency>" +
							"</country>" +
        				 
							"<country name='Japan' flag='"+Integer.toString(R.drawable.japan) +"'>" +
								"<language>Japanese</language>" +
								"<capital city='Tokyo' />" +
								"<currency code='JPY'>Yen</currency>" +
							"</country>" +	        				 

        				 "</countries>";
        
        /** The parsing of the xml data is done in a non-ui thread */
        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
        
        /** Start parsing xml data */
        listViewLoaderTask.execute(xmlData);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
        
    
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{

    	/** Doing the parsing of xml data in a non-ui thread */
		@Override
		protected SimpleAdapter doInBackground(String... xmlData) {
			StringReader reader = new StringReader(xmlData[0]);
			
			CountryXmlParser countryXmlParser = new CountryXmlParser();
			
	        List<HashMap<String, String>> countries = null;
	        
	        try{
	        	/** Getting the parsed data as a List construct */
	        	countries = countryXmlParser.parse(reader);
	        }catch(Exception e){
	        	Log.d("Exception",e.toString());
	        }	       

	        /** Keys used in Hashmap */
	        String[] from = { "country","flag","details"};

	        /** Ids of views in listview_layout */
	        int[] to = { R.id.tv_country,R.id.iv_flag,R.id.tv_country_details};

	        /** Instantiating an adapter to store each items
	        *  R.layout.listview_layout defines the layout of each item
	        */
	        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.lv_layout, from, to);  
	        
			return adapter;
		}
		
		
		@Override
		protected void onPostExecute(SimpleAdapter adapter) {
	        
			/** Getting a reference to listview of main.xml layout file */
	        ListView listView = ( ListView ) findViewById(R.id.lv_countries);
	        
	        /** Setting the adapter containing the country list to listview */
	        listView.setAdapter(adapter);
		}		
    }
}