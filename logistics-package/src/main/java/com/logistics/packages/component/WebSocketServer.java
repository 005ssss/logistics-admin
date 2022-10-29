package com.logistics.packages.component;

import com.logistics.packages.entity.SocketEntity;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/pkg/websocket/{userName}")
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //在线客户端数量
    private static int  onlineCount = 0;
    //Map用来存储已连接的客户端信息
    private static ConcurrentHashMap<String, SocketEntity> websocketMap = new ConcurrentHashMap<>();
    //当前连接客户端的Session信息
    private Session session;
    //当前客户端名称
    private String userName="";

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName){
        if(!websocketMap.containsKey(userName)){
            WebSocketServer.onlineCount++;
        }
        this.session = session;
        this.userName = userName;
        SocketEntity socketEntity = new SocketEntity();
        socketEntity.setSession(session);
        socketEntity.setUri(session.getRequestURI().toString());
        websocketMap.put(userName, socketEntity);
        logger.info("用户连接："+ userName + "，人数:"+onlineCount);
        try {
            sendMessage("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClose
    public void onClose(){
        if(websocketMap.containsKey(userName)){
            websocketMap.remove(userName);
            onlineCount--;
            logger.info("用户关闭："+ userName + "，人数:"+onlineCount);
        }
    }

    @OnMessage
    public void onMessage(String message,Session session){
        if(StringUtil.isNotEmpty(message)){
            logger.info("收到用户消息:"+userName+",报文:"+message);
        }
    }
    //给当前客户端发消息
    private void sendMessage(String obj) {
        synchronized (session) {
            this.session.getAsyncRemote().sendText(obj);
        }
    }

    //给指定客户端发送消息，通过UserName找到Session发送
    private void sendMessageTo(String userName,String obj){
        SocketEntity socketEntity = websocketMap.get(userName);
        try {
            if(socketEntity !=null){
                socketEntity.getSession().getAsyncRemote().sendText(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    //给除了当前客户端的其他客户端发消息
    private void sendMessageToAllExpectSelf(String message, Session fromSession) {
        for(Map.Entry<String, SocketEntity> client : websocketMap.entrySet()){
            Session toSession = client.getValue().getSession();
            if( !toSession.getId().equals(fromSession.getId())&&toSession.isOpen()){
                toSession.getAsyncRemote().sendText(message);
                logger.info("服务端发送消息给"+client.getKey()+":"+message);
            }
        }
    }
    //给包括当前客户端的全部客户端发送消息
    private void sendMessageToAll(String message){
        for(Map.Entry<String, SocketEntity> client : websocketMap.entrySet()){
            Session toSession = client.getValue().getSession();
            if(toSession.isOpen()){
                toSession.getAsyncRemote().sendText(message);
                logger.info("服务端发送消息给"+client.getKey()+":"+message);
            }
        }
    }
    //给外部调用的方法接口
    public void sendAll(String Message){
        sendMessageToAll(Message);
    }

    public void sendMessage(String message, String userName){
        sendMessageTo(userName, message);
    }

}