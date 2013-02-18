package es.usc.citius.servando.android.models.protocol;

import android.support.v4.app.Fragment;

@Deprecated
public class UIMedicalActionExecution extends MedicalActionExecution {

	private Fragment view;

	private MedicalAction action;

	public UIMedicalActionExecution(MedicalAction action)
	{
		this.action = action;
	}

	public Fragment getView()
	{
		return view;
	}

	public void setView(Fragment view)
	{
		this.view = view;
	}

}
