import net.seabookchen.grails.chatroom.Message
import net.seabookchen.grails.chatroom.User;

events = {
	 'afterPostMessage' browser:true // allows browser push on this topic
	 'afterJoinRoom' browser:true // allows browser push on this topic
}
