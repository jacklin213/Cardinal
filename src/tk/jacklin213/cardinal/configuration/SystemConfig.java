package tk.jacklin213.cardinal.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import tk.jacklin213.cardinal.Cardinal;
import tk.jacklin213.cardinal.configuration.utils.Config;
import tk.jacklin213.cardinal.configuration.utils.YamlConfig;

public class SystemConfig extends Config {

	public SystemConfig() {
		this.addDefault("Nick", "LinTest");
		this.addDefault("NickServ.Enabled", true);
		this.addDefault("NickServ.PasswordProtected", false);
		this.addDefault("NickServ.Login", "LinLogin");
		this.addDefault("NickServ.Password", "change");
		this.addDefault("Server", "irc.esper.net");
		this.addDefault("AutoJoinChannels", new ArrayList<String>(Arrays.asList("jacklin213", "cardinal", "drtshock")));
	}
	
	public YamlConfig createConfig() {
		return this.createConfig(null);
	}
	
	public YamlConfig createConfig(File file) {
		YamlConfig config = new YamlConfig();
		config.setDefaults(defaults);
		try {
			if (file == null) {
				config.save(new File(Cardinal.getDataFolder(), "config.yml"));
				return config;
			}
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public YamlConfig getConfig() {
		YamlConfig config = new YamlConfig();
		config.setDefaults(defaults);
		return config;
	}
}
