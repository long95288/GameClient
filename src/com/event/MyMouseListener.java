package com.event;

import com.Config.EvenType;
;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener extends Handle {

    MouseListener listener ;
    public MyMouseListener() {
        super();
        inti();
        // 抛出鼠标的点击事件
        // this.successor.handleRequest(new EventRequest("CLICK","x|y"));
    }
    private void inti(){
        listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                int type = e.getButton(); // 获得按钮的类型。左键还是右键
                int clickX = e.getY()/40;
                int clickY = e.getX()/40;
                String value =clickX+"|"+clickY+"|"+type;
                System.out.println("鼠标事件："+value);
                // 抛出鼠标事件
                ThrowMouseEvent(value);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }
    @Override
    public void handleRequest(EventRequest request) {
        // 事件处理，没有
    }

    // 鼠标事件
    private void ThrowMouseEvent(String value){
        // 向职责链发送CLICK事件请求
        if (this.successor != null) {
            this.successor.handleRequest(new EventRequest(EvenType.CLICK, value));
        }
    }
    // 获得本类的监听器
    public MouseListener getListener(){
        return this.listener; // 返回本类的鼠标监听对象
    }
}
