package org.spacegame.components;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.entityflow.component.ComponentBase;

/**
 * Represents position and direction of the entity
 */
public class Location extends ComponentBase {

    public final Vector3 position = new Vector3();
    public final Quaternion direction = new Quaternion();

    public Location() {
    }

    public Location(Vector3 pos) {
        position.set(pos);
    }

    public Location(float x, float y, float z) {
        position.set(x, y, z);
    }

    public Location(float x, float y, float z, Quaternion direction) {
        this.position.set(x, y, z);
        this.direction.set(direction);
    }

    public Location(Vector3 pos, Quaternion direction) {
        position.set(pos);
        this.direction.set(direction);
    }


    /**
     * Write the world transformation for this location in the specified transformation matrix.
     */
    public Matrix4 getWorldTransform(Matrix4 worldTransform) {
        worldTransform.idt();

        // TODO: Which order to apply the rotation and translation??
        worldTransform.rotate(direction);
        worldTransform.translate(position);

        return worldTransform;
    }
}
