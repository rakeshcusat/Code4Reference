package com.rak.letmeknow;

import java.util.ArrayList;

import com.rak.letmeknow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	Context context;
	ArrayList<Event> eventList;

	public MyListAdapter(Context context, 
			ArrayList<Event> eventList) { 
		this.context = context;
		this.eventList = eventList;
		inflater = LayoutInflater.from( context );
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return eventList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return eventList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	public View getView(int position, View convertView, ViewGroup parent) {

		View v = null;
		if( convertView != null )
			v = convertView;
		else
			v = inflater.inflate(R.layout.child_event, null); 
		Event e = (Event)getItem( position );

		TextView id = (TextView)v.findViewById( R.id.child);
		if( id != null )
			id.setText(e.getSubject());
		TextView event = (TextView)v.findViewById( R.id.childname2 );
		if( event != null )
			event.setText( e.getSenderEmail() );
		return v;

	}

}
