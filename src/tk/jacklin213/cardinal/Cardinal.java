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

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import tk.jacklin213.cardinal.command.CommandManager;
import tk.jacklin213.cardinal.configuration.ConfigHandler;

public class Cardinal extends PircBotX {
	
	public ConfigHandler configHandler = new ConfigHandler(this);
	public static CommandManager commandManager = new CommandManager();
	public static boolean logToFile = false;
	private static Cardinal instance;
	private static final Logger LOGGER = Logger.getLogger(Cardinal.class.getName());
	private static final String LOG_PREFIX = "[Cardinal] ";
	private File dataFolder;
	String[] channels = {"jacklin213"};
	
	public static Cardinal getInstance() {
		return instance;
	}
	
	private Cardinal() {
		super(new Configuration.Builder<Cardinal>()
		.setName("Cardinal") //Set the nick of the bot. CHANGE IN YOUR CODE
		.setVersion("v1.1")
		.setNickservPassword("")
		.setLogin("Cardinal")
		.setRealName("LinBot")
		.addListener(new CardinalListener())
		.addListener(commandManager)
		.setServerHostname("irc.esper.net") //Join the freenode network
		.addAutoJoinChannel("#jacklin213") //Join the official #pircbotx channel
		.addAutoJoinChannel("#cardinal")
		.addAutoJoinChannel("#drtshock")
		.buildConfiguration());
	}
	
	public static void main(String[] args) {
		Cardinal cardinal = new Cardinal();
	    cardinal.setup();
		SYSO(Level.INFO, "Cardinal is now initializing...");
		cardinal.startConnection();
		
		SYSO(Level.INFO, "Initialization complete!");
	}

	public void startConnection() {
		SYSO("Connecting to server...");
		try {
			this.connect();
			SYSO("Successfully connected to " + this.getServerInfo().getNetwork());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stopConnection() {
		SYSO("Disconnecting from server...");
		SYSO("Disconnect complete");
		System.exit(0);
	}
	
	public static void SYSO(Level level, String msg) {
		LOGGER.log(level, LOG_PREFIX + msg);
	}
	
	public static void SYSO(String msg) {
		SYSO(Level.INFO, msg);
	}
	
	private void setup() {
		dataFolder = new File(System.getProperty("user.dir"));
		// Setup logging to file
		FileHandler fh; 
		try {
			LOGGER.setUseParentHandlers(false);
			fh = new FileHandler(getDataFolder().getAbsolutePath() + "/cardinal.log");
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);
			SYSO(Level.INFO, "Logger ready");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getDataFolder() {
		return this.dataFolder;
	}
	
}
