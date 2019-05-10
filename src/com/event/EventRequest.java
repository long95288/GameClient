package com.event;

// 事件请求类
public class EventRequest {
    private String eventType; // 事件类型
    private String eventData; // 事件参数
    public EventRequest(String eventType, String eventData){
        this.eventType = eventType;
        this.eventData = eventData;
    }
    public void setEvenType(String eventType){
        this.eventType = eventType;
    }
    public void setEventData(String eventData){
        this.eventData = eventData;
    }
    public String getEventType(){return this.eventType;}
    public String getEventData(){return this.eventData;}
}
