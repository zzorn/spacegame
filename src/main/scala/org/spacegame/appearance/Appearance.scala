package org.spacegame.appearance

import org.spacegame.entity.Facet
import org.sgine.ui.{Box, Component}

/**
 * Represents the visual appearance of an entity.
 */
trait Appearance extends Facet {

  final def kind = 'appearance

  /**
   * The visual appearance of an entity.
   */
  var shape: Component = createShape()

  def createShape(): Component
}