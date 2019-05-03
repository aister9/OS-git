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
	Simulator(int tq){timeQ = tq;}
	
	public void setTimeQ(int tq) {timeQ = tq;}
	
	public void setMain(Main mp) {
		mainApp = mp;

	}
	
	public void setUp(process ... pl) {
		pwQueue.clear();
		for(process p : pl) {
			pwQueue.add(p);
		}
		curTime = 0;
		checkTime = -1;
		squeue.clear();
		priQueue.clear();
	}
	public void setUp(ObservableList<process> processData) {
		// TODO Auto-generated method stub
		pwQueue.clear();
		for(process p : processData) {
			pwQueue.add(p);
		}
		curTime = 0;
		checkTime = -1;
		squeue.clear();
		priQueue.clear();
	}
	
	public boolean chkSecond() {
		return Math.abs(curTime-checkTime) == 1;
		}
	
	/*FCFS*/
	public Queue<ProcessProgress> runFCFS() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();//��ȯ���� ť ��ϻ���
		int totalTime = 0;//�� ���� �ð�
		
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				while(totalTime >= pwQueue.peek().getArrTime()) {
					process addP = pwQueue.remove();
					squeue.add(addP);
					if(pwQueue.isEmpty())
						break;
				}
			}
			if(!squeue.isEmpty()) {//����� ������ ť�κ��� �Է� ���� ��
				process cur = squeue.remove();//id1���� ���ʴ�� ���� �޴´�.
				ProcessProgress elements = new ProcessProgress(cur.getId(), totalTime);//��Ҹ� �Է� �ް�
				totalTime += cur.getBustTime();//�ѽ��� �ð��� �� ���μ����� ����ð� �� ���� ���� �־� �ش�.
				elements.setworkTime(cur.getBustTime());//��ҵ��� ���� �ð����� ���ϴ� �ð��� ���� �ְ� 
				progressQueue.add(elements);
				if(mainApp != null ) {
					cur.setTaTime(totalTime-cur.getArrTime());
					cur.setWaitTime(cur.getTaTime()-cur.getBustTime());
					cur.setNormalizeTT((double)cur.getTaTime() / (double)cur.getBustTime());
					mainApp.processDataChange(cur);
				}
			}
			else {
				totalTime++;
			}
				
		}
		return progressQueue;
	}
	/*RR*/
	public Queue<ProcessProgress> runRR() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();
		int totalTime = 0;
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				while(totalTime >= pwQueue.peek().getArrTime()) {
					process addP = pwQueue.remove();
					squeue.add(addP);
					if(pwQueue.isEmpty())
						break;
				}
			}
			if(!squeue.isEmpty()) {
				process cur = squeue.remove();
				ProcessProgress elements = new ProcessProgress(cur.getId(), totalTime);
				if((cur.getWork()/timeQ)!=0) {
					totalTime += timeQ;
					cur.setWork(cur.getWork()-timeQ);
					elements.setworkTime(timeQ);
					progressQueue.add(elements);
					squeue.add(cur);
				}
				else 
				{
					int  temp = cur.getWork()% timeQ;
					totalTime += temp;
					cur.setWork(0);
					elements.setworkTime(temp);
					if(elements.getworkTime() != 0)progressQueue.add(elements);
					if(mainApp != null ) {
						cur.setTaTime(totalTime-cur.getArrTime());
						cur.setWork(cur.getBustTime());
						cur.setWaitTime(cur.getTaTime()-cur.getBustTime());
						cur.setNormalizeTT((double)cur.getTaTime() / (double)cur.getBustTime());
						mainApp.processDataChange(cur);
					}
				}
			}
			else {
				totalTime++;
			}
		}
		return progressQueue;
	}
	/*SPN*/
	public Queue<ProcessProgress> runSPN() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();
		int totalTime = 0;
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				while(totalTime >= pwQueue.peek().getArrTime()) {
					process addP = pwQueue.remove();
					priQueue.add(addP);
					if(pwQueue.isEmpty())
						break;
				}
			}
			if(!priQueue.isEmpty()) {
				process cur = priQueue.remove();
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
			else {
				totalTime++;
			}
		}
		return progressQueue;
	}
	/*HRN*/
	public Queue<ProcessProgress> runHRN() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();
		int totalTime = 0;
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				while(totalTime >= pwQueue.peek().getArrTime()) {
					process stageP = pwQueue.remove();
					process addP = new process(stageP.getId(), stageP.getArrTime(), stageP.getBustTime()) {
						public int compareTo(process target) {return this.getAging() <= target.getAging() ? 1 : -1; }
					};
					priQueue.add(addP);
					if(pwQueue.isEmpty())
						break;
				}
					
			}
			if(!priQueue.isEmpty()) {
				PriorityQueue<process> tempQueue = new PriorityQueue<process>();
				while(!priQueue.isEmpty()) {
					process temp = priQueue.remove();
					int waitTime = totalTime - temp.getArrTime();
					temp.setAging((temp.getBustTime()+waitTime)/temp.getBustTime());
					tempQueue.add(temp);
				}
				
				priQueue.addAll(tempQueue);
				process cur = priQueue.remove();
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
			else {
				totalTime++;
			}
		}
		return progressQueue;
	}
	/*THRN*/
	public Queue<ProcessProgress> runTHRN() {
		Queue<ProcessProgress> progressQueue = new LinkedList<ProcessProgress>();
		int totalTime = 0;
		while(!isEnd()) {
			if(!pwQueue.isEmpty()) {
				while(totalTime >= pwQueue.peek().getArrTime()) {
					process stageP = pwQueue.remove();
					process addP = new process(stageP.getId(), stageP.getArrTime(), stageP.getBustTime()) {
						public int compareTo(process target) {return this.getAging() <= target.getAging() ? 1 : -1; }
					};
					priQueue.add(addP);
					if(pwQueue.isEmpty())
						break;
				}
			}
			if(!priQueue.isEmpty()) {
				PriorityQueue<process> tempQueue = new PriorityQueue<process>();
				while(!priQueue.isEmpty()) {
					process temp = priQueue.remove();
					int waitTime = totalTime - temp.getArrTime();
					temp.setAging((temp.getBustTime()+waitTime)/temp.getBustTime());
					tempQueue.add(temp);
				}
				priQueue.addAll(tempQueue);
				process cur = priQueue.remove();
				ProcessProgress elements = new ProcessProgress(cur.getId(), totalTime);
				if((cur.getWork()/timeQ)!=0) {
					totalTime += timeQ;
					cur.setWork(cur.getWork()-timeQ);
					elements.setworkTime(timeQ);
					progressQueue.add(elements);
					priQueue.add(cur);
				}
				else 
				{
					int  temp = cur.getBustTime()% timeQ;
					totalTime += temp;
					elements.setworkTime(temp);
					progressQueue.add(elements);
					if(mainApp != null ) {
						cur.setTaTime(totalTime-cur.getArrTime());
						cur.setWork(cur.getBustTime());
						cur.setWaitTime(cur.getTaTime()-cur.getBustTime());
						cur.setNormalizeTT((double)cur.getTaTime() / (double)cur.getBustTime());
						mainApp.processDataChange(cur);
					}
				}
			}
			else {
				totalTime++;
			}
		}
		return progressQueue;
	}
	
	
	public boolean isEnd() {
		return (squeue.isEmpty() && priQueue.isEmpty()) && pwQueue.isEmpty();
	}

}