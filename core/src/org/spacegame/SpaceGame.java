package org.spacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import org.flowutils.LogUtils;
import org.slf4j.Logger;

public class SpaceGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Logger log = LogUtils.getLogger();

    private Vector3 vec3 = new Vector3();
    private float scale = 300;
    private TextureRegion image;

    @Override
	public void create () {
		batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("textures/textures.atlas");

        image = textureAtlas.findRegion("turbulent01");


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

                if (axisIndex == 0) vec3.x = scale * value;
                if (axisIndex == 1) vec3.y = scale * value;

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

        Controllers.getControllers();
    }



	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(image, vec3.x, vec3.y);
		batch.end();
	}

    @Override public void dispose() {
    }
}
