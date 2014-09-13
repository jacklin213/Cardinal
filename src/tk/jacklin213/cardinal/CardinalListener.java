/* This File is part of Cardinal
 * 
 * Copyright (C) 2014 jacklin213
 * 
 * Cardinal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package tk.jacklin213.cardinal;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CardinalListener extends ListenerAdapter<Cardinal> {
	
	//private static final String CMD_PREFIX = "!";
	private int counter = 0;
	
	//Buggy
//	@Override
//	public void onJoin(JoinEvent<Cardinal> event) throws Exception {
//		if (event.getUser().getBot().getBotId() == event.getBot().getBotId()) {
//			event.getChannel().send().message("System initiated. Ready to work");
//		}
//	}
	
	@Override
	public void onGenericMessage(GenericMessageEvent<Cardinal> event) throws Exception {
		for (String string : event.getMessage().split(" ")) {
			if (string.equalsIgnoreCase("eta")) {
				counter += 1;
				event.respond("There is NO ETA for the Sponge project or SpongeAPI as a whole. Every time you ask, the project gets moved back an hour. Please do not.");
				if (counter % 5 == 0 || counter == 1) {
					event.getBot().sendRaw().rawLine("Current ETA count number: " + counter);
				}
			}
		}
	}
	
//	@Override
//	public void onGenericMessage(GenericMessageEvent<Cardinal> event) throws Exception {
//		if (event.getMessage().startsWith(CMD_PREFIX)) {
//			if (event.getMessage().equalsIgnoreCase(CMD_PREFIX + "test")) {
//				event.respond("Hello, I am working");
//			}
//			if (event.getMessage().equalsIgnoreCase(CMD_PREFIX + "whois")) {
//				event.respond("Channels op'ed in: ");
//				for (Channel channel : event.getUser().getChannelsOpIn()) {
//					event.respond(channel.getName());
//				}
//			}
//			// OP's only commands
//			if (event.getMessage().equalsIgnoreCase(CMD_PREFIX + "shutdown")) {
//				if (event.getUser().isIrcop()) {
//					event.respond("Shutting down");
//				} else {
//					event.respond("Your not op");
//				}
//			}
//		}
//	}
}
