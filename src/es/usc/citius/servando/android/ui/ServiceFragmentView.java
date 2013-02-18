package es.usc.citius.servando.android.ui;

import java.util.Collections;
import java.util.List;

import android.support.v4.app.Fragment;
import android.widget.Toast;
import es.usc.citius.servando.android.models.protocol.MedicalActionExecution;

public abstract class ServiceFragmentView extends Fragment {

	public interface ServiceFragmentCloseListener {
		public void onCloseFragment();

		public void speak();
	}

	private ServiceFragmentCloseListener listener;

	protected boolean hasFocus;

	protected MedicalActionExecution execution;

	public final boolean hasFocus()
	{
		return hasFocus;
	}

	public final void setFocus(boolean hasFocus)
	{
		this.hasFocus = hasFocus;
		onFocusChange(hasFocus);
	}

	protected void onFocusChange(boolean hasFocus)
	{

	}

	public void setOnCloseListener(ServiceFragmentCloseListener listener)
	{
		this.listener = listener;
	}

	protected final void onCloseView()
	{
		listener.onCloseFragment();
	}

	public void toast(String msg)
	{
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	public void setExecution(MedicalActionExecution e)
	{
		execution = e;
	}

	public List<FragmentViewMenuItem> getMenuItems()
	{

		// default implementation
		return Collections.emptyList();
	}

	public boolean onCloseByUser()
	{
		// default implementation
		return false;
	}

	public void updateUi()
	{
		// default implementation does nothing
	}

}
