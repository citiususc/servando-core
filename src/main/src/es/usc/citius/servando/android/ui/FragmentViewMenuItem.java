package es.usc.citius.servando.android.ui;

import android.view.MenuItem.OnMenuItemClickListener;

public class FragmentViewMenuItem {

	private int itemId;

	private int iconId = -1;

	private String title;

	private OnMenuItemClickListener onClickListener;

	public FragmentViewMenuItem(int id, String title, OnMenuItemClickListener listener)
	{
		itemId = id;
		this.title = title;
		onClickListener = listener;
	}

	public FragmentViewMenuItem(int id, String title, int icon, OnMenuItemClickListener listener)
	{
		this(id, title, listener);
		iconId = icon;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId()
	{
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}

	/**
	 * @return the onClickListener
	 */
	public OnMenuItemClickListener getOnClickListener()
	{
		return onClickListener;
	}

	/**
	 * @param onClickListener the onClickListener to set
	 */
	public void setOnClickListener(OnMenuItemClickListener onClickListener)
	{
		this.onClickListener = onClickListener;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the iconId
	 */
	public int getIconId()
	{
		return iconId;
	}

	/**
	 * @param iconId the iconId to set
	 */
	public void setIconId(int iconId)
	{
		this.iconId = iconId;
	}

	public boolean hasIcon()
	{
		return iconId != -1;
	}

}
