package net.seabookchen.grails.chatroom

class Chatroom {
	static List<User> users = new ArrayList<User>();
	static List<Message> messages = new ArrayList<Message>();
	
	static List<String> colors = Arrays.asList("DeepPink", "Magenta", "MediumSeaGreen", "Red", "RoyalBlue", "black", "DarkOrange")
	static List<String> appearedColor = new ArrayList<String>();

	static Random rand = new Random()
	
	
    static String getRandomColor() {
		int size = colors.size()
		int bufferedSize = size - 2;
		
		String randomColor = colors.get(rand.nextInt(size))
		if (appearedColor.size() >= bufferedSize) {
			appearedColor.clear()
			return randomColor
		}
		
		
		if (appearedColor.contains(randomColor)) {
			return getRandomColor()
		}
		
		appearedColor.add(randomColor)
		return randomColor
	}
	
	static List<String> getHistoryMessages(int number) {
		List<String> latestMessages = messages.reverse(false)
		
		int messageSize = latestMessages.size()
		
		if (messageSize == 0) {
			return latestMessages
		}
		
		int max = 0
		if (messageSize > number) {
			max = number
		} else {
			max = messageSize 
		}
		
		return latestMessages[0 .. max - 1]
	}
	
	static boolean hasHistory() {
		return messages.size() > 0
	}
}
