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

import org.jibble.pircbot.PircBot;

public class Cardinal extends PircBot {
	
	private static final Logger LOGGER = Logger.getLogger(Cardinal.class.getName());
	private static final String LOG_PREFIX = "[Cardinal] ";
	private File dataFolder;
	String[] channels = {"jacklin213"};
	
	private Cardinal() {
		
	}
	
	public static void main(String[] args) {
		Cardinal cardinal = new Cardinal();
		cardinal.setup();
		SYSO(Level.INFO, "Cardinal is now initializing...");
		cardinal.connect();
		SYSO(Level.INFO, "Initialization complete!");
	}

	public void connect() {
		
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
