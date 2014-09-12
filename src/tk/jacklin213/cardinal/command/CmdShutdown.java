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

import tk.jacklin213.cardinal.Cardinal;

public class CmdShutdown extends Command {

	private static final String NAME = "shutdown";
	private static final boolean OPCMD = true;
	
	public CmdShutdown() {
		super(NAME, OPCMD);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender.getName() != "jacklin213" && sender.getUser().isVerified()) {
				sender.broadcastMessage("Shutdown sequence initiated...");
				sender.broadcastMessage("Disconnecting");
				for (Channel channel : sender.getBotInvolved().getChannels()) {
					channel.send().part("Shut down by command");
				}
				//Cardinal.getInstance().stopConnection();
				Cardinal.SYSO("Disconnecting from server...");
				Cardinal.SYSO("Disconnect complete");
				System.exit(0);
			}
		}
	}

}
