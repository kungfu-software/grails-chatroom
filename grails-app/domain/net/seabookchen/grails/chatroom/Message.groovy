package net.seabookchen.grails.chatroom

class Message implements Serializable {
	String username
	String message
	String color
	
    static constraints = {
    }
}
