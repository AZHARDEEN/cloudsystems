package org.zkoss.poc.gazer.data;

public class Analytics {
	private String target;
	private String event;
	private long timeSum;
	private int count = 0;
	
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public long getTimeSum() {
		return timeSum;
	}
	public void setTimeSum(long l) {
		this.timeSum = l;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

				
}
