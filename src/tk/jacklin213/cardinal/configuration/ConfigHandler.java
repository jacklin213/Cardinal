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
package tk.jacklin213.cardinal.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import tk.jacklin213.cardinal.Cardinal;
import tk.jacklin213.cardinal.configuration.utils.ConfigType;
import tk.jacklin213.cardinal.configuration.utils.YamlConfig;


public class ConfigHandler {
	
	private Map<ConfigType, YamlConfig> configs = new HashMap<ConfigType, YamlConfig>();
	
	public ConfigHandler(Cardinal instance) {
//		this.registerConfig(ConfigType.SYSTEM, new SystemConfig().createConfig());
	}
	
	public void registerConfig(ConfigType type, YamlConfig config) {
		configs.put(type, config);
	}
	
	public void reloadConfig(ConfigType type, File configFile) {
		configs.put(type, YamlConfig.loadConfiguration(configFile));
	}
	
}
