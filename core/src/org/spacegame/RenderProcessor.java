package org.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.entityflow.component.Component;
import org.entityflow.entity.Entity;
import org.entityflow.processors.EntityProcessorBase;
import org.flowutils.LogUtils;
import org.flowutils.time.Time;
import org.slf4j.Logger;

/**
 * Processor that handles rendering of all visible entities in the world.
 */
public class RenderProcessor extends EntityProcessorBase {
    private SpriteBatch batch;

    private TextureAtlas textureAtlas;
    private TextureAtlas.AtlasRegion image;

    public RenderProcessor() {
        super(Appearance.class);
    }

    @Override protected void onInit() {
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas("textures/textures.atlas");

        image = textureAtlas.findRegion("turbulent01");

    }

    @Override protected void preProcess(Time time) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
    }

    @Override protected void processEntity(Time time, Entity entity) {
        batch.draw(image, 0, 0/*,vec3.x, vec3.y*/);
    }

    @Override protected void postProcess(Time time) {
        batch.end();
    }

    @Override protected void onShutdown() {

    }
}
