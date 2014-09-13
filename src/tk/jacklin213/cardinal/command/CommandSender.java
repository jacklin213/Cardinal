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
package tk.jacklin213.cardinal.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * Creates a new Command sender through {@link CommandSender#CommandSender(Channel, User, String, boolean, boolean) constructor}
 * @author jacklin213
 *
 */
public class CommandSender {
	
	private String name;
	private Channel channel;
	private User user;
	private User bot;
	private boolean op;
	private boolean voice;
	
	/**
	 * Creates a new Command sender
	 * @param channel
	 * @param user
	 * @param name
	 * @param op
	 * @param voice
	 */
	public CommandSender(Channel channel, User user, User bot) {
		this.name = user.getNick();
		this.channel = channel;
		this.user = user;
		this.bot = bot;
		this.op = channel.isOp(user);
	}
	
	public void broadcastMessage(String message) {
		this.channel.send().message(message);
	}
	
	public void sendMessage(User user, String message) {
		this.channel.send().message(user, message);
	}
	
	public void sendPrivateMessage(String message) {
		this.user.send().message(message);
	}

	public String getName() {
		return name;
	}

	public boolean isOp() {
		return op;
	}

	public boolean isVoice() {
		return voice;
	}

	public Channel getChannel() {
		return channel;
	}

	public User getUser() {
		return user;
	}
	
	public User getBotInvolved() {
		return bot;
	}

}
