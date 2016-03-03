package controller;

import model.TimerObservable;
import controller.Command;
import view.ControlPanel;
import view.PreviewPanel;

//call method of model to stop the timer observable
public class CloseCommand implements Command {

	private TimerObservable timerObs;

	public CloseCommand(TimerObservable timerObs) {
		this.timerObs = timerObs;
	}

	//call method of model to stop the timer observable
	@Override
	public void execute() {
		timerObs.closePreview();
	}

	public TimerObservable getTimerObs() {
		return timerObs;
	}

	public void setTimerObs(TimerObservable timerObs) {
		this.timerObs = timerObs;
	}

}
