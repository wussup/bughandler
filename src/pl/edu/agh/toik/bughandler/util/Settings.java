package pl.edu.agh.toik.bughandler.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Load settings from settings file
 */
public class Settings {

	/**
	 * Map with settings from settings file
	 */
	private Map<String, String> settings = new LinkedHashMap<String, String>();

	/**
	 * Constructor, do all job
	 */
	public Settings() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("local.settings"));

			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] splittedLine = line.split("=");
					if (splittedLine.length == 2)
						settings.put(splittedLine[0], splittedLine[1]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return map with settings from settings file
	 * 
	 * @return map with settings
	 */
	public Map<String, String> getSettings() {
		return settings;
	}
}
