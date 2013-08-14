<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
	</head>
	<body>
		<h2 class="prompt-style text-center">Welcome to Grails Chat Room App</h2>

		<div class="prompt-style align-center lead">
		    <p class="text-info">This is a demo App. Containing the following feature:</p>
		    <div class="text-left">
			    <ol>
			        <li>Oauth2.0 Login as username</li>
			        <li>Web socket and server side push to real time update the chatroom</li>
			        <li>Bootstrap css and Angular JS</li>
			    </ol>	
			</div>
			
			<g:if test="${flash.message}">
				<div class="alert alert-${flash.messageLevel}" style="width: 300px;">
	    			<button type="button" class="close" data-dismiss="alert">&times;</button>
	    			<strong>Heads up!</strong> ${flash.message}
	    		</div>
    		</g:if>

			<div class="social-login">
				<oauth:connect provider="facebook" id="facebook-connect-link" class="zocial facebook">Sign In with Facebook</oauth:connect>
			</div>
			<div class="social-login">
				<oauth:connect provider="linkedin" id="linkedin-connect-link" class="zocial linkedin social-loginin">Sign In with LinkedIn</oauth:connect>
			</div>
			<div class="social-login">
				<oauth:connect provider="twitter" id="twitter-connect-link" class="zocial twitter social-loginin">Sign In with Twitter</oauth:connect>
			</div>
			<div class="social-login">
				<oauth:connect provider="google" id="google-connect-link" class="zocial googleplus social-loginin">Sign In with Google</oauth:connect>
			</div>

		</div>

	</body>
</html>
