package org.spacegame.shape
import java.util.concurrent.atomic.AtomicBoolean

import org.sgine.bounding.BoundingObject
import org.sgine.bounding.mutable.BoundingBox

import org.sgine.core.Color
import org.sgine.core.Face
import org.sgine.core.ProcessingMode
import org.sgine.core.Resource

import org.sgine.event.Event
import org.sgine.event.EventHandler

import org.sgine.property.AdvancedProperty
import org.sgine.property.NumericProperty
import org.sgine.property.event.PropertyChangeEvent

import org.sgine.render.Renderer
import org.sgine.render.TextureManager
import org.sgine.render.shape._

import scala.math._

import simplex3d.math.doublem.renamed._
import org.sgine.ui.{ShapeComponent, Component}

/**
 * 
 */
class Sphere extends Component with ShapeComponent {
	val source = new AdvancedProperty[Resource](null, this)
	val cull = _cull
	val material = _material

	val sides = new AdvancedProperty[Int](100, this)

	_mode := ShapeMode.TriangleFan
	source.listeners += EventHandler(sourceChanged, ProcessingMode.Blocking)
	private val numericHandler = EventHandler(numericChanged, ProcessingMode.Blocking)
	size.width.listeners  += numericHandler
	size.height.listeners += numericHandler
	size.depth.listeners  += numericHandler

	def this(source: Resource) = {
		this()

		this.source := source
	}

	private def sourceChanged(evt: PropertyChangeEvent[Resource]) = {
		texture = TextureManager(source())
//		// TODO: texturing isn't working correctly...why not?
//		val array = new Array[Vec2](sides() + 2)
//		array(0) = Vec2(0.0, 0.0)
//		for (k <- 1 until array.length) {
//			val x = _vertices()(k).x / size.width() + 1.0 / texture.width
//			val y = _vertices()(k).y / size.height() + 1.5 / texture.height
//			array(k) = Vec2(x, y)
//		}
//		_texcoords := array
	}

	private def numericChanged(evt: PropertyChangeEvent[Double]) = {
		updateVertices()
	}

	private def updateVertices() = {
    // TODO: Change to sphere code
		val w = size.width() / 2.0
		val h = size.height() / 2.0
		val array = new Array[Vec3](sides() + 2)
		array(0) = Vec3(0.0, 0.0, 0.0)

		for (k <- 1 until array.length) {
			val x = sin(2.0 * Pi * k / sides()) * size.width() / 2.0
			val y = cos(2.0 * Pi * k / sides()) * size.height() / 2.0
			val z = 0.0
			array(k) = Vec3(x, y, z)
		}
		_vertices := array //ShapeUtilities.convertTriangleFan(array)
	}
}