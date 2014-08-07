package org.spacegame.desktop;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Startup configuration for the SpaceGame.
 * Parsed from command line options.
 */
public class SpaceGameStartOptions {

    @Option(name="-p", aliases = {"--pack-textures"},usage="Reads all game textures from the ./asset-sources/textures/ directory and creates packed textures to the ./android/assets/textures/ directory.  This option is only intended for use during development.")
    private boolean packTextures;

    @Option(name="-c", aliases = {"--config"},usage="Configuration file to read",metaVar="<config file path>")
    private File configFile = new File("./config.xml");

    @Option(name="-h", aliases = {"--help"},usage="Prints help",help = true)
    private boolean printHelp = false;


    /**
     * Additional command line arguments.
     */
    @Argument
    private List<String> arguments = new ArrayList<String>();


    /**
     * @return true if textures should be repacked before starting.
     */
    public boolean isPackTextures() {
        return packTextures;
    }

    /**
     * @return path to config file.
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * @return additional arguments provided.
     */
    public List<String> getArguments() {
        return arguments;
    }

    public boolean isPrintHelp() {
        return printHelp;
    }
}
