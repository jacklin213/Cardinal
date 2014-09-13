package tk.jacklin213.cardinal.configuration.utils;

import java.util.Map;

import org.apache.commons.lang3.Validate;

public class Config extends ConfigSection {
	protected Config defaults;
	private boolean copyDefaults = false;
	
	public Config() {}
	
	public Config(Config defaults) {
        this.defaults = defaults;
    }
	
	public void addDefault(String path, Object value) {
        Validate.notNull(path, "Path may not be null");

        if (defaults == null) {
            defaults = new Config();
        }

        defaults.set(path, value);
    }
	
	public void addDefaults(Map<String, Object> defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            addDefault(entry.getKey(), entry.getValue());
        }
    }

    public void addDefaults(Configuration defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        addDefaults(defaults.getValues(true));
    }

    public void setDefaults(Config defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        this.defaults = defaults;
    }
	
	public void setCopyDefaults(boolean copyDefaults) {
		this.copyDefaults = copyDefaults;
	}
	
	
	public Config getDefaults() {
		return defaults;
	}
	
	public boolean copyDefaults() {
		return copyDefaults;
	}
	
	public ConfigSection getParent() {
        return null;
    }
}
