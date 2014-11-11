package com.example.expandablelistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

/*I do - 11/09/14-13:26(-3GMT):
	* The lose of Status Ckeckeds of Header and Childs when Scroll de ListView
	* When click in a Header CheckBox - all childs of it, will be checked
	@Guihgo :{)*/

public class MainActivity extends ActionBarActivity {

	 ExpandableListAdapter listAdapter;
	    ExpandableListView expListView;
	    List<String> listDataHeader;
	    CheckBox chkChild,chkParent;
   	    HashMap<String, List<String>> listDataChild;
   	    Button btnSubmit;
   	    
   	    ArrayList<Boolean> isCheckedHeader;
   	    HashMap<String, List<Boolean>> listIsCheckedChild;
   	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        btnSubmit=(Button)findViewById(R.id.buttonSubmit);
	        
	        // get the listview
	        expListView = (ExpandableListView) findViewById(R.id.lstExpandable);
	 
	        // preparing list data
	        prepareListData();
	 
	        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, isCheckedHeader, listIsCheckedChild);
	 
	        // setting list adapter
	        expListView.setAdapter(listAdapter);
	    }
	 
	    /*
	     * Preparing the list data
	     */
	    private void prepareListData() {
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	        
	        
	        isCheckedHeader = new ArrayList<Boolean>();
	        listIsCheckedChild = new HashMap<String, List<Boolean>>(); //String will be the Header
	        
	        //(wrong) Adding child data - Hey... Here you adding Header Data
	        listDataHeader.add("India");
	        isCheckedHeader.add(false); //set false checked as default
	        listDataHeader.add("South Africa");
	        isCheckedHeader.add(false); 
	        listDataHeader.add("Australia");
	        isCheckedHeader.add(false);
	        
	        
	        // Adding child data !!! \\
	        
	        //INDIA
	        List<String> india = new ArrayList<String>();
	        List<Boolean> isCheckedIndia = new ArrayList<Boolean>();
	        
	        india.add("Sachin Tendulkar");
	        isCheckedIndia.add(false); //set false checked as default
	        india.add("Sourav Ganguly");
	        isCheckedIndia.add(false);
	        india.add("Virat Kohli");
	        isCheckedIndia.add(false);
	        india.add("Ishant Sharma");
	        isCheckedIndia.add(false);

	        //SOUTH AFRICA
	        List<String> southafrica = new ArrayList<String>();
	        List<Boolean> isCheckedSouthAfrica = new ArrayList<Boolean>();
	        
	        southafrica.add("AB Devilliers");
	        isCheckedSouthAfrica.add(false); //set false checked as default
	        southafrica.add("Hashim Amla");
	        isCheckedSouthAfrica.add(false);
	        southafrica.add("Dale Steyn");
	        isCheckedSouthAfrica.add(false);
	        
	        //AUSTRALIA
	        List<String> australia = new ArrayList<String>();
	        List<Boolean> isCheckedAustralia = new ArrayList<Boolean>();
	        
	        australia.add("Matthew Hayden");
	        isCheckedAustralia.add(false); //set false checked as default
	        australia.add("Ricky Ponting");
	        isCheckedAustralia.add(false);
	        australia.add("Glenn McGrath");
	        isCheckedAustralia.add(false);
	        australia.add("Shane warne");
	        isCheckedAustralia.add(false);
	        
	        
	        //So...	      
	        listDataChild.put(listDataHeader.get(0), india); // Header, Child data
	        listIsCheckedChild.put(listDataHeader.get(0), isCheckedIndia); //status of Checked Child
	        
	        listDataChild.put(listDataHeader.get(1), southafrica);
	        listIsCheckedChild.put(listDataHeader.get(1), isCheckedSouthAfrica);
	        
	        listDataChild.put(listDataHeader.get(2), australia);
	        listIsCheckedChild.put(listDataHeader.get(2), isCheckedAustralia);
	        
	        
	    }

}
