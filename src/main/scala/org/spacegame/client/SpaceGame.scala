package org.spacegame.client

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
object SpaceGame {
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
}