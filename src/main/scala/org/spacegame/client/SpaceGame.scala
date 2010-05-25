package org.spacegame.client

import com.ardor3d.example.ExampleBase

/**
 * Main entry point for SpaceGame client.
 */
object SpaceGame {

  def main(args: Array[String]) {
    print( "SpaceGame client starting." )

    ExampleBase.start(classOf[MainUi])
  }

  
}

