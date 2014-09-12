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

import tk.jacklin213.cardinal.Cardinal;

public class CmdLog extends Command {
	
	private static final String NAME = "log";
	private static final boolean OPCMD = true;
	
	public CmdLog() {
		super(NAME, OPCMD);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!Cardinal.logToFile) {
				Cardinal.logToFile = true;
			}
			if (Cardinal.logToFile) {
				Cardinal.logToFile = false;
			}
		}
	}

}
