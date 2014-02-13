package es.usc.citius.servando.android.ui.animation;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import es.usc.citius.servando.R;

public class AnimationStore {

	/**
	 * Singleton unique instance
	 */
	private static final AnimationStore instance = new AnimationStore();

	/**
	 * Private constructor to avoid multiple instances
	 */
	private AnimationStore()
	{
	}

	/**
	 * Static member to obtain the unique instance
	 */
	public static AnimationStore getInstance()
	{
		return instance;
	}

	private Animation slideInFromRigth;
	private Animation slideInFromLeft;
	private Animation slideOutToRigth;
	private Animation slideOutToLeft;

	private Animation comeIn;
	private Animation goBack;

	public void initialize(Context ctx)
	{
		slideInFromRigth = slideInFromRigth();
		slideInFromLeft = slideInFromLeft();
		slideOutToLeft = slideOutToLeft();
		slideOutToRigth = slideOutToRight();
		comeIn = AnimationUtils.loadAnimation(ctx, R.anim.anim_come_in);
		goBack = AnimationUtils.loadAnimation(ctx, R.anim.anim_go_back);
	}

	private Animation slideInFromRigth()
	{
		Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	private Animation slideOutToLeft()
	{
		Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;

	}

	private Animation slideInFromLeft()
	{
		Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;

	}

	private Animation slideOutToRight()
	{
		Animation outtoRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;

	}

	public Animation getSlideInFromRigth()
	{
		return slideInFromRigth;
	}

	public void setSlideInFromRigth(Animation slideInFromRigth)
	{
		this.slideInFromRigth = slideInFromRigth;
	}

	public Animation getSlideInFromLeft()
	{
		return slideInFromLeft;
	}

	public void setSlideInFromLeft(Animation slideInFromLeft)
	{
		this.slideInFromLeft = slideInFromLeft;
	}

	public Animation getSlideOutToRigth()
	{
		return slideOutToRigth;
	}

	public void setSlideOutToRigth(Animation slideOutToRigth)
	{
		this.slideOutToRigth = slideOutToRigth;
	}

	public Animation getSlideOutToLeft()
	{
		return slideOutToLeft;
	}

	public void setSlideOutToLeft(Animation slideOutToLeft)
	{
		this.slideOutToLeft = slideOutToLeft;
	}

	/**
	 * @return the comeIn
	 */
	public Animation getComeIn()
	{
		return comeIn;
	}

	/**
	 * @param comeIn the comeIn to set
	 */
	public void setComeIn(Animation comeIn)
	{
		this.comeIn = comeIn;
	}

	/**
	 * @return the goBack
	 */
	public Animation getGoBack()
	{
		return goBack;
	}

	/**
	 * @param goBack the goBack to set
	 */
	public void setGoBack(Animation goBack)
	{
		this.goBack = goBack;
	}

}
