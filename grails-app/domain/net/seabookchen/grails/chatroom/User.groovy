package net.seabookchen.grails.chatroom

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['username'])
class User {
	String firstname
	String lastname
	String username
	String gender
	String avartaUrl
	String color = Chatroom.getRandomColor()
	
	boolean fetched = true
	
    static constraints = {
		firstname nullable: true
		lastname nullable: true
		gender nullable: true
		avartaUrl nullable: true
    }
}
