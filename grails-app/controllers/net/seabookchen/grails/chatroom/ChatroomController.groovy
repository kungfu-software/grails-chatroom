package net.seabookchen.grails.chatroom

class ChatroomController {
	
	def loggedIn() {
		if (session.currentUser) {
			redirect(action: 'show')
			return
		}
		
		render (view:'/index')
	}
	
	def show() {
		println session.currentUser
		
		if (session.currentUser) {
			render(view: '/chatroom')
			return
		}
		
		flash.message = "You are not signed In yet !!!"
		flash.messageLevel = "error"
		
		redirect (uri: '/')
	}
	
	def leave() {
		
		User user = session.currentUser
		
		flash.message = "$user.username left the chatroom"
		flash.messageLevel = "info"
		
		Chatroom.users.remove(user)
		
		session.currentUser = null
		
		redirect (uri: '/')
	}
}
