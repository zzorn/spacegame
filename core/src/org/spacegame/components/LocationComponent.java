package org.spacegame.components;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.entityflow.component.ComponentBase;

/**
 * Represents position and direction of the entity
 */
public class LocationComponent extends ComponentBase {

    public final Vector3 position = new Vector3();
    public final Quaternion direction = new Quaternion();

    public LocationComponent() {
    }

    public LocationComponent(Vector3 pos) {
        position.set(pos);
    }

    public LocationComponent(float x, float y, float z) {
        position.set(x, y, z);
    }

    public LocationComponent(float x, float y, float z, Quaternion direction) {
        this.position.set(x, y, z);
        this.direction.set(direction);
    }

    public LocationComponent(Vector3 pos, Quaternion direction) {
        position.set(pos);
        this.direction.set(direction);
    }



}
