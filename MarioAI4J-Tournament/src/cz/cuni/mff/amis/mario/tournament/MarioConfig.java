package cz.cuni.mff.amis.mario.tournament;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarioConfig {
	
	private String options;
	private int repetitions;
	private int seed = -1;
	
	public MarioConfig() {		
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
		if (this.options == null) {
			seed = -1;
		} else {
			Pattern p = Pattern.compile("ls (-?[0-9]+)");
			Matcher m = p.matcher(this.options);
			while (m.find()) {
				// get the last seed (the last one is used...)
				seed = Integer.parseInt(m.group(1));
			}			
		}
	}

	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) {
		this.repetitions = repetitions;
	}

	public int getSeed() {
		return seed;
	}
	
}
