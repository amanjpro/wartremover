package org.brianmckenna.wartremover
package test

import org.scalatest.FunSuite

import org.brianmckenna.wartremover.warts.MutableDataStructures

class MutableDataStructuresTest extends FunSuite {
  test("disable scala.collection.mutable._ when referenced") {
    val result = WartTestTraverser(MutableDataStructures) {
      var x = scala.collection.mutable.HashMap("key" -> "value")
    }
    assertResult(List("scala.collection.mutable package is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
  test("ignore immutable collections") {
    val result = WartTestTraverser(MutableDataStructures) {
      var x = Map("key" -> "value")
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
  test("MutableDataStructures wart obeys ignoreWarts") {
    val result = WartTestTraverser(MutableDataStructures) {
      @ignoreWarts("org.brianmckenna.wartremover.warts.MutableDataStructures")
      var x = scala.collection.mutable.HashMap("key" -> "value")
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
