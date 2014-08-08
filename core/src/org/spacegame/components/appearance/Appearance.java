package org.spacegame.components.appearance;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import org.entityflow.component.ComponentBase;
import org.entityflow.entity.Entity;
import org.flowutils.time.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A 3D appearance for an entity.
 */
public abstract class Appearance extends ComponentBase {

    private final List<ModelInstance> appearances = new ArrayList<ModelInstance>(3);
    private float scale = 1;
    private final Vector3 offset = new Vector3();

    private boolean visible = true;
    private boolean appearancesCreated = false;


    protected Appearance() {
        super(Appearance.class);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3 getOffset() {
        return offset;
    }

    public void setOffset(Vector3 offset) {
        this.offset.set(offset);
    }

    public void setOffset(float x, float y, float z) {
        this.offset.set(x, y, z);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final List<ModelInstance> getModelInstances() {
        if (!appearancesCreated) {
            // Single
            final ModelInstance singleAppearance = createAppearance();
            if (singleAppearance != null) appearances.add(singleAppearance);

            // Multiple
            appearances.addAll(createAppearances());

            // Configure them
            for (ModelInstance appearance : appearances) {
                configureInstance(appearance);
            }

            appearancesCreated = true;
        }

        return appearances;
    }

    public void update(Time time) {
    }

    protected ModelInstance createAppearance() {return null; }

    protected List<ModelInstance> createAppearances() {return Collections.EMPTY_LIST; }

    protected void configureInstance(ModelInstance appearance) {
    }

    @Override protected void handleRemoved(Entity entity) {
    }
}
