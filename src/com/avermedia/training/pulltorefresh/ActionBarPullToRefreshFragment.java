package com.avermedia.training.pulltorefresh;

import java.util.ArrayList;

import com.avermedia.training.pulltorefresh.PullToRefreshBase.OnRefreshListener;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActionBarPullToRefreshFragment extends Fragment {
	private static final int ITEM_COUNT = 20;
	private ActionBarPullToRefreshListView mPullToRefreshListView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment2, container, false);
		mPullToRefreshListView = (ActionBarPullToRefreshListView) v.findViewById(R.id.pull_refresh_list	);
		
		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(IPullToRefresh<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				if(refreshView instanceof PullToRefreshBase) {
					((PullToRefreshBase<ListView>)refreshView).getHeaderLayout().setLastUpdatedLabel(label);
				}

				// Do work to refresh the list here.
				new RefreshDataTask().execute();
			}
		});

		ListView actualListView = mPullToRefreshListView.getRefreshableView();
		actualListView.setAdapter(new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, getItems()));
				
				
		return v;
	}
	
	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for(int i = 0; i < ITEM_COUNT; i++) {
			items.add(i);
		}
		
		return items;
	}
	
	private class RefreshDataTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			publishProgress();
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer...integers) {
			if(null != getActivity()) {
				mPullToRefreshListView.getRefreshableView().setAdapter(
						new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, getItems()));
			}
		}
		
		@Override
		protected void onPostExecute(Void v) {
			mPullToRefreshListView.onRefreshComplete();
		}
		
	}
}
