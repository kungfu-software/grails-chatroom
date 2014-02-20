class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/chatroom"(controller:'chatroom', action:"show")
		"/"(controller:'chatroom', action:"loggedIn")
		"500"(view:'/error')
		"/error"(view:'/error')
	}
}
