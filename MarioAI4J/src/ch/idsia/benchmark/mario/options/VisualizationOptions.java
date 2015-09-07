package ch.idsia.benchmark.mario.options;

import java.awt.Point;

import ch.idsia.benchmark.mario.engine.SimulatorOptions;
import ch.idsia.benchmark.mario.engine.SimulatorOptions.ReceptiveFieldMode;
import ch.idsia.benchmark.mario.options.MarioOptions.BoolOption;
import ch.idsia.benchmark.mario.options.MarioOptions.IntOption;

/**
 * Read Visualization values from {@link MarioOptions}.
 * 
 * @author Jakub 'Jimmy' Gemrot, gemrot@gamedev.cuni.cz
 */
public class VisualizationOptions {

	public static void reset() {
		SimulatorOptions.isVisualization = isVisualization();
		SimulatorOptions.FPS = getFPS();
		
		SimulatorOptions.VISUAL_COMPONENT_HEIGHT = getViewportHeight();
		SimulatorOptions.VISUAL_COMPONENT_WIDTH = getViewportWidth();
		
		SimulatorOptions.receptiveFieldMode = getReceptiveFieldMode();
		if (SimulatorOptions.receptiveFieldMode == null) SimulatorOptions.receptiveFieldMode = ReceptiveFieldMode.NONE;
	}
	
	public static boolean isVisualization() {
		return MarioOptions.getInstance().getBool(BoolOption.VISUALIZATION);
	}
	
	public static boolean isReceptiveField() {
		return MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_RECEPTIVE_FIELD) > 0;
	}
	
	public static ReceptiveFieldMode getReceptiveFieldMode() {
		return ReceptiveFieldMode.getForCode(MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_RECEPTIVE_FIELD));
	}	
	
	public static boolean isViewAlwaysOnTop() {
		return MarioOptions.getInstance().getBool(BoolOption.VISUALIZATION_VIEW_ALWAYS_ON_TOP);
	}
	
	public static boolean isScale2x() {
		return MarioOptions.getInstance().getBool(BoolOption.VISUALIZATION_2X_SCALE);
	}
	
	public static boolean isGameViewer() {
		return MarioOptions.getInstance().getBool(BoolOption.VISUALIZATION_GAME_VIEWER);
	}
	
	public static boolean isGameViewerContinuousUpdate() {
		return MarioOptions.getInstance().getBool(BoolOption.VISUALIZATION_GAME_VIEWER_CONTINUOUS_UPDATED);
	}
	
	public static int getFPS() {
		return MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_FPS);
	}
	
	public static Point getViewLocation() {
		return new Point(MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_VIEW_LOCATION_X), MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_VIEW_LOCATION_Y)); 
	}
	
	public static int getViewportWidth() {
		return MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_VIEWPORT_WIDTH);
	}
	
	public static int getViewportHeight() {
		return MarioOptions.getInstance().getInt(IntOption.VISUALIZATION_VIEWPORT_HEIGHT);
	}
	
}
