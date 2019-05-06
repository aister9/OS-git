package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ProgressAnimation {
	private colorMap cMap;
	private Group processGroup;
	private Group endGroup;
	private double startPosX;
	private double startPosY;
	private int totalTime;
	private double lineLength;
	private double endPosX;
	private double endPosY;
	private int index;
	
	private Timeline timeline;
	private KeyValue keyValue;
	private KeyFrame keyFrame;
	
	
	public ProgressAnimation(ProcessProgress pp, colorMap cMap, double spx, double spy, int totalTime, double lineLength, int index) {
		startPosX = spx;
		startPosY = spy;
		this.totalTime = totalTime;
		this.lineLength = lineLength;
		endPosX = (double)pp.getworkTime()/(int)totalTime * lineLength - 10;
		endPosY = spy;
		this.index = index;
		this.cMap = cMap;
		setProcessGroup(pp);
		setEndGroup(pp);
	}
	
	public int getNextIndex() {return ++index;}
	public int getIndex() {return index;}
	public void setEndPosX(double epx) { endPosX =epx;} 	
	public double getEndPosX() {return endPosX;}
	public int getTotalTime() {return totalTime;}
	
	
	public void setProcessGroup(ProcessProgress pp) {
		Label processText = new Label("P" + pp.getId());
		Rectangle processSquare = new Rectangle(startPosX, startPosY, 20, 20);
		processSquare.setFill(Color.web(cMap.getColorOfI(pp.getId()-1)));
		processText.setTranslateX(startPosX);
		processText.setTranslateY(startPosY);
		processText.setTextFill(Color.WHITE);
		processGroup = new Group(processSquare,processText); //앞에 선언한게 먼저깔림.
	}
	public void setEndGroup(ProcessProgress pp) {
		Line curLine = new Line(startPosX + endPosX + 20, 50, startPosX + endPosX + 20, 60);
		Label currentTime = new Label(Integer.toString(pp.getworkTime()));
		currentTime.setTranslateX(curLine.getStartX());
		currentTime.setTranslateY(curLine.getEndY()+10);
		endGroup = new Group(curLine, currentTime);
	}
	
	public void setTimeLine() {
		timeline = new Timeline();
		keyValue = new KeyValue(processGroup.translateXProperty(), endPosX);
		keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
		
		timeline.getKeyFrames().add(keyFrame);
	}
	
	public Group getProcessGroup() { return processGroup; }
	public Group getEndGroup() { return endGroup; }
	public Timeline getTimeline() {return timeline;}
	public void setFinish(EventHandler<ActionEvent> e) {
		timeline.setOnFinished(e);
	}
}
