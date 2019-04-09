package application;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.collections.ObservableList;

public class Simulator {
	private Queue<process> squeue = new LinkedList<process>(); //system queue
	private Queue<process> pwQueue = new LinkedList<process>(); // process waiting queue
	private PriorityQueue<process> priQueue = new PriorityQueue<process>(); //System queue. version is priorityqueue
	private int curTime = 0;
	private int checkTime = -1;
	private int timeQ = 0;
	private Main mainApp;
	
	Simulator(){}
	
	public void setMain(Main mp) {
		mainApp = mp;
	}
	
	public void setUp(process ... pl) {
		for(process p : pl) {
			pwQueue.add(p);
		}
		curTime = 0;
		checkTime = -1;
		timeQ = 0;
		squeue.clear();
		priQueue.clear();
	}
	public void setUp(ObservableList<process> processData) {
		// TODO Auto-generated method stub
		for(process p : processData) {
			pwQueue.add(p);
		}
		curTime = 0;
		checkTime = -1;
		timeQ = 0;
		squeue.clear();
		priQueue.clear();
	}
	
	public boolean chkSecond() {
		return Math.abs(curTime-checkTime) == 1;
		}
	
	/*FCFS*/
	public Queue<ProcessProgress> runFCFS() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();
		int totalTime = 0;
		
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				process cur = pwQueue.remove();
				ProcessProgress elements = new ProcessProgress(cur.getId(), cur.getArrTime());
				totalTime += cur.getBustTime();
				elements.setworkTime(cur.getBustTime());
				progressQueue.add(elements);
				if(mainApp != null ) {
					cur.setTaTime(totalTime-cur.getArrTime());
					cur.setWaitTime(cur.getTaTime()-cur.getBustTime());
					cur.setNormalizeTT((double)cur.getTaTime() / (double)cur.getBustTime());
					mainApp.processDataChange(cur);
				}
			}
		}
		return progressQueue;
	}
	
	public boolean isEnd() {
		return (squeue.isEmpty() && priQueue.isEmpty()) && pwQueue.isEmpty();
	}

}