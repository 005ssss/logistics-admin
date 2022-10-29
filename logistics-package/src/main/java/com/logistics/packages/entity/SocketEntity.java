package com.logistics.packages.entity;

import lombok.Data;

import javax.websocket.Session;

@Data
public class SocketEntity {

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //连接的uri
    private String uri;

}