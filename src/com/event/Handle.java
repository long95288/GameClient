package com.event;
// 处理抽象类,主要是处理EventRequest发出的请求
public abstract class Handle {
    // protected String eventType; // 事件类型
    protected Handle successor; // 下一个事件处理者
    public Handle(){}

    // 设置下一个处理者
    public void setSuccessor(Handle successor){
        this.successor = successor;
    }
    // 处理请求
    public abstract void handleRequest(EventRequest request);

}
