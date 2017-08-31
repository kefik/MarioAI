Refactored MarioAI code from: http://code.google.com/p/marioai/

# MarioAI Version 0.3.1

![alt tag](https://github.com/kefik/MarioAI/raw/master/MarioAI4J/MatioAI4J.png)

## Project structure

**MarioAI4J** -> main project containing MarioSimulator (runnable as is), which is featuring keyboard-controlled mario, use arrows + A(jump) + S(peed/hoot) ... oh and do not forget to try to press 'G' (multiple times) to visualize Mario's receptive field

**MarioAI4J-Agents** -> example agents, this project must reference MarioAI4J in order to compile

**MarioAI4J-Tournament** -> provides means for agent evaluations from console (check EvaluateAgentConsole class) producing detailed CSV reports.

**MarioAI4J-Playground** -> here you can start coding your own Mario AI right away, just navigate to MarioAgent and fool around (required MarioAI4J, MarioAI4J-Tournament + its libs on path).

I did not mavenized projects as I usually do... so far you have to setup them within your IDE manually, but as they need only 2 libraries and projects feature rather standard Java project layout, it is trivial task (projects are directly importable into Eclipse).

## Change Log

Things, which are different from original MarioAI v0.2.0 project:

1) only JAVA agents can be used now;

2) GameViewer + ToolsConfigurator are not working (not refactored/broken);

3) New agent base types: especially interesting is MarioHijackAIBase, which is an agent you can interrupt anytime during its run, hijacking its controls and manually control Mario from keyboard (press 'H'ijack to start controlling agent manually) - this is great for debugging as you can let your AI to run "dry" and watch logs / debug draws as you position MARIO into a concrete situation; see next section for more information about "extra debug stuff controls" you can use with
MarioHijackAIBase

4) Agents now may implement IMarioDebugDraw interface, which is a callback that is regularly called to render custom debugging information inside the visualization component;

5) All options are now grouped within MarioOptions class that contains different enums for different types of options (boolean, int, float, string) ... these options are then read and used by respective option categories (AIOptions, LevelOptions, SimulationOptions, SystemOptions, VisualizationOptions);

6) Options string can now be created fast using constants and functions from FastOpts;

7) Running simulation is now encapsulated within MarioSimulator class that is instantiated using some "options" and then can be used to run(IAgent);

8) **WARNING - 1 JVM can run 1 SIMULATION** (visualized or headless) at max (I did not change the original architecture that is using statics a lot).

9) Agent environment interface has been refactored, many information is now encapsulated inside enums and classes rather than bytes, ints, named constants and C-like function calls ... especially querying tiles / entities has been simplified by implementing Tiles and Entities sensory modules;

10) Mario controls has been refactored as well, pressing/releasing of buttons that control Mario is now encapsulated within MarioInput class. Or even better, use MarioControl instead of MarioInput as it brushes out shoot/sprint glitches.

11) Example agents have been reimplemented using new agent base classes and environment interface; they are more readable now;

12) Receptive field visualization has been fixed (now it correctly aligns with respective simulation tiles) + it now includes "relative position" / "tiles" / "entity" visualization modes so you can quickly see how to "reference concrete tile" / "what tiles are written within RF" / "what entities are present within RF";

13) Generalization of tiles / entities has been improved, especially it is very easy to query current speed of entities and relative position wrt. Mario position in pixels;

14) I've tried to javadoc crucial parts regarding Mario agent development, hope it saves some time to someone.

## MarioHijackAIBase 

All example AIs within project MarioAI4J-Agents and MarioAI4J-Playground are using this class as their base.

It allows you to visualize / perform extra debugging stuff, which is extremely useful when developing.

Follows the list of keys (controls) you can press when running visualized simulation:

1. "space" or "P": Pauses the simulation
2. "N": when the simulation is paused, this will poke the simulator to compute next frame, i.e., you can check step-by-step what is hapenning
3. "H": size the control of Mario, i.e., you may take control over Mario actions manually and navigate Mario into the situation you want to observe + you will still see what is the output of your AI!
4. "G": cycle between modes of receptive field visualization, see what is "around" your Mario; 0 == OFF, 1 == visualize receptive field grid with tile coordinates relative to Mario (see EntityType); 2 == grid visualizing tile types (see Tile), 3 == grid visualizing entities, 4 == grid visualizing highest threats on given tile (see EntityKind)
5. "O": freeze creatures, they will stop moving
6. "E": render extra debug stuff about your Mario, see _MarioHijackAIBase.debugDraw(...)_ for details
7. "L": render positions of sprites within the map
8. "F": Mario will start flying, good for quickly moving forward through the map back and forth
9. "Z": Toggle scale x2 of the visualization (scale x2 is broken on some systems, dunno why)
10. "+" / "-": adjust simulator FPS

## Cheers!
