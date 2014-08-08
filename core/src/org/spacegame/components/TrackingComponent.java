package org.spacegame.components;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.entityflow.component.ComponentBase;
import org.entityflow.entity.Entity;

/**
 * Something that tracks and aligns with another entitys position
 */
public class TrackingComponent extends ComponentBase {

    public Entity trackedEntity;

    public Vector3 relativePosition = new Vector3();
    public Quaternion relativeDirection = new Quaternion();

    public TrackingComponent(Entity trackedEntity) {
        this.trackedEntity = trackedEntity;
    }


    public TrackingComponent(Entity trackedEntity, Vector3 relativePosition) {
        this.trackedEntity = trackedEntity;
        this.relativePosition = relativePosition;
    }

    public TrackingComponent(Entity trackedEntity,
                             Vector3 relativePosition,
                             Quaternion relativeDirection) {
        this.trackedEntity = trackedEntity;
        this.relativePosition = relativePosition;
        this.relativeDirection = relativeDirection;
    }
}
