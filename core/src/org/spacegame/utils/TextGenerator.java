package org.spacegame.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;

import static org.flowutils.MathUtils.map;

/**
 * Generates texts as adjacent blocks with a textured character on each.
 */
public final class TextGenerator {

    private static final int ATTRIBUTES = VertexAttributes.Usage.Position |
                                          VertexAttributes.Usage.Normal |
                                          VertexAttributes.Usage.TextureCoordinates;

    private final Texture fontTexture;
    private final int textureWidthChars;
    private final int textureHeightChars;
    private final int firstCharAscii;
    private final int unknownCharId;

    private Material material;
    private Matrix4 transform = new Matrix4();

    public TextGenerator() {
        this("assets/fonts/rustfont.png");
    }

    public TextGenerator(String fontPicturePath) {
        this(new Texture(new FileHandle(fontPicturePath)), 16, 6, 32);
    }

    public TextGenerator(Texture fontTexture, int textureWidthChars, int textureHeightChars, int firstCharAscii) {
        this.fontTexture = fontTexture;
        this.textureWidthChars = textureWidthChars;
        this.textureHeightChars = textureHeightChars;
        this.firstCharAscii = firstCharAscii;

        // Use space for unknown characters if it is included in the font.
        int uc = ' ';
        if (!isSupported(uc)) uc = 0;
        unknownCharId = uc;

        material = new Material(TextureAttribute.createDiffuse(fontTexture));
    }

    /**
     * Generates text along x axis, with y axis upward and facing the z axis.
     *
     * @param text text to generate.
     * @param charWidth width of each character
     * @param charHeight height of each character
     * @param charThickness thickness of each character
     * @param centerX if true, centered along x, otherwise will start at 0 x and extend in positive direction.
     * @param centerY if true, centered along y, otherwise will start at 0 y and extend in positive direction.
     * @param centerZ if true, centered along z, otherwise will start at 0 z and extend in positive direction.
     * @return model with the text.
     */
    public Model createTextLine(String text,
                                float x,
                                float y,
                                float z,
                                float charWidth,
                                float charHeight,
                                float charThickness,
                                boolean centerX,
                                boolean centerY,
                                boolean centerZ,
                                boolean rescaleWidth) {

        // Start building a model
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        createTextLine(modelBuilder, text, x, y, z, charWidth, charHeight, charThickness, centerX, centerY, centerZ, rescaleWidth);

        // Generate model
        return modelBuilder.end();
    }

    /**
     * Generates text along x axis, with y axis upward and facing the z axis.
     *
     * @param modelBuilder builder to add the characters to.
     * @param text text to generate.
     * @param charWidth width of each character
     * @param charHeight height of each character
     * @param charThickness thickness of each character
     * @param centerX if true, centered along x, otherwise will start at 0 x and extend in positive direction.
     * @param centerY if true, centered along y, otherwise will start at 0 y and extend in positive direction.
     * @param centerZ if true, centered along z, otherwise will start at 0 z and extend in positive direction.
     */
    public void createTextLine(ModelBuilder modelBuilder,
                                String text,
                                float x,
                                float y,
                                float z,
                                float charWidth,
                                float charHeight,
                                float charThickness,
                                boolean centerZ,
                                boolean centerX,
                                boolean centerY,
                                boolean rescaleWidth) {
        // Start building a new node
        modelBuilder.node();

        // Rotate the characters correctly
        // TODO: This messes up with the other coordinates, would be better to just edit the UV coords.
        transform.idt().setToRotation(0, 1, 0, 90);

        // Get text length
        int num = text.length();

        // Rescale width to fit in specified width if desired
        if (rescaleWidth) {
            charWidth = charWidth / num;
        }

        // Determine starting coordinates.  Center the desired axes
        float xPos = -z;
        float yPos = y;
        float zPos = x;
        if (centerX) xPos -= charHeight * 0.5f;
        if (centerY) yPos -= charThickness * 0.5f;
        if (centerZ) zPos -= num * charWidth * 0.5f;

        // Loop the chars
        for (int i = 0; i < num; i++) {
            // Get char
            int c = text.charAt(i);

            // Replace out of range chars with a default
            if (!isSupported(c)) {
                c = unknownCharId;
            }

            // Determine the part of the texture to use
            int charIndex = c - firstCharAscii;
            int charPosU = charIndex % textureWidthChars;
            int charPosV = charIndex / textureWidthChars;

            float charU1 = map(charPosU, 0, textureWidthChars, 0, 1);
            float charV1 = map(charPosV, 0, textureHeightChars, 0, 1);
            float charU2 = charU1 + 1f / textureWidthChars;
            float charV2 = charV1 + 1f / textureHeightChars;

            // Generate block
            final MeshPartBuilder part = modelBuilder.part("char", GL20.GL_TRIANGLES, ATTRIBUTES, material);
            part.setUVRange(charU1, charV1, charU2, charV2);
            part.setVertexTransform(transform);
            part.box(xPos + charHeight * 0.5f,
                     yPos +  charThickness* 0.5f,
                     zPos + charWidth * 0.5f,
                     charHeight,
                     charThickness,
                     charWidth);

            // Move to next pos
            zPos += charWidth;
        }

    }

    /**
     * @return true if the specified character can be rendered.
     */
    public boolean isSupported(int c) {
        final int maxChar = firstCharAscii + textureHeightChars * textureWidthChars;
        return c >= 0 && c < maxChar;
    }

}
