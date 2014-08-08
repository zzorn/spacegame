package org.spacegame.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 *
 */
public class SpaceShader extends BaseShader {

    private ShaderProgram program;
    private Camera camera;
    private RenderContext context;
    private int u_projTrans;
    private int normalMatrixUniform;
    private int sunDirectionUniform;
    private int sunLightColorUniform;
    private int cameraPositionUniform;
    private int seaLevelUniform;
    private int specialTypeUniform;
    private int u_worldTrans;
    private int objectColorUniform;
    private int opacityUniform;

    private Matrix3 normalMatrix = new Matrix3();


    public SpaceShader() {
    }


    @Override public void init() {
        String vert = Gdx.files.internal("assets/shaders/oceanshader.vertex.glsl").readString();
        String frag = Gdx.files.internal("assets/shaders/oceanshader.fragment.glsl").readString();

        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());

        u_projTrans = program.getUniformLocation("u_projTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
        normalMatrixUniform = program.getUniformLocation("NormalMatrix");
        sunDirectionUniform = program.getUniformLocation("SunDirection");
        sunLightColorUniform = program.getUniformLocation("SunLightColor");
        cameraPositionUniform = program.getUniformLocation("CameraPosition");
        seaLevelUniform = program.getUniformLocation("SeaLevel");
        specialTypeUniform = program.getUniformLocation("SpecialType");
        objectColorUniform = program.getUniformLocation("ObjectColor");
        opacityUniform = program.getUniformLocation("Opacity");
    }

    @Override public void dispose() {
        program.dispose();
    }

    @Override
    public void begin (Camera camera, RenderContext context) {
        this.camera = camera;
        this.context = context;
        program.begin();
        program.setUniformMatrix(u_projTrans, camera.combined);

        /*
        // Sun position and color
        program.setUniformf(sunDirectionUniform, sea.getSunDirection());
        program.setUniformf(sunLightColorUniform, sea.getSunLightColor());
        program.setUniformf(cameraPositionUniform, camera.position);
        program.setUniformf(seaLevelUniform, sea.getAverageSeaLevel());
        */

        context.setDepthTest(GL20.GL_LEQUAL, 0.1f, 2000f);
        context.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render (Renderable renderable) {

        // Sky data
        final SpecialAttribute specialAttribute = (SpecialAttribute) renderable.material.get(SpecialAttribute.ATTRIBUTE);
        int specialType = specialAttribute == null ? 0 : specialAttribute.value;
        program.setUniformi(specialTypeUniform, specialType);

        program.setUniformMatrix(u_worldTrans, renderable.worldTransform);

        normalMatrix.set(renderable.worldTransform);
        if (normalMatrix.det() == 0) {
            // Broken world matrix, can't be inverted
            normalMatrix.idt();
        }
        else {
            normalMatrix.inv().transpose();
        }

        program.setUniformMatrix(normalMatrixUniform, normalMatrix);

        final Color diffuseColor = getColorAttribute(renderable, ColorAttribute.Diffuse, Color.WHITE);
        program.setUniformf(objectColorUniform, diffuseColor);

        // Setup blending
        final BlendingAttribute blendingAttribute = (BlendingAttribute) renderable.material.get(BlendingAttribute.Type);
        if (blendingAttribute != null) {
            context.setBlending(true, blendingAttribute.sourceFunction, blendingAttribute.destFunction);
            if (has(opacityUniform)) set(opacityUniform, blendingAttribute.opacity);
        } else {
            context.setBlending(false,  GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }

        renderable.mesh.render(program,
                               renderable.primitiveType,
                               renderable.meshPartOffset,
                               renderable.meshPartSize);
    }

    private Color getColorAttribute(Renderable renderable, final long type, final Color defaultColor) {
        final ColorAttribute colorAttribute = (ColorAttribute) renderable.material.get(type);
        if (colorAttribute != null) {
            return colorAttribute.color;
        }
        else {
            return defaultColor;
        }
    }

    @Override
    public void end () {
        program.end();
    }

    @Override
    public int compareTo (Shader other) {
        return 0;
    }
    @Override
    public boolean canRender (Renderable instance) {
        return true;
    }}
