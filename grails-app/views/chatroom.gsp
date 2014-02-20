<%@page import="net.seabookchen.grails.chatroom.User" %>
<%@page import="net.seabookchen.grails.chatroom.Message" %>
<%@page import="net.seabookchen.grails.chatroom.Chatroom" %>

<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Welcome to Grails</title>

<r:require module="grailsEvents"/>
<r:script>
 var grailsEvents = new grails.Events("${createLink(uri: '')}");
 
 //will listen for server events on 'afterPostMessage' topic
 grailsEvents.on('afterPostMessage', function(data){
 	addMessage2Chatroom(data);
 }); 
 
 //will listen for server events on 'afterJoinRoom' topic
 grailsEvents.on('afterJoinRoom', function(data){
 	addUser2Chatroom(data);
 });
 
 function addUser2Chatroom(data) {
 	$('#chatroom-userlist-table tr:last').after('<tr><td><img class="user-avarta" src="' + data.avartaUrl + '" /></td><td>' + data.firstname + '</td><td><div class="user-color" style="background:' + data.color + '"></div></td></tr>');
 }
 
 function addMessage2Chatroom(data) {
 	$('#chatroom-contentbox').append('<div class="row"><div class="span1"></div><div class="label label-large span2" style="background-color:' + data.color + '">' + data.username +  ' : </div><div class="span9"><p class="lead-message">' + data.message + '</p></div></div>');
 }
 
 function cleanup() {
 	$('#chatroom-sendbox #message').val('');
 	var div = $('#chatroom-contentbox');
 	var scollHeight = div.scrollHeight;
 	div.scrollTop(scollHeight);
 }
 
</r:script>

</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div id="chatroom-userlist" class="span3">
				<table id="chatroom-userlist-table" class="table table-striped">
					<g:each var="user" in="${Chatroom.users}">
						<tr>
							<td><img  class="user-avarta" src="${user.avartaUrl }"/></td>
							<td>${user.firstname}</td>
							<td><div class="user-color" style="background:${user.color}"></div></td>
						</tr>
					</g:each>
				</table>
				
			</div>
			<div class="span9">
				<div id="error"></div>
				<div id="chatroom-contentbox">
				
					<g:if test="${Chatroom.hasHistory()}">
					
						<g:each var="message" in="${Chatroom.getHistoryMessages(3)}">
							<div class="row">
								<div class="span1"></div>
								<div class="label label-large span2" style="background-color:${message.color}">${message.username} : </div>
								<div class="span9"><p class="lead-message">${message.message}</p></div>
							</div>
						</g:each>
						
						<p class="lead"> ------------- Above is History Messages -------------- </p>
					
					</g:if>
					
				</div>
				<div id="chatroom-sendbox" class="row">
					<g:formRemote name="messageForm"
						url="[controller: 'message', action: 'postMessage']" onSuccess="cleanup()">
						<g:textArea name="message"
							placeholder="Start your chat from here ..." />
						<input type="hidden" name="username" value="${session.currentUser.firstname}" />
						<input type="hidden" name="color" value="${session.currentUser.color}" />
						<button id="send-message-btn" type="submit" class="btn btn-large">Send</button>
					</g:formRemote>
					<g:link controller="chatroom" action="leave" ><p class="text-info">Leave the room</p></g:link>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
