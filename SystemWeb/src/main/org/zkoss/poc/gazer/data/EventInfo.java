package org.zkoss.poc.gazer.data;


public class EventInfo {
	private String execID;
	private String eventName;
	private String reqID;
	private String reqPath;
	private String targetCompName;/* targetComp contains Event and Target names*/
	private long startProcTime;
	private long afterProcTime;
	private long eventProcTime;
	private int count = 0;
	
	//constructor
	public EventInfo(String execID){
		this.setExecId(execID);
	}
	
	public EventInfo(String execID, String eventName, String targetCompName,
			long startProcTime, long afterProcTime) {
		super();
		this.execID = execID;
		this.eventName = eventName;
		this.targetCompName = targetCompName;
		this.startProcTime = startProcTime;
		this.afterProcTime = afterProcTime;
	}

	//setters
	public void setReqPath(String reqPath){
		this.reqPath = reqPath;
	}
	
	public String setReqId(String reqID){
		this.reqID = reqID;
		return reqID;
	}
	
	public String setExecId(String execID) {
		this.execID = execID;
		return execID;
	}
	
	public void setStartProcTime(long startproc) {
		this.startProcTime = startproc;
	}
	
	public void setAfterProcTime(long afterproc) {
		this.afterProcTime = afterproc;
	}
	
	//getters
	public String getReqPath(){
		return reqPath;
	}
	
	public String getReqId(){
		return reqID;
	}
	
	public String getEventName() {
		return eventName;
	}
	public long getStartProcTime() {
		return startProcTime;
	}
	public long getAfterProcTime() {
		return afterProcTime;
	}
	
	public long getEventProcTime(){
		return afterProcTime - startProcTime;
	}
	
	public long getEventTimeSum(){
		return this.eventProcTime;
	}
	
	
	public void setEventTimeSum(long time){
		this.eventProcTime = time;
	}
	
	
	public String getExecId() {
		return execID;
	}
		
	public String getTargetCompName() {
		return targetCompName;
	}
	
	public void setTargetCompName(String targetCompName) {
		this.targetCompName = targetCompName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public int getCount(){
		return count;
	}
	
	public String toString(){
		return targetCompName+","+startProcTime+","+afterProcTime;
	}
}
