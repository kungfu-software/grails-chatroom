package grails.chatroom

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import grails.converters.JSON
import grails.events.Listener
import net.seabookchen.grails.chatroom.Chatroom;
import net.seabookchen.grails.chatroom.User


class ChatroomListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		println "session created"
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		User user = httpSessionEvent.getSession().getAttribute("currentUser")
		Chatroom.users.remove(user)
		httpSessionEvent.getSession().setAttribute("currentUser", null)
		
		println "The $user.username is left ... "
	}
	
}
	
