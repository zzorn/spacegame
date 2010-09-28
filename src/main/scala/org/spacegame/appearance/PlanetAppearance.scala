package org.spacegame.appearance

import org.spacegame.shape.Sphere
import org.sgine.ui.Box
import org.sgine.core.{Resource, Face}

/**
 * 
 */
class PlanetAppearance extends Appearance {
  def update(delta_s: Double, currentTime_ms: Long) = null
  def createShape() = {
		val planet = new Box()
		planet.cull := Face.None
		planet.manualSize := true
		planet.size.width := 512.0
		planet.size.height := 256.0
		planet.size.depth := 512.0
    Resource.addPath("media")
		planet.source := Resource("turbulent.png")
		planet.alpha := 0.5
    planet
  }
}
