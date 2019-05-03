<<<<<<< HEAD
package application;

public class ProcessProgress {
	private int id;
	private int startTime;
	private int workTime;
	public ProcessProgress(int _id, int _startTime) {
		id = _id;
		startTime = _startTime;
		workTime = 0;
	}
	public void setworkTime(int _endTime) {
		workTime = _endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getworkTime() {
		return workTime;
	}
	@Override
	public String toString() {
		return "ProcessProgress [id=" + id + ", startTime=" + startTime + ", endTime=" + workTime + "]";
	}
}
=======
package application;

public class ProcessProgress {
	private int id;
	private int startTime;
	private int workTime;
	public ProcessProgress(int _id, int _startTime) {
		id = _id;
		startTime = _startTime;
		workTime = 0;
	}
	public void setworkTime(int _endTime) {
		workTime = _endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getworkTime() {
		return workTime;
	}
	@Override
	public String toString() {
		return "ProcessProgress [id=" + id + ", startTime=" + startTime + ", endTime=" + workTime + "]";
	}
}
>>>>>>> f1c07344ca7e846838ad0cb7d04eccc16bcad5a9
