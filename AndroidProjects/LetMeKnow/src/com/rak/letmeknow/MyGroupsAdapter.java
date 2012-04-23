package com.rak.letmeknow;

import java.util.ArrayList;

import com.rak.letmeknow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyGroupsAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<String> groups;
	private ArrayList<ArrayList<Group>> mygroups;
	private LayoutInflater inflater;

	public MyGroupsAdapter(Context context, 
			ArrayList<String> groups,
			ArrayList<ArrayList<Group>> mygroups ) { 
		this.context = context;
		this.groups = groups;
		this.mygroups = mygroups;
		inflater = LayoutInflater.from( context );
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mygroups.get( groupPosition ).get( childPosition );
	}

	public long getChildId(int groupPosition, int childPosition) {
		return (long)( groupPosition*1024+childPosition );  // Max 1024 children per group
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View v = null;
		if( convertView != null )
			v = convertView;
		else
			v = inflater.inflate(R.layout.child_row, parent, false); 

		Group c = (Group)getChild( groupPosition, childPosition );
		TextView event = (TextView)v.findViewById( R.id.childname );
		if( event != null )
			event.setText( c.getEvent() );

		CheckBox cb = (CheckBox)v.findViewById( R.id.check1 );
		cb.setChecked( c.getState() );
		return v;
	}

	public void changeClickStatus(View v) {
		System.out.println(v.getClass());
	}

	public int getChildrenCount(int groupPosition) {
		return mygroups.get( groupPosition ).size();
	}

	public Object getGroup(int groupPosition) {
		return groups.get( groupPosition );        
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return (long)( groupPosition*1024 );  // To be consistent with getChildId
	} 

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View v = null;
		if( convertView != null )
			v = convertView;
		else
			v = inflater.inflate(R.layout.group_row, parent, false); 
		String gt = (String)getGroup( groupPosition );
		TextView eventGroup = (TextView)v.findViewById( R.id.childname );
		if( gt != null )
			eventGroup.setText( gt );
		return v;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	} 

	public void onGroupCollapsed (int groupPosition) {

	} 

	public void onGroupExpanded(int groupPosition) {

	}


}
