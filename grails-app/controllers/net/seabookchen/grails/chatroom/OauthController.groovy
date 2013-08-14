package net.seabookchen.grails.chatroom


import grails.converters.JSON
import grails.converters.XML

import org.scribe.model.Token
import org.springframework.core.io.Resource

import uk.co.desirableobjects.oauth.scribe.OauthService

class OauthController {
	OauthService oauthService
	def grailsApplication

	def facebookSuccess() {
		Token facebookAccessToken = session[oauthService.findSessionKeyForAccessToken('facebook')]

		def fResource = oauthService.getFacebookResource(facebookAccessToken, 'https://graph.facebook.com/me')

		def fResponse = JSON.parse(fResource?.getProperties().get("body"))
		
		
		def facebookPic = oauthService.getFacebookResource(facebookAccessToken, 'http://graph.facebook.com/sarfraz.anees/picture')

		println fResponse

		def userInfo = new User();
		userInfo.firstname =  fResponse.first_name
		userInfo.lastname = fResponse.last_name
		userInfo.username = fResponse.name
		userInfo.gender = fResponse.gender
		
		String facebookPicProfileUrl = String.format('http://graph.facebook.com/%s/picture?width=128&height=128', fResponse.username)
		userInfo.avartaUrl = facebookPicProfileUrl
		
		userInfo = handelUserInfo(userInfo)
		
		normalizedUserInfo(userInfo)
		
		if (userInfo)
			redirect (uri: "/chatroom")
		//render (view: "/chatroom", model: [user: user, socialNetwork: 'Facebook'])
	}

	def linkedinSuccess() {
		Token linkedInAccessToken = session[oauthService.findSessionKeyForAccessToken('linkedin')]

		def lResource = oauthService.getLinkedinResource(linkedInAccessToken, 'http://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-url)')

		println lResource?.getBody()
		
		def person = XML.parse(lResource?.getBody())
			
		def userInfo = new User();
		userInfo.firstname =  person."first-name".text()
		userInfo.lastname = person."last-name".text()
		userInfo.username = person."first-name".text() + person."last-name".text()
		userInfo.gender = 'Male'
		userInfo.avartaUrl = person."picture-url".text()
		
		normalizedUserInfo(userInfo)
		
		userInfo = handelUserInfo(userInfo)
		if (userInfo)
			redirect (uri: "/chatroom")
		//render (view: "/chatroom", model: [user: userInfo, socialNetwork: 'LinkedIn'])
	}
	
	def twitterSuccess() {
		Token twitterAccessToken = session[oauthService.findSessionKeyForAccessToken('twitter')]
		
        def resourceURL = "https://api.twitter.com/1.1/account/settings.json"
        def settingsResource = oauthService.getTwitterResource(twitterAccessToken, resourceURL)
        def settingsResponse = JSON.parse(settingsResource?.getBody())
		
        def twitterUserDetailsURL = "https://api.twitter.com/1.1/users/show.json?screen_name=" + settingsResponse['screen_name']
        def userDetailsResource = oauthService.getTwitterResource(twitterAccessToken, twitterUserDetailsURL)
        def userDetailsJson = JSON.parse(userDetailsResource?.getBody())
		println userDetailsJson
		
		def userInfo = new User();
		userInfo.firstname =  userDetailsJson.name.split(' ')[0]
		userInfo.lastname = userDetailsJson.name.split(' ')[1]
		userInfo.username = userDetailsJson.name
		userInfo.gender = 'n/a'
		userInfo.avartaUrl = userDetailsJson.profile_image_url
		
		normalizedUserInfo(userInfo)
		
		userInfo = handelUserInfo(userInfo)
		
		if (userInfo)
			redirect (uri: "/chatroom")
		//render (view: '/chatroom', model: [user: userInfo, socialNetwork: 'Twitter'])
     
	}
	
	def googleSuccess() {
		Token token = session[oauthService.findSessionKeyForAccessToken('google')]
		
		def userInfoResource = oauthService.getGoogleResource(token, 'https://www.googleapis.com/oauth2/v1/userinfo?alt=json')
		def userInfoJson = JSON.parse(userInfoResource?.getBody())
	
		def userInfo = new User();
		userInfo.firstname =  userInfoJson.first_name
		userInfo.lastname = userInfoJson.given_name
		userInfo.username = userInfoJson.name
		userInfo.gender = userInfoJson.gender
		userInfo.avartaUrl = userInfoJson.picture
		
		normalizedUserInfo(userInfo)
		
		userInfo = handelUserInfo(userInfo)
		if (userInfo)
			redirect (uri: "/chatroom")
		//render (view: "/chatroom", model: [user: userInfo, socialNetwork: 'Google'])
		
	}
	
	def handelUserInfo(User user) {
		
		if (session.currentUser) {
			println "User $user.username is logged in already"
			flash.message = "User $user.username is logged in already"
			redirect (uri: "/error")
			
			return null
		}		

		def userInRoom = null
		if (Chatroom.users.contains(user)) {
			int index = Chatroom.users.indexOf(user)
			userInRoom = Chatroom.users.get(index)
		} else {
			userInRoom = user
			Chatroom.users.add(userInRoom)
			
			def userJson =  user as JSON
			event(topic:'afterJoinRoom', data:JSON.parse(userJson.toString()))
		}
		
		session.currentUser = userInRoom
		
		
		return userInRoom
		
	}
	
	def normalizedUserInfo(User userInfo) {
		if (!userInfo.firstname || userInfo.firstname.size() == 0) {
			userInfo.firstname = userInfo.username[0 .. 6] + "..."
		}
		
		if (!userInfo.avartaUrl || userInfo.avartaUrl.size() == 0) {
			userInfo.avartaUrl = request.getContextPath() + "/images/default_avarta.png"
		}
	}
}