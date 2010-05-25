package org.spacegame.client

import com.ardor3d.bounding.BoundingBox
import com.ardor3d.renderer.Renderer
import com.ardor3d.renderer.state.MaterialState.ColorMaterial
import com.ardor3d.renderer.state.{MaterialState, TextureState}
import com.ardor3d.util.{ReadOnlyTimer, TextureManager, Timer}
import com.ardor3d.scenegraph.controller.SpatialController
import com.ardor3d.example.ExampleBase
import com.ardor3d.extension.ui._
import com.ardor3d.math.{Vector3, Matrix3, MathUtils}
import com.ardor3d.extension.ui.UITabbedPane.TabPlacement
import com.ardor3d.scenegraph.shape.Box
import com.ardor3d.image.Texture
import com.ardor3d.image.Image.Format
import com.ardor3d.input.logical.LogicalLayer
import com.ardor3d.framework.FrameHandler

import com.google.inject.Inject

/**
 * Main UI entrypoint for the client.
 */
class MainUi @Inject() (layer: LogicalLayer, frameWork: FrameHandler, timer: Timer) extends ExampleBase(layer, frameWork) {

  @Override
  protected def initExample: Unit = {
    _canvas.setTitle("Skycastle 3D Test")

    val box: Box = new Box("Box", new Vector3(0, 0, 0), 5, 5, 5)
    box.setModelBound(new BoundingBox)
    box.setTranslation(new Vector3(0, 0, -15))
    box.addController(createRotationController())

    val ms: MaterialState = new MaterialState
    ms.setColorMaterial(ColorMaterial.Diffuse)
    box.setRenderState(ms)

    _root.attachChild(box)

  }


  private def createRotationController(): SpatialController[Box] = {
    new SpatialController[Box] {
      private final val axis: Vector3 = new Vector3(1, 1, 0.5f).normalizeLocal
      private final val rotate: Matrix3 = new Matrix3
      private var angle: Double = 0

      def update(time: Double, caller: Box): Unit = {
        angle += time * 50
        angle %= 360
        rotate.fromAngleNormalAxis(angle * MathUtils.DEG_TO_RAD, axis)
        caller.setRotation(rotate)
      }
    }
  }

}