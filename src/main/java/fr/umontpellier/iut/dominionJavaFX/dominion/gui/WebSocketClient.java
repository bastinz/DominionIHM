/*
package fr.umontpellier.iut.dominionJavaFX.dominion.gui;

import fr.umontpellier.iut.dominionJavaFX.dominion.AppDominion;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/")
public class WebSocketClient {
    @OnOpen
    public void onOpen(Session session) {
        AppDominion.addClient(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        AppDominion.addInput(message);
    }

    @OnClose
    public void onClose(Session session) {
        AppDominion.removeClient(session);
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        exception.printStackTrace();
        System.err.println("Error for client: " + session.getId());
    }
}
*/
