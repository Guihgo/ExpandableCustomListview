package com.example.expandablelistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    
    private ArrayList<Boolean> _isCheckedHeader;
    private HashMap<String, List<Boolean>> _listIsCheckedChild;
    
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, ArrayList<Boolean> isCheckedHeader, 
            HashMap<String, List<Boolean>> listIsCheckedChild) 
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._isCheckedHeader = isCheckedHeader;
        this._listIsCheckedChild = listIsCheckedChild;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        
        final CheckBox chkChild=(CheckBox)convertView.findViewById(R.id.checkBoxchild);
             
        //HEREE !!!! Do this to update with a right status check...
        chkChild.setChecked(_listIsCheckedChild.get(this._listDataHeader.get(groupPosition)).get(childPosition));
        
        final int iGroupPos = groupPosition;
        final int iChildPos = childPosition;

        chkChild.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(chkChild.isChecked()) //the child was enable (checked), so the other of same header have to are non-checked
				{
					//unChecking all SubItem(Childs) of this Header less this chkChild
					int iSizeChilds = getChildrenCount(iGroupPos); //get the size of children of header
					Log.e("iSizeChilds", String.valueOf(iSizeChilds));
					for(int i = 0; i<iSizeChilds; i++) //for each child 
					{
						//disable all children
						_listIsCheckedChild.get(_listDataHeader.get(iGroupPos)).set(i, false);
					}
					
					
					//And enable that i want
					_listIsCheckedChild.get(_listDataHeader.get(iGroupPos)).set(iChildPos, true);
					
					//Update the Childs Views... 
					notifyDataSetChanged();
					
					Toast.makeText(_context, "Child "+iChildPos+" \r\nof Header "+iGroupPos+" was enabled", Toast.LENGTH_SHORT).show();
				}
				else //the child was disable (non-checked)
				{
					_listIsCheckedChild.get(_listDataHeader.get(iGroupPos)).set(iChildPos, false);
					
					Toast.makeText(_context, "Child "+iChildPos+" \r\nof Header "+iGroupPos+" was disable", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
    	
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
       
        convertView.setSelected(false);
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        final CheckBox chkParent=(CheckBox)convertView
        		.findViewById(R.id.checkBoxparent);

        //HEREE !!!! Do this to update with a right status check...
        chkParent.setChecked(_isCheckedHeader.get(groupPosition));
       
        final int iPos = groupPosition;
    
        //Don't use this (setOnCheckedChangeListener)...
        chkParent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
					//Don't use this...
			}
		});
        
        chkParent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (chkParent.isChecked()) //OnClick enable it,we have to update the _isCheckedHeader
				{
					//READ: the main code first, and after the notifications(toast, log)...
					_isCheckedHeader.set(iPos, true);
					
					//Checking all SubItem(Childs) of this
					int iSizeChilds = getChildrenCount(iPos);
					Log.e("iSizeChilds", String.valueOf(iSizeChilds));
					for(int i = 0; i<iSizeChilds; i++)
					{
						_listIsCheckedChild.get(_listDataHeader.get(iPos)).set(i, true);
						Log.i("Child "+i, "was setted to "+_listIsCheckedChild.get(_listDataHeader.get(iPos)).get(i));
					}
					
					Toast.makeText(_context, "Header "+iPos+ " was enabled(checked)", Toast.LENGTH_SHORT).show();
					Log.d("Header", iPos+ "was enabled(checked)"); //i recommend that use Log to see a developping info... 
				}
				else
				{
					_isCheckedHeader.set(iPos, false);
					
					//NON-Checking all SubItem(Childs) of this
					int iSizeChilds = getChildrenCount(iPos);
					for(int i = 0; i<iSizeChilds; i++)
					{
						_listIsCheckedChild.get(_listDataHeader.get(iPos)).set(i, false);
						Log.i("Child "+i, "was setted to "+_listIsCheckedChild.get(_listDataHeader.get(iPos)).get(i));
					}
					
					Toast.makeText(_context, "Header "+iPos+ " was disable(non-checked)", Toast.LENGTH_SHORT).show();
					Log.d("Header", iPos+ "was disable(non-checked)");
				}
				
				//Update the Childs Views... 
				notifyDataSetChanged();
			}
		});
        
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}