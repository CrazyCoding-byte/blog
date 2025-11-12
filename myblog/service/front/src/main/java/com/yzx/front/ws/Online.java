package com.yzx.front.ws;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yzx
 * @create 2023-01-13 9:18
 */
@ServerEndpoint("/online")
@Component
public class Online {

    //用了存储每一个对象对应的Online对象
    private static CopyOnWriteArraySet<Online> objectMap = new CopyOnWriteArraySet<>();

    //声明session对象，通过该对象可以发送消息给指定的用户
    private Session session;
    private static  int onlineCount;
    @OnOpen
    //连接建立时被调用
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        try {
            this.session = session;
            objectMap.add(this);
            getOnlineCount();
            session.getBasicRemote().sendText(String.valueOf(onlineCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage

    public void onMessage(String message, Session session) {

    }

    @OnClose
    public void onClose(Session session) {
        try {
            System.out.println("当前的客户已经关闭了");
            objectMap.remove(this);
            getOnlineCount();
            if(session.isOpen()){
                session.getAsyncRemote().sendText(String.valueOf(onlineCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getOnlineCount(){
        onlineCount=objectMap.size();
        return onlineCount;
    }
}
