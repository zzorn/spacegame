package org.spacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import org.entityflow.entity.Entity;
import org.entityflow.world.ConcurrentWorld;
import org.flowutils.LogUtils;
import org.flowutils.time.RealTime;
import org.slf4j.Logger;
import org.spacegame.components.CameraComponent;
import org.spacegame.components.LocationComponent;
import org.spacegame.components.appearance.SphereAppearance;
import org.spacegame.processors.CameraProcessor;
import org.spacegame.processors.RenderingProcessor;
import org.spacegame.processors.TrackingProcessor;

import java.util.Random;

public class SpaceGame extends ApplicationAdapter {
    public static final String NAME = "Space Pop";
    public static final int SIMULATION_STEP_MILLISECONDS = 5;

    private Logger log = LogUtils.getLogger();
    private ConcurrentWorld world;
    private RealTime time;
    private RenderingProcessor renderingProcessor;
    private Vector3 tempPos;
    private final InputMultiplexer inputHandler = new InputMultiplexer();


    @Override public void create() {
        // Create world
        time = new RealTime();
        world = new ConcurrentWorld(time, SIMULATION_STEP_MILLISECONDS);

        /*
        sea = new Sea();

        entityFactory = new EntityFactory(world, sea);
        */

        // Add processors
        world.addProcessor(new TrackingProcessor());
        renderingProcessor = new RenderingProcessor(null);
        world.addProcessor(new CameraProcessor(renderingProcessor, inputHandler));
        world.addProcessor(renderingProcessor);


        // Setup input handler
        Gdx.input.setInputProcessor(inputHandler);

        // Initialize processors
        world.init();

        tempPos = new Vector3();

        // Create player
        //final Entity player = entityFactory.createPlayerSubmarine(tempPos.set(0, 0, 0), 0.3f, 0.7f, inputHandler);
        Entity player = world.createEntity(new LocationComponent(),
                                           new CameraComponent());
        player.get(CameraComponent.class).entityToHideWhenCameraActive = player;

        // Create some planets
        Random random = new Random();
        float spread = 1000;
        for (int i = 0; i < 1000; i++) {

            tempPos.set((float) random.nextGaussian() * spread,
                        (float) random.nextGaussian() * spread ,
                        (float) random.nextGaussian() * spread);

            float size = random.nextFloat() * 100 + 20;

            final Color color = new Color(random.nextFloat(),
                                          random.nextFloat(),
                                          random.nextFloat(), 1);

            world.createEntity(new SphereAppearance(color, size),
                               new LocationComponent(tempPos));
        }

        setupControllerTest();

    }

    @Override public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override public void render() {
        // Tick
        time.nextStep();

        // Update world
        world.process();
    }




    private void setupControllerTest() {
        Controllers.addListener(new ControllerAdapter() {
            @Override public boolean buttonDown(Controller controller, int buttonIndex) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.buttonDown");
                System.out.println("buttonIndex = " + buttonIndex);

                return true;
            }

            @Override public boolean buttonUp(Controller controller, int buttonIndex) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.buttonUp");
                System.out.println("buttonIndex = " + buttonIndex);

                return true;
            }

            @Override public boolean axisMoved(Controller controller, int axisIndex, float value) {
                System.out.println(controller.getName());
                System.out.println("controller hash = " + controller.hashCode());
                System.out.println("SpaceGame.axisMoved");
                System.out.println("axisIndex = " + axisIndex);
                System.out.println("value = " + value);

                /*
                if (axisIndex == 0) vec3.x = scale * value;
                if (axisIndex == 1) vec3.y = scale * value;
                */

                return true;
            }

            @Override public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.povMoved");
                System.out.println("povIndex = " + povIndex);
                System.out.println("value = " + value);

                return true;
            }

            @Override public boolean xSliderMoved(Controller controller, int sliderIndex, boolean value) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.xSliderMoved");
                System.out.println("sliderIndex = " + sliderIndex);
                System.out.println("value = " + value);
                return true;
            }

            @Override public boolean ySliderMoved(Controller controller, int sliderIndex, boolean value) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.ySliderMoved");
                System.out.println("sliderIndex = " + sliderIndex);
                System.out.println("value = " + value);
                return true;
            }

            @Override public boolean accelerometerMoved(Controller controller,
                                                        int accelerometerIndex,
                                                        Vector3 value) {
                System.out.println(controller.getName());
                System.out.println("SpaceGame.accelerometerMoved");
                System.out.println("accelerometerIndex = " + accelerometerIndex);
                System.out.println("value = " + value);
                return true;
            }

            @Override public void connected(Controller controller) {
                System.out.println("SpaceGame.connected");
                System.out.println(controller.getName());
            }

            @Override public void disconnected(Controller controller) {
                System.out.println("SpaceGame.disconnected");
                System.out.println(controller.getName());
            }
        });

        for (Controller controller : Controllers.getControllers()) {
            log.info(controller.getName());
        }

    }


    @Override public void dispose() {
        world.shutdown();
    }
}
