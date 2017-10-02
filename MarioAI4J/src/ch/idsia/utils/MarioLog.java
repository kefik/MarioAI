package ch.idsia.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import cz.cuni.amis.utils.simple_logging.SimpleLogging;

public class MarioLog {
	
	public static final Level INITIAL_LOG_LEVEL = Level.INFO;

	public static final Logger LOG;
	
	private static boolean enabled = true;
	
	private static Level lastLevel = INITIAL_LOG_LEVEL;
	
	public static void error(String msg) {
		log(Level.SEVERE, msg);
	}
	
	public static void warn(String msg) {
		log(Level.WARNING, msg);
	}
	
	public static void info(String msg) {
		log(Level.INFO, msg);
	}
	
	public static void fine(String msg) {
		log(Level.FINE, msg);
	}
	
	public static void trace(String msg) {
		log(Level.FINEST, msg);
	}
	
	public static void log(Level level, String msg) {
		LOG.log(level, msg);
	}
	
	public static void setLogLevel(Level newLevel) {
		if (enabled) {
			LOG.setLevel(newLevel);
		} else {
			lastLevel = newLevel;
		}
	}
	
	public static void enable() {
		if (enabled) return;
		enabled = true;
		LOG.setLevel(lastLevel);
	}
	
	public static void disable() {
		if (!enabled) return;
		enabled = false;
		lastLevel = LOG.getLevel();		
		LOG.setLevel(Level.OFF);
	}
	
	static {
		SimpleLogging.initLogging(true);
		LOG = Logger.getAnonymousLogger();
		LOG.setLevel(INITIAL_LOG_LEVEL);
		enable();
	}
	
}
