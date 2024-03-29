package com.avermedia.training.pulltorefresh;

import com.avermedia.training.pulltorefresh.PullToRefreshBase.Orientation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@SuppressLint("ViewConstructor")
public class FlipLoadingLayout extends LoadingLayout {

	private static final int FLIP_ANIMATION_DURATION = 150;
	private static final int FLIP_ANIMATION_ANGLE = -180; 

	private ImageView mHeaderImage;
	private final Animation mRotateAnimation, mResetRotateAnimation;

	public FlipLoadingLayout(Context context, final Orientation scrollDirection, TypedArray attrs) {
		super(context, scrollDirection, attrs);
		
		final int rotateAngle = FLIP_ANIMATION_ANGLE;

		mRotateAnimation = new RotateAnimation(0, rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(rotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mResetRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);
	}

	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {
		if (null != imageDrawable) {
			final int dHeight = imageDrawable.getIntrinsicHeight();
			final int dWidth = imageDrawable.getIntrinsicWidth();
			
			mHeaderImage = new ImageView(getContext());
			((FrameLayout)findViewById(R.id.fl_progress)).addView(mHeaderImage);
			mHeaderProgress.setVisibility(View.GONE);

			/**
			 * We need to set the width/height of the ImageView so that it is
			 * square with each side the size of the largest drawable dimension.
			 * This is so that it doesn't clip when rotated.
			 */
			ViewGroup.LayoutParams lp = mHeaderImage.getLayoutParams();
			lp.width = lp.height = Math.max(dHeight, dWidth);
			mHeaderImage.requestLayout();

			/**
			 * We now rotate the Drawable so that is at the correct rotation,
			 * and is centered.
			 */
			mHeaderImage.setScaleType(ScaleType.MATRIX);
			Matrix matrix = new Matrix();
			matrix.postTranslate((lp.width - dWidth) / 2f, (lp.height - dHeight) / 2f);
			matrix.postRotate(getDrawableRotationAngle(), lp.width / 2f, lp.height / 2f);
			mHeaderImage.setImageMatrix(matrix);
		}
	}

	@Override
	protected void onPullImpl(float scaleOfLayout) {
		// NO-OP
	}

	@Override
	protected void pullToRefreshImpl() {
		// Only start reset Animation, we've previously show the rotate anim
		if (null != mHeaderImage && mRotateAnimation == mHeaderImage.getAnimation()) {
			mHeaderImage.startAnimation(mResetRotateAnimation);
		}
	}

	@Override
	protected void refreshingImpl() {		
		if(null != mHeaderImage) {
			mHeaderImage.clearAnimation();
			mHeaderImage.setVisibility(View.INVISIBLE);
			mHeaderProgress.setVisibility(View.VISIBLE);
		}
		
		mHeaderProgress.setVisibility(View.VISIBLE);
	}

	@Override
	protected void releaseToRefreshImpl() {
		if(null != mHeaderImage) {
			mHeaderImage.startAnimation(mRotateAnimation);
		}
		mHeaderProgress.setVisibility(View.GONE);
	}

	@Override
	protected void resetImpl() {
		if(null != mHeaderImage) {
			mHeaderImage.clearAnimation();
			mHeaderImage.setVisibility(View.VISIBLE);
			mHeaderProgress.setVisibility(View.GONE);
		}
		
		mHeaderProgress.setVisibility(View.GONE);
	}

	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.default_ptr_flip;
	}

	private float getDrawableRotationAngle() {
		float angle = 0f;

		if (mScrollDirection == Orientation.HORIZONTAL) {
			angle = 270f;
		}

		return angle;
	}

}
