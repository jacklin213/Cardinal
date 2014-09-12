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
package tk.jacklin213.cardinal.test;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import tk.jacklin213.cardinal.CardinalListener;

public class MyBot {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Configuration<?> configuration = new Configuration.Builder()
	.setName("Test") //Set the nick of the bot. CHANGE IN YOUR CODE
	.setLogin("Cardinal")
	.setRealName("LinBot")
	.addListener(new CardinalListener())
	.setServerHostname("irc.esper.net") //Join the freenode network
	.addAutoJoinChannel("#jacklin213") //Join the official #pircbotx channel
	.buildConfiguration();
	
	public MyBot() throws Exception {
		//Create our bot with the configuration
        PircBotX bot = new PircBotX(configuration);
        //Connect to the server
        bot.startBot();
        bot.stopBotReconnect();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
        //Configure what we want our bot to do
        
	}

}
