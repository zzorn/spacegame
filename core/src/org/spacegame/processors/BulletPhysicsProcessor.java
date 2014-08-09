package org.spacegame.processors;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import org.entityflow.entity.Entity;
import org.entityflow.processors.EntityProcessorBase;
import org.flowutils.Check;
import org.flowutils.time.Time;
import org.spacegame.components.Location;
import org.spacegame.components.Physical;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.flowutils.Check.notNull;

/**
 *
 */
public class BulletPhysicsProcessor extends EntityProcessorBase {

    private btCollisionConfiguration collisionConfig;
    private btDispatcher dispatcher;
    private ContactListener contactListener;
    private btCollisionWorld collisionWorld;

    private Map<Integer, Entity> entitiesLookup = new HashMap<Integer, Entity>();
    private int nextEntityRef = 1;
    private btBroadphaseInterface broadphase;


    public BulletPhysicsProcessor() {
        super(Physical.class, Location.class);
    }

    @Override protected void onInit() {
        // Initialize physics library
        Bullet.init();
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);

        contactListener = new ContactListener() {
            @Override public boolean onContactAdded(int userValue0,
                                                    int partId0,
                                                    int index0,
                                                    boolean match0,
                                                    int userValue1,
                                                    int partId1,
                                                    int index1,
                                                    boolean match1) {

                final Entity entity1 = entitiesLookup.get(userValue0);
                final Entity entity2 = entitiesLookup.get(userValue1);

                System.out.println("Collision between entities " + entity1 + " and " + entity2);

                return true;
            }
        };

    }

    @Override protected void handleAddedEntity(Entity entity) {
        final Physical physical = entity.get(Physical.class);
        final Location location = entity.get(Location.class);
        notNull(location, "location");
        notNull(physical, "physical");
        Check.equals(physical.collisionObject, "physical.collisionObject", null, "<not initialized yet>"); // Assert that the collision object is not yet created, and that it has a shape

        // Create collision object for this entity
        final btCollisionObject collisionObject = new btCollisionObject();
        physical.collisionObject = collisionObject;

        // Set the right shape for the collision object
        notNull(physical.collisionShape, "physical.collisionShape");
        collisionObject.setCollisionShape(physical.collisionShape);

        // Put the collision object in the correct position
        final Matrix4 worldTrans = new Matrix4();
        location.getWorldTransform(worldTrans);
        collisionObject.setWorldTransform(worldTrans);

        // Store a quick lookup value used to find the entity for a given collision object
        int ref = nextEntityRef++;
        collisionObject.setUserValue(ref);
        entitiesLookup.put(ref, entity);

        // Listen to collisions
        collisionObject.setCollisionFlags(collisionObject.getCollisionFlags() |
                                          btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);

        // Add collision object to physics simulation
        collisionWorld.addCollisionObject(collisionObject);

    }

    @Override protected void handleRemovedEntity(Entity entity) {
        final Physical physical = entity.get(Physical.class);
        notNull(physical, "physical");

        // Remove from physics simulation
        collisionWorld.removeCollisionObject(physical.collisionObject);

        // Dispose the collision object.
        physical.collisionObject.dispose();
        final int ref = physical.collisionObject.getUserValue();
        physical.collisionObject = null;
        entitiesLookup.remove(ref);
    }

    @Override protected void preProcess(Time time) {

    }

    @Override protected void processEntity(Time time, Entity entity) {


    }

    @Override protected void postProcess(Time time) {

        // Detect collisions
        collisionWorld.performDiscreteCollisionDetection();
    }

    @Override protected void onShutdown() {
        contactListener.dispose();
        collisionWorld.dispose();
        broadphase.dispose();
        dispatcher.dispose();
        collisionConfig.dispose();
    }
}
