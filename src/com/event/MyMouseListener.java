package com.event;

import com.Config.EvenType;
import com.JsonData.JsonData;
import com.Store.Store;
;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener extends Handle {

    private MouseListener listener ;
    public MyMouseListener() {
        super();
        inti();
    }
    private void inti(){
        listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int type = e.getButton(); // 获得按钮的类型。左键还是右键
                int clickX = e.getY()/40;
                int clickY = e.getX()/40;
//                String value =clickX+"|"+clickY+"|"+type;
                String value = JsonData.formatClickJson(String.valueOf(clickX),
                        String.valueOf(clickY),String.valueOf(type));
                System.out.println("鼠标事件："+value);
                // 抛出鼠标事件
                ThrowMouseEvent(value);
            }
        };
    }
    @Override
    public void handleRequest(EventRequest request) {
        // 事件处理，将请求传递下去
        if (this.successor!=null){
            this.successor.handleRequest(request);
        }
    }

    // 鼠标事件
    private void ThrowMouseEvent(String value){
        //  向职责链发送CLICK事件请求
        //  可以点击
        if (this.successor != null && Store.isMouseClickable())  {
            this.successor.handleRequest(new EventRequest(EvenType.CLICK, value));
        }
    }
    // 获得本类的监听器
    public MouseListener getListener(){
        return this.listener; // 返回本类的鼠标监听对象
    }
}
