package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class process implements Comparable<process> {
	private IntegerProperty id;
	private IntegerProperty arrTime;
	private IntegerProperty bustTime;
	private IntegerProperty waitTime;
	private IntegerProperty taTime;
	private IntegerProperty work;
	private DoubleProperty NormalizeTT;
	private DoubleProperty Aging;
	
	
	public double getAging() {
		return Aging.get();
	}

	public void setAging(double aging) {
		Aging.set(aging);
	}

	public process(int id, int artime, int buTime) {
		this.id= new SimpleIntegerProperty(id);
		arrTime = new SimpleIntegerProperty(artime);
		bustTime = new SimpleIntegerProperty(buTime);
		work = new SimpleIntegerProperty(buTime);
		waitTime = new SimpleIntegerProperty(0);
		taTime= new SimpleIntegerProperty(0);
		NormalizeTT = new SimpleDoubleProperty(0.0);
		Aging = new SimpleDoubleProperty(0.0);
	}	
	
	public void progress() {
		work.set(work.get()-1);
	}
	
	public boolean isEnd() {
		return work.get()==0;
	}

	
	public int getWork() {
		return work.get();
	}

	public void setWork(int work) {
		this.work.set(work);;
	}

	public IntegerProperty idProperty() {
		return id;
	}
	public IntegerProperty arrTimeProperty() {
		return arrTime;
	}
	public IntegerProperty burstProperty() {
		return bustTime;
	}
	public IntegerProperty waitProperty() {
		return waitTime;
	}
	public IntegerProperty ttProperty() {
		return taTime;
	}
	public DoubleProperty nttProperty() {
		return NormalizeTT;
	}
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public int getArrTime() {
		return arrTime.get();
	}
	public void setArrTime(int arrTime) {
		this.arrTime.set(arrTime);
	}
	public int getBustTime() {
		return bustTime.get();
	}
	public void setBustTime(int bustTime) {
		this.bustTime.set(bustTime);
	}
	public int getWaitTime() {
		return waitTime.get();
	}
	public void setWaitTime(int waitTime) {
		this.waitTime.set(waitTime);
	}
	public int getTaTime() {
		return taTime.get();
	}
	public void setTaTime(int taTime) {
		this.taTime.set(taTime);
	}
	public double getNormalizeTT() {
		return NormalizeTT.get();
	}
	public void setNormalizeTT(double normalizeTT) {
		NormalizeTT.set(normalizeTT);
	}

	@Override
	public String toString() {
		return "process [id=" + id + ", arrTime=" + arrTime + ", bustTime=" + bustTime + ", waitTime=" + waitTime
				+ ", taTime=" + taTime + ", NormalizeTT=" + NormalizeTT + "]";
	}

	@Override
	public int compareTo(process target) {
		// TODO Auto-generated method stub.
		return this.getBustTime() >= target.getBustTime() ? 1 : -1;
	}
	
}
