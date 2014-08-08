package org.spacegame.components.appearance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Appearance based on a shared static model class.
 */
public abstract class ModelAppearance extends Appearance {

    private static Model sharedModel;

    @Override protected final ModelInstance createAppearance() {
        return new ModelInstance(getModel());
    }

    private Model getModel() {
        if (sharedModel == null) {
            sharedModel = createBaseModel();
        }

        return sharedModel;
    }

    protected abstract Model createBaseModel();


}
