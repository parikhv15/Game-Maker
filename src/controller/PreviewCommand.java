package controller;

import model.TimerObservable;
import controller.Command;

//call method of model to load demo game for preview command 
public class PreviewCommand implements Command {

	private TimerObservable timerObs;

	public PreviewCommand(TimerObservable timerObs) {
		this.timerObs = timerObs;
	}

	//call method of model to load demo game for preview command
	@Override
	public void execute() {
		timerObs.startPreview();
	}

	public TimerObservable getTimerObs() {
		return timerObs;
	}

	public void setTimerObs(TimerObservable timerObs) {
		this.timerObs = timerObs;
	}

}
