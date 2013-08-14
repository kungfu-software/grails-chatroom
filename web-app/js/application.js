if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		})
	})(jQuery);

	$(function() {
		// Set button disabled
		$('textarea').val('');
		$('#send-message-btn').attr("disabled", "disabled");

		// Append a change event listener to you inputs
		$('textarea').keyup(function() {
			// Validate your form here, example:
			var validated = true;
			if ($('textarea').val().length === 0)
				validated = false;

			// If form is validated enable form
			if (validated)
				$("#send-message-btn").removeAttr("disabled");
			else
				$('#send-message-btn').attr("disabled", "disabled");
		});
	});

}
