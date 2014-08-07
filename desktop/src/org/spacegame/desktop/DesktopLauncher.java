package org.spacegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.spacegame.SpaceGame;

import java.io.File;

public class DesktopLauncher {
    private static final String PROGRAM_STRING = "spacegame";
    private static final String ASSET_SOURCE_PATH = "../../asset-sources/textures/";
    private static final String ASSET_TARGET_PATH = "./textures/";

    public static void main (String[] arg) {

        // Parse command line arguments
        final SpaceGameStartOptions spaceGameStartOptions = new SpaceGameStartOptions();
        CmdLineParser parser = new CmdLineParser(spaceGameStartOptions);
        try {
            parser.parseArgument(arg);
        } catch (CmdLineException e) {
            // Handle incorrect arguments
            System.err.println(e.getMessage());

            printHelp(parser);

            System.exit(1);
        }

        // Handle help
        if (spaceGameStartOptions.isPrintHelp()) {
            printHelp(parser);

            System.exit(0);
        }

        // Re-pack textures if desired
        if (spaceGameStartOptions.isPackTextures()) {
            // Clear out previous atlas
            File target = new File(ASSET_TARGET_PATH + "textures.json");
            if (target.exists()) target.delete();

            // Generate atlas
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 512;
            settings.maxHeight = 512;
            TexturePacker.process(settings, ASSET_SOURCE_PATH, ASSET_TARGET_PATH, "textures.json");
        }

        // Start game
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SpaceGame(), config);
	}

    private static void printHelp(CmdLineParser parser) {
        System.err.println();
        System.err.println("SpaceGame version ?.?");
        System.err.println("   A space action game.");
        System.err.println();
        System.err.println(" Usage: " + PROGRAM_STRING +" [options...] arguments...");
        parser.printUsage(System.err);
        System.err.println();
        System.err.println(" Example: "+PROGRAM_STRING+" -c myConfig.xml");
    }
}
