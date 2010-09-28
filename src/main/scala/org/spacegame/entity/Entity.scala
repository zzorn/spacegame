package org.spacegame.entity

/**
 * Represents some type of entity that consists of different components (facets).
 */
class Entity {
  private var facets_ : Map[Symbol, Facet] = Map()

  def this(initialFacets: Facet*) {
    this()
    initialFacets foreach (add(_))
  }

  // TODO: Remove need for name parameter
  def facet[T <: Facet](name: Symbol): Option[T] = facets.get(name).asInstanceOf[Option[T]]

  def facets: Map[Symbol, Facet] = facets_
  def add(facet: Facet) = facets_ += (facet.kind -> facet)

  def update(delta_s: Double, currentTime_ms: Long) = facets.values foreach (_.update(delta_s, currentTime_ms))
}
