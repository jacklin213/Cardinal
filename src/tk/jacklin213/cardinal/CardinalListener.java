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

public class CardinalListener extends ListenerAdapter<Cardinal> {
	
	//private static final String CMD_PREFIX = "!";
	
	@Override
	public void onJoin(JoinEvent<Cardinal> event) throws Exception {
		event.getChannel().send().message("System initiated. Ready to work");
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
