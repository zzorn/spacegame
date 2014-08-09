package org.spacegame.components;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import org.entityflow.component.Component;
import org.entityflow.component.ComponentBase;
import org.entityflow.entity.Entity;

/**
 * Something with a physical body, simulated by the physics engine.
 */
// TODO: Should this include rigid body dynamics + mass as well, or should this just be for collisions (could be renamed then)
public class Physical extends ComponentBase {

    public btCollisionShape collisionShape;

    public btCollisionObject collisionObject;


    public Physical() {
    }

    public Physical(btCollisionShape collisionShape) {
        this.collisionShape = collisionShape;
    }


    @Override protected void handleRemoved(Entity entity) {

        // Dispose native classes

        if (collisionShape != null) {
            collisionShape.dispose();
            collisionShape = null;
        }

        if (collisionObject != null) {
            collisionObject.dispose();
            collisionObject = null;
        }
    }
}
