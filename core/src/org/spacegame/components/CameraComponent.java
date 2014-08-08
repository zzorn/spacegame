package org.spacegame.components;

import org.entityflow.component.ComponentBase;
import org.entityflow.entity.Entity;

/**
 *
 */
public class CameraComponent extends ComponentBase {

    public Entity entityToHideWhenCameraActive = null;
    public float fieldOfView_degrees = 67;
    public boolean keepUpright = true;
    public boolean showUi = true;
    public float priority = 1;
    /**
     * True if this camera is currently being used to render the players view.
     */
    public boolean activeCamera = false;

    public CameraComponent() {
    }

    public CameraComponent(Entity entityToHideWhenCameraActive) {
        this.entityToHideWhenCameraActive = entityToHideWhenCameraActive;
    }

    public CameraComponent(Entity entityToHideWhenCameraActive, float fieldOfView_degrees) {
        this.entityToHideWhenCameraActive = entityToHideWhenCameraActive;
        this.fieldOfView_degrees = fieldOfView_degrees;
    }

    public CameraComponent(Entity entityToHideWhenCameraActive, float fieldOfView_degrees, boolean keepUpright, boolean showUi) {
        this.entityToHideWhenCameraActive = entityToHideWhenCameraActive;
        this.fieldOfView_degrees = fieldOfView_degrees;
        this.keepUpright = keepUpright;
        this.showUi = showUi;
    }

    public CameraComponent(Entity entityToHideWhenCameraActive,
                           float fieldOfView_degrees,
                           boolean keepUpright,
                           boolean showUi, float priority) {
        this.entityToHideWhenCameraActive = entityToHideWhenCameraActive;
        this.fieldOfView_degrees = fieldOfView_degrees;
        this.keepUpright = keepUpright;
        this.showUi = showUi;
        this.priority = priority;
    }
}
