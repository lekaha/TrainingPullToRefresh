package com.avermedia.training.pulltorefresh;

import com.avermedia.training.pulltorefresh.PullToRefreshBase.OnPullEventListener;
import com.avermedia.training.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.avermedia.training.pulltorefresh.PullToRefreshBase.State;

import android.view.View;
import android.view.animation.Interpolator;

public interface IPullToRefresh<T extends View> {

	/**
	 * Returns whether the Touch Events are filtered or not. If true is
	 * returned, then the View will only use touch events where the difference
	 * in the Y-axis is greater than the difference in the X-axis. This means
	 * that the View will not interfere when it is used in a horizontal
	 * scrolling View (such as a ViewPager).
	 * 
	 * @return boolean - true if the View is filtering Touch Events
	 */
	public boolean getFilterTouchEvents();

	/**
	 * Get the Wrapped Refreshable View. Anything returned here has already been
	 * added to the content view.
	 * 
	 * @return The View which is currently wrapped
	 */
	public T getRefreshableView();

	/**
	 * Get whether the 'Refreshing' View should be automatically shown when
	 * refreshing. Returns true by default.
	 * 
	 * @return - true if the Refreshing View will be show
	 */
	public boolean getShowViewWhileRefreshing();

	/**
	 * @return - The state that the View is currently in.
	 */
	public State getState();
	
	/**
	 * Set Pull-to-Refresh is enabled or not.
	 * 
	 * @param enabled
	 */
	public void setPullToRefreshEnable(boolean enable);

	/**
	 * Whether Pull-to-Refresh is enabled
	 * 
	 * @return enabled
	 */
	public boolean isPullToRefreshEnabled();

	/**
	 * Gets whether Overscroll support is enabled. This is different to
	 * Android's standard Overscroll support (the edge-glow) which is available
	 * from GINGERBREAD onwards
	 * 
	 * @return true - if both PullToRefresh-OverScroll and Android's inbuilt
	 *         OverScroll are enabled
	 */
	public boolean isPullToRefreshOverScrollEnabled();

	/**
	 * Returns whether the Widget is currently in the Refreshing mState
	 * 
	 * @return true if the Widget is currently refreshing
	 */
	public boolean isRefreshing();

	/**
	 * Returns whether the widget has enabled scrolling on the Refreshable View
	 * while refreshing.
	 * 
	 * @return true if the widget has enabled scrolling while refreshing
	 */
	public boolean isScrollingWhileRefreshingEnabled();

	/**
	 * Mark the current Refresh as complete. Will Reset the UI and hide the
	 * Refreshing View
	 */
	public void onRefreshComplete();

	/**
	 * Set the Touch Events to be filtered or not. If set to true, then the View
	 * will only use touch events where the difference in the Y-axis is greater
	 * than the difference in the X-axis. This means that the View will not
	 * interfere when it is used in a horizontal scrolling View (such as a
	 * ViewPager), but will restrict which types of finger scrolls will trigger
	 * the View.
	 * 
	 * @param filterEvents - true if you want to filter Touch Events. Default is
	 *            true.
	 */
	public void setFilterTouchEvents(boolean filterEvents);

	/**
	 * Set OnPullEventListener for the Widget
	 * 
	 * @param listener - Listener to be used when the Widget has a pull event to
	 *            propogate.
	 */
	public void setOnPullEventListener(OnPullEventListener<T> listener);

	/**
	 * Set OnRefreshListener for the Widget
	 * 
	 * @param listener - Listener to be used when the Widget is set to Refresh
	 */
	public void setOnRefreshListener(OnRefreshListener<T> listener);

	/**
	 * Sets whether Overscroll support is enabled. This is different to
	 * Android's standard Overscroll support (the edge-glow). This setting only
	 * takes effect when running on device with Android v2.3 or greater.
	 * 
	 * @param enabled - true if you want Overscroll enabled
	 */
	public void setPullToRefreshOverScrollEnabled(boolean enabled);

	/**
	 * Sets the Widget to be in the refresh state. The UI will be updated to
	 * show the 'Refreshing' view, and be scrolled to show such.
	 */
	public void setRefreshing();

	/**
	 * Sets the Widget to be in the refresh state. The UI will be updated to
	 * show the 'Refreshing' view.
	 * 
	 * @param doScroll - true if you want to force a scroll to the Refreshing
	 *            view.
	 */
	public void setRefreshing(boolean doScroll);

	/**
	 * Sets the Animation Interpolator that is used for animated scrolling.
	 * Defaults to a DecelerateInterpolator
	 * 
	 * @param interpolator - Interpolator to use
	 */
	public void setScrollAnimationInterpolator(Interpolator interpolator);

	/**
	 * By default the Widget disables scrolling on the Refreshable View while
	 * refreshing. This method can change this behaviour.
	 * 
	 * @param scrollingWhileRefreshingEnabled - true if you want to enable
	 *            scrolling while refreshing
	 */
	public void setScrollingWhileRefreshingEnabled(boolean scrollingWhileRefreshingEnabled);

	/**
	 * A mutator to enable/disable whether the 'Refreshing' View should be
	 * automatically shown when refreshing.
	 * 
	 * @param showView
	 */
	public void setShowViewWhileRefreshing(boolean showView);

}