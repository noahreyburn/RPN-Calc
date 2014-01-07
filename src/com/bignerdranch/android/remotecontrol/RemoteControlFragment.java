package com.bignerdranch.android.remotecontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RemoteControlFragment extends Fragment {
	private TextView mSelectTextView;
	private TextView mWorkingTextView;
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate( R.layout.fragment_remote_control, parent, false );
		
		mSelectTextView = (TextView)v.findViewById( R.id.fragment_remote_control_selectedTextView );
		mSelectTextView.setTextAlignment( 5 ); //viewStart
		
		mWorkingTextView = (TextView)v.findViewById( R.id.fragment_remote_control_workingTextView );
		
		View.OnClickListener numberButtonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textView = (TextView)v;
				String working = mSelectTextView.getText().toString(); // gets current display string
				String text = textView.getText().toString(); // gets string value of number pressed
				
				
				if (working.equals("0")) {
					mSelectTextView.setText("\n" + text);
				} else {
					mSelectTextView.setText( working + text );
				}
			}
		};
		
		
		TableLayout tableLayout = (TableLayout)v.findViewById( R.id.fragment_remote_control_tableLayout );
		
		int number = 1;
		for ( int i = 4; i < tableLayout.getChildCount() - 1; i++ ) {
			TableRow row = (TableRow)tableLayout.getChildAt(i);
			for ( int j = 0; j < row.getChildCount(); j++) {
				Button button = (Button)row.getChildAt(j);
				button.setText( "" + number );
				button.setOnClickListener( numberButtonListener );
				number++;
			}
		}
		
		TableRow topRow = (TableRow)tableLayout.getChildAt( 2 );
		Button clearButton = (Button)topRow.getChildAt( 0 );
		clearButton.setText("AC");
		clearButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSelectTextView.setText("0");
				
			}
		});
		
		Button inverseButton = (Button)topRow.getChildAt( 1 );
		inverseButton.setText( "-/+" );
		inverseButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String working = mWorkingTextView.getText().toString();
				float workingFloat = Float.valueOf( working );
				workingFloat *= -1;
				mWorkingTextView.setText( Float.toString( workingFloat ) );
			}
		});
		
		Button plusButton = (Button)topRow.getChildAt( 2 );
		plusButton.setText("+");
		plusButton.setTextAppearance(getActivity(), R.style.RemoteButton_ActionButton);
		plusButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String working = mWorkingTextView.getText().toString();
				String selected = mSelectTextView.getText().toString();
				float addition = Float.valueOf(selected) + Float.valueOf(working);
				mSelectTextView.setText( "" + addition );
				mWorkingTextView.setText("");
			}
		});
		
		TableRow bottomRow = (TableRow)tableLayout.getChildAt(tableLayout.getChildCount() - 1 );
		Button deleteButton = (Button)bottomRow.getChildAt( 0 ); // lower left hand corner
		deleteButton.setText("Delete");
		deleteButton.setTextAppearance(getActivity(), R.style.RemoteButton_ActionButton );
		deleteButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String working = mWorkingTextView.getText().toString();
				if ( working.length() <= 0 ) return;
				working = working.substring(0, working.length() - 1 );
				mWorkingTextView.setText(working);
			}
		});
		
		Button zeroButton = (Button)bottomRow.getChildAt( 1 );
		zeroButton.setText("0");
		zeroButton.setOnClickListener( numberButtonListener );
			
		Button enterButton = (Button)bottomRow.getChildAt( 2 );
		enterButton.setText("Enter");
		enterButton.setTextAppearance(getActivity(), R.style.RemoteButton_ActionButton );
		enterButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CharSequence working = mWorkingTextView.getText();
				if (working.length() > 0 )
					mSelectTextView.setText( working );
				mWorkingTextView.setText( "0" );
			}
		});
		return v;
	}
	
}
