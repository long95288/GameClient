package com.Utils;

import com.event.EventRequest;
import com.event.Handle;

// 最高级别的处理者，将处理责任链中各个部分不能处理的事情
public class TopHandler extends Handle {
    @Override
    public void handleRequest(EventRequest request) {

    }
}
