package org.spacegame

import appearance.{Appearance, PlanetAppearance}
import entity.Entity
import simplex3d.math.doublem.renamed._
import simplex3d.math.doublem.DoubleMath._

import javax.imageio._

import org.lwjgl.opengl.GL11._

import org.sgine.core.Color

import org.sgine.core.Resource

import org.sgine.render.primitive.Ellipsoid
import org.sgine.render._
import org.sgine.ui.Image

/**
 * Main entry point for SpaceGame client.
 */
object SpaceGame extends StandardDisplay with Debug {

  override val settings = RenderSettings.High

  private var entities: List[Entity] = Nil

  def setup() {
    entities = createEntities()

    // Add each entity appearance to the scene
    val appearances: List[Appearance] = (entities map (_.facet[Appearance]('appearance))).flatten
    appearances foreach (scene += _.shape)
  }

  // TODO: Update method

  def createEntities(): List[Entity] = {
    val planet = new Entity(new PlanetAppearance())
    List(planet)
  }
  
/*
	def main(args: Array[String]): Unit = {
		val r = Renderer.createFrame(1024, 768, "Test Ellipsoid", RenderSettings.High)
		r.verticalSync := false

		val t = TextureUtil(ImageIO.read(getClass.getClassLoader.getResource("turbulent.png")))

		val m = Mat3x4 translate(Vec3(0, 0, -800.0))
		val i = RenderImage(t)
		val ellipsoid = Ellipsoid(500.0, 500.0, 500.0, 32, 32, Color.White, i)
		val fps = FPS(1.0)

		r.renderable := RenderList(MatrixState(m), ellipsoid, fps)

		while(true) {
			Thread.sleep(5)
			m := Mat3x4 rotateX(0.0001) rotateY(0.0001) concatenate(m)
		}
	}

	private def animate(component: Component) = {
		component.rotation.x.animator = new LinearNumericAnimator(2.0)
		component.rotation.y.animator = new LinearNumericAnimator(2.0)
		component.rotation.z.animator = new LinearNumericAnimator(2.0)
		component.location.x.animator = new EasingNumericAnimator(Elastic.easeInOut, 3.0)

		component.rotation.x := Double.MaxValue
		component.rotation.y := Double.MaxValue
		component.rotation.z := Double.MaxValue

		// Move the cube back and forth perpetually on the x-axis
		val me0 = new PauseEffect(random * 2.0 + 0.5)
		val me1 = new PropertyChangeEffect(component.location.x, -400.0)
		val me2 = new PropertyChangeEffect(component.location.x, 400.0)
		val move = new CompositeEffect(me0, me1, me2)
		move.repeat = -1
		move.start()
	}

*/
}