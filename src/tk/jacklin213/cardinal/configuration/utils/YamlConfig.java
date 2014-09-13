package tk.jacklin213.cardinal.configuration.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import tk.jacklin213.cardinal.Cardinal;

public class YamlConfig extends Config implements YamlConfigInterface {

	private Yaml yaml = new Yaml();
	
	@Override
	public void save(File file) throws IOException {
		Validate.notNull(file, "File cannot be null");
		 
		if(!file.exists()) file.mkdirs();
		
		 String data = saveToString();
		 Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		 
		 try {
			 writer.write(data);
		 } finally {
			 writer.close();
		 }
	}

	@Override
	public void load(File file) throws FileNotFoundException, IOException, YAMLException {
		Validate.notNull(file, "File cannot be null");
		 
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		StringBuilder builder = new StringBuilder();
		
		try {
			String line;
			while ((line = reader.readLine()) != null){
				 builder.append(line);
	             builder.append('\n');
			}
		} finally {
			reader.close();
			input.close();
		}
		
		loadFromString(builder.toString());
	}
	
//	@Deprecated
//	public void printConfig() {
//		for (String key : configMap.keySet()) {
//			if (!(configMap.get(key) instanceof Map<?, ?>)) {
//				Cardinal.SYSO(key +  " =  "+ configMap.get(key));
//				continue;
//			}
//			for (String innerKey : ((Map<String, Object>) configMap.get(key)).keySet()) {
//				Cardinal.SYSO(innerKey +  " =  "+ ((Map<String, Object>) configMap.get(key)).get(innerKey));
//			}
//		}
//	}

	public void printlnConfig() {
		Cardinal.SYSO("Config paths: ");
		for (String key : this.getKeys(true)) {
			Cardinal.SYSO(key);
		}
	}
	
	protected void convertMapsToSections(Map<?, ?> input, ConfigSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }
	
	public static YamlConfig loadConfiguration(File file, boolean createIfNotExist) {
		YamlConfig config = new YamlConfig();
		if (createIfNotExist) {
			try {
				config.save(file);
				return config;
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		try {
			config.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public static YamlConfig loadConfiguration(File file) {
		return loadConfiguration(file, false);
	}

	public String saveToString() {
		String dump = yaml.dump(getValues(false));
		if (dump.equals("{}\n")) {
			dump = "";
		}
		return dump;
	}
	 
	public void loadFromString(String contents) throws YAMLException {
		Validate.notNull(contents, "Contents cannot be null");

		Map<?, ?> input;
		input = (Map<?, ?>) yaml.load(contents);
		
		if (input != null) {
			convertMapsToSections(input, this);
		}
	}
	
}
