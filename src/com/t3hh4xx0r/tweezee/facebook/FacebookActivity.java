package com.t3hh4xx0r.tweezee.facebook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.t3hh4xx0r.tweezee.MainActivity;
import com.t3hh4xx0r.tweezee.R;
import com.t3hh4xx0r.tweezee.sms.EntriesAdapter;

public class FacebookActivity extends SherlockListActivity {
	ListView lv1;
	Button mAddEntry;
	static EntriesAdapter a;
	static ArrayList<String> mEntries;
	TextView userTV;
	String userID;
	String userN;
	ImageView userPic;

	private static final String FACEBOOK_APPID = "405018012875515";
	private static final String FACEBOOK_PERMISSION = "publish_stream";
	private FacebookConnector facebookConnector;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this, getApplicationContext(), new String[] {FACEBOOK_PERMISSION});
		setContentView(R.layout.facebook_main);
	    lv1 = (ListView) findViewById(android.R.id.list);   
	    userTV = (TextView) findViewById(R.id.user);
	    userPic = (ImageView) findViewById(R.id.userPic);
		checkLogin();
//        lv1.setOnItemLongClickListener(new OnItemLongClickListener() {
//        	@Override
//        	public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {           	
//    			ArrayList<String> mMessages = new ArrayList<String>();
//    			ArrayList<String> mRecipients = new ArrayList<String>();
//    			DBAdapter db = new DBAdapter(v.getContext());
//    		    db.open();
//    		    Cursor c = db.getAllSEntries();
//    		    	try {
//    		       		while (c.moveToNext()) {
//    		       			mMessages.add(c.getString(c.getColumnIndex("message")));
//    		       			mRecipients.add(c.getString(c.getColumnIndex("send_to")));
//    		       		}
//    		       	 } catch (Exception e1) {}
//    		   c.close();
//    		   db.close();
//    		   
//    		   final Vibrator vibe = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE) ;
//        	   vibe.vibrate(50);
//               String message = mMessages.get(position); 
//               String recipient = mRecipients.get(position); 
//               BetterPopupWindowS dw = new BetterPopupWindowS.DemoPopupWindow(v, message, recipient, position);
//               dw.showLikeQuickAction(0, 30);
//               return false;
//        	}
//        });
        
        mAddEntry = (Button) findViewById(R.id.entry_b);
//        mAddEntry.setOnClickListener(new OnClickListener() {
//        	@Override
//        	public void onClick(View v) {
//        		Intent i = new Intent(SMSActivity.this, EntryAddS.class);
//        		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        		i.putExtra("editing", false);
//        		startActivity(i);
//        	}
//        });	   
	}

	private void checkLogin() {
		if (!facebookConnector.getFacebook().isSessionValid()) {
            startActivity(new Intent(this, FacebookSplash.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		} else {
			getUserInfo();
		}
	}

	private void getUserInfo() {
		try {
			String userReq = facebookConnector.getFacebook().request("me");
			JSONObject o = Util.parseJson(userReq);
			userN = o.optString("name", "Error retrieving username");
			userID = o.optString("id");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		userTV.setText(userN);
		Thread thread = new Thread() {
			Drawable p;
		    @Override
		    public void run() {
		    	 p = getPic(userID);		    			    
		         runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
		            	   userPic.setImageDrawable(p);
		               }
		         });		    			    		    	
		    }
		};
		thread.start();
	}

	private Drawable getPic(String id) {
		Drawable d = null;
		Resources res = getResources();
		try {
			URL imgUrl = new URL("http://graph.facebook.com/"+id+"/picture?type=large");
			Bitmap imgB = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
			d = new BitmapDrawable(imgB);
		} catch (MalformedURLException e) {
			d = res.getDrawable(R.drawable.acct_sel);
			e.printStackTrace();
		} catch (IOException e) {
			d = res.getDrawable(R.drawable.acct_sel);
			e.printStackTrace();
		}
		return d;
	}

	public void onListItemClick(ListView lv, View v, int p, long id) {	
//		ArrayList<String> mMessages = new ArrayList<String>();
//		ArrayList<String> mRecipients = new ArrayList<String>();
//		ArrayList<String> mIntervals = new ArrayList<String>();
//		ArrayList<String> mDays = new ArrayList<String>();
//		ArrayList<String> mTimes = new ArrayList<String>();
//		ArrayList<String> mBoots = new ArrayList<String>();
//		ArrayList<String> mDates = new ArrayList<String>();
//		String m = null;
//		String r = null;
//		String i = null;
//		String d = null;
//		String t = null;
//		String date = null;
//		String boot = null;
//		DBAdapter db = new DBAdapter(v.getContext());
//	    db.open();
//	    Cursor c = db.getAllSEntries();
//	    	try {
//	       		while (c.moveToNext()) {
//	       			mMessages.add(c.getString(c.getColumnIndex("message")));
//	       			mRecipients.add(c.getString(c.getColumnIndex("send_to")));
//	       			mIntervals.add(c.getString(c.getColumnIndex("send_wait")));
//	       			mDays.add(c.getString(c.getColumnIndex("send_day")));
//	       			mTimes.add(c.getString(c.getColumnIndex("send_time")));
//	       			mBoots.add(c.getString(c.getColumnIndex("start_boot")));
//       				mDates.add(c.getString(c.getColumnIndex("send_date")));
//	       		}
//	       	 } catch (Exception e1) {}
//	    c.close();
//	    db.close();		
//	    r = mRecipients.get(p);
//	    m = mMessages.get(p);
//	    i = mIntervals.get(p);
//	    d = mDays.get(p);
//	    date = mDates.get(p);
//	    t = mTimes.get(p);
//	    boot = mBoots.get(p);
//        Bundle b = new Bundle();
//        b.putBoolean("editing", true);
//        b.putInt("pos", p);
//        b.putString("message", m);
//        b.putString("interval", i);
//        b.putString("recipient", r);
//        b.putString("days", d);
//        b.putString("boot", boot);
//        if (t.length()>1) {
//        	b.putString("time", t);
//        } else {
//        	b.putString("time", null);
//        }
//        if (date.length()>1) {
//        	b.putString("date", date);
//        }
//        Intent mi = new Intent(v.getContext(), EntryAddS.class);
//        mi.putExtras(b);
//        startActivity(mi);	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		populateList(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		populateList(this);
	}
	
	void populateList(Context ctx) {
//		mEntries = new ArrayList<String>();
//		if (mEntries.size() != 0) {
//			mEntries.clear();
//		}
//		a = new EntriesAdapter(SMSActivity.this, mEntries);
//	    lv1.setAdapter(a);
//
//		DBAdapter db = new DBAdapter(ctx);
//	    db.open();
//	    Cursor cu = db.getAllSEntries();
//	    	try {
//	       		while (cu.moveToNext()) {
//	       			StringBuilder sB = new StringBuilder();
//	       			sB.append(cu.getString(cu.getColumnIndex("message")));
//	       			sB.append(":");
//	       			sB.append(cu.getString(cu.getColumnIndex("send_to")));
//	       			mEntries.add(sB.toString());
//	       		}
//	       	 } catch (Exception e1) {}
//	   cu.close();
//	   db.close();
//	   
//	   a.notifyDataSetChanged();
	   
	}
	
	 public static Handler handy = new Handler() {
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:			
				   a.notifyDataSetChanged();
				break;
			}
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {	   
	        case android.R.id.home:
	            Intent hi = new Intent(this, MainActivity.class);
	            hi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(hi);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
}