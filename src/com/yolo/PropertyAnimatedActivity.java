package com.yolo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class PropertyAnimatedActivity extends Activity {

	private ObjectAnimator barAni;
	private int progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_animated);
		
		if (savedInstanceState != null) 
			progress = savedInstanceState.getInt("progress");
		else
			progress = 0;

		//get the wheel view
		ImageView wheel = (ImageView)findViewById(R.id.wheel);
		//load the wheel spinning animation
		AnimatorSet wheelSet = (AnimatorSet) 
				AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
		//the the view as target
		wheelSet.setTarget(wheel);
		//start the animation
		wheelSet.start();

		//get the sun view
		ImageView sun = (ImageView)findViewById(R.id.sun);
		//load the sun movement animation
		AnimatorSet sunSet = (AnimatorSet) 
				AnimatorInflater.loadAnimator(this, R.animator.sun_swing);
		//set the view as target
		sunSet.setTarget(sun);
		//start the animation
		sunSet.start();

		//create a value animator to darken the sky as we move towards and away from the sun
		//passing the view, property and to-from values
		ValueAnimator skyAnim = ObjectAnimator.ofInt
				(findViewById(R.id.car_layout), "backgroundColor", 
						Color.rgb(0x66, 0xcc, 0xff), Color.rgb(0x00, 0x66, 0x99));
		//set same duration and animation properties as others
		skyAnim.setDuration(3000);
		skyAnim.setEvaluator(new ArgbEvaluator());
		skyAnim.setRepeatCount(ValueAnimator.INFINITE);
		skyAnim.setRepeatMode(ValueAnimator.REVERSE);
		//start the animation
		skyAnim.start();

		//create an object animator to move the cloud
		//passing the view, property and to value only
		ObjectAnimator cloudAnim = ObjectAnimator.ofFloat
				(findViewById(R.id.cloud1), "x", -350);
		//same duration and other properties as rest
		cloudAnim.setDuration(3000);
		cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
		cloudAnim.setRepeatMode(ValueAnimator.REVERSE);
		//start the animation
		cloudAnim.start();

		//create an object animator for second cloud
		//same as previous except movement distance
		ObjectAnimator cloudAnim2 = ObjectAnimator.ofFloat
				(findViewById(R.id.cloud2), "x", -300);
		cloudAnim2.setDuration(3000);
		cloudAnim2.setRepeatCount(ValueAnimator.INFINITE);
		cloudAnim2.setRepeatMode(ValueAnimator.REVERSE);
		cloudAnim2.start();
		
		TimeInterpolator GAUGE_ANIMATION_INTERPOLATOR = new DecelerateInterpolator(2);
		int MAX_LEVEL = 100;

		ProgressBar p = (ProgressBar)findViewById(R.id.bar);
//		Drawable d = Drawable.createFromXml(r, parser);
		Drawable d = getResources().getDrawable(R.drawable.layers);
		p.setProgressDrawable(d);
		barAni = ObjectAnimator.ofInt(p, "progress", progress, 100);
		barAni.setDuration(5000);
		barAni.setInterpolator(GAUGE_ANIMATION_INTERPOLATOR);
		barAni.start();
		
		// tada
		ObjectAnimator tada = tada(findViewById(R.id.keek));
		tada.start();
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		new Handler().post(new Runnable() {
//		    @Override
//		    public void run() {
//		      view = findViewById(R.id.menu_applist);
//		      
//		    }
//		});
//		return super.onCreateOptionsMenu(menu);
//	}
	
	///
	public static ObjectAnimator tada(View view, float shakeFactor) {

	    PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
	            Keyframe.ofFloat(0f, 1f),
	            Keyframe.ofFloat(.1f, .9f),
	            Keyframe.ofFloat(.2f, .9f),
	            Keyframe.ofFloat(.3f, 1.1f),
	            Keyframe.ofFloat(.4f, 1.1f),
	            Keyframe.ofFloat(.5f, 1.1f),
	            Keyframe.ofFloat(.6f, 1.1f),
	            Keyframe.ofFloat(.7f, 1.1f),
	            Keyframe.ofFloat(.8f, 1.1f),
	            Keyframe.ofFloat(.9f, 1.1f),
	            Keyframe.ofFloat(1f, 1f)
	    );

	    PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
	            Keyframe.ofFloat(0f, 1f),
	            Keyframe.ofFloat(.1f, .9f),
	            Keyframe.ofFloat(.2f, .9f),
	            Keyframe.ofFloat(.3f, 1.1f),
	            Keyframe.ofFloat(.4f, 1.1f),
	            Keyframe.ofFloat(.5f, 1.1f),
	            Keyframe.ofFloat(.6f, 1.1f),
	            Keyframe.ofFloat(.7f, 1.1f),
	            Keyframe.ofFloat(.8f, 1.1f),
	            Keyframe.ofFloat(.9f, 1.1f),
	            Keyframe.ofFloat(1f, 1f)
	    );

	    PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
	            Keyframe.ofFloat(0f, 0f),
	            Keyframe.ofFloat(.1f, -3f * shakeFactor),
	            Keyframe.ofFloat(.2f, -3f * shakeFactor),
	            Keyframe.ofFloat(.3f, 3f * shakeFactor),
	            Keyframe.ofFloat(.4f, -3f * shakeFactor),
	            Keyframe.ofFloat(.5f, 3f * shakeFactor),
	            Keyframe.ofFloat(.6f, -3f * shakeFactor),
	            Keyframe.ofFloat(.7f, 3f * shakeFactor),
	            Keyframe.ofFloat(.8f, -3f * shakeFactor),
	            Keyframe.ofFloat(.9f, 3f * shakeFactor),
	            Keyframe.ofFloat(1f, 0)
	    );

	    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate);
	    animator.setDuration(1000);
	    return animator;
	}
	
	public static ObjectAnimator tada(View view) {
	    return tada(view, 1f);
	}
	
	public static ObjectAnimator nope(View view) {
//	    int delta = view.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);
		int delta = 10;

	    PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
	            Keyframe.ofFloat(0f, 0),
	            Keyframe.ofFloat(.10f, -delta),
	            Keyframe.ofFloat(.26f, delta),
	            Keyframe.ofFloat(.42f, -delta),
	            Keyframe.ofFloat(.58f, delta),
	            Keyframe.ofFloat(.74f, -delta),
	            Keyframe.ofFloat(.90f, delta),
	            Keyframe.ofFloat(1f, 0f)
	    );

	    return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
	            setDuration(500);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt("progress", (Integer)barAni.getAnimatedValue());
	}
}
