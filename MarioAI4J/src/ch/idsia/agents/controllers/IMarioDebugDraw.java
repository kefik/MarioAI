package ch.idsia.agents.controllers;

import java.awt.Graphics;

import ch.idsia.benchmark.mario.engine.LevelScene;
import ch.idsia.benchmark.mario.engine.VisualizationComponent;
import ch.idsia.benchmark.mario.environments.IEnvironment;

public interface IMarioDebugDraw {

	public void debugDraw(VisualizationComponent vis, LevelScene level, IEnvironment env, Graphics g);
	
}
