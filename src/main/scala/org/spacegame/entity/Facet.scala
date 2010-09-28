package org.spacegame.entity

/**
 * One aspect of some entity.
 */
trait Facet {

  def kind: Symbol

  def update(delta_s: Double, currentTime_ms: Long)
  
}