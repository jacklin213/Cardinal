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

import java.util.HashMap;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import tk.jacklin213.cardinal.Cardinal;

public class CommandManager extends ListenerAdapter<Cardinal> {

	private static final String CMD_PREFIX = "./";
	private HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public CommandManager() {
		this.registerCommands();
	}
	
	public void registerCommand(Command command) {
		commands.put(command.getName(), command);
	}
	
	private void registerCommands() {
		registerCommand(new CmdJoin());
		registerCommand(new CmdList());
		registerCommand(new CmdLog());
		registerCommand(new CmdShutdown());
		registerCommand(new CmdTest());
	}
	
	@Override
	public void onMessage(MessageEvent<Cardinal> event) throws Exception {
		CommandSender sender = new CommandSender(event.getChannel(), event.getUser(), event.getBot().getUserBot());
		String message = event.getMessage();
		if (Cardinal.logToFile) Cardinal.SYSO(message);
		if (message.startsWith(CMD_PREFIX)) {
			String args[] = message.substring(CMD_PREFIX.length()).split(" ");
			if (commands.containsKey(args[0])) {
				commands.get(args[0]).run(sender, shrinkArgs(args));
			}
		}
	}
	
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	
	public String getCommandsList() {
		String list = "";
		int i = 0;
		for (String key : commands.keySet()) {
			list += key + (i + 1 <= commands.size() ? ", " : ".");
			i++;
		}
		return list;
	}
	
	public String[] shrinkArgs(String args[]) {
        // Shift args down
        String[] newArgs = new String[args.length-1];
        for (int i = 1; i < args.length; i++) {
            newArgs[i-1] = args[i];
        }
        return newArgs;
    }
}
