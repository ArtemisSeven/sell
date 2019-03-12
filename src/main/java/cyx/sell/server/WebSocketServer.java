package cyx.sell.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
//websocket第三步：编写websocket服务端
@Component
@ServerEndpoint("/webSocketServer")
@Slf4j
public class WebSocketServer {
    private Session session;
    private CopyOnWriteArraySet<WebSocketServer> sessions=new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        sessions.add(this);
        log.info("【websocket消息】有新的连接，总数={}",sessions.size());
    }
    @OnClose
    public void onClose(){
        sessions.remove(this);
        log.info("【websocket消息】断开连接，总数={}",sessions.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到消息，message={}",message);
    }
    public void sendMessage(String message){
        log.info("【websocket消息】广播发送消息，message={}",message);
        for (WebSocketServer webSocketServer:sessions){
            try {
                webSocketServer.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
