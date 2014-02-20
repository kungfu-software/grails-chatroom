package net.seabookchen.grails.chatroom

import grails.chatroom.ChatroomListener
import grails.converters.JSON

class MessageController {
	
    def postMessage() {
		String username = params.username
		String message = params.message
		String color = params.color
		
		Message m = new Message(username: username, message: message, color: color)
		
		Chatroom.messages.add(m)
		
		if ( Chatroom.messages.size() > 1000) {
			Chatroom.messages.drop(500)
		}
		
		def messageJson =  m as JSON
		println messageJson
		event(topic:'afterPostMessage', data:JSON.parse(messageJson.toString()))
		
		render messageJson
	}
}
