package edu.umontreal.kotlingrad.numerical

import edu.umontreal.kotlingrad.algebra.Field

// TODO: Try to make this a subtype of Function.Const
abstract class RealNumber<X: RealNumber<X, Y>, Y>(val value: Y):
  Number(), Field<X>, Comparable<Y> where Y: Number, Y: Comparable<Y> {
  override fun toByte() = value.toByte()

  override fun toChar() = value.toChar()

  override fun toDouble() = value.toDouble()

  override fun toFloat() = value.toFloat()

  override fun toInt() = value.toInt()

  override fun toLong() = value.toLong()

  override fun toShort() = value.toShort()

  override fun compareTo(other: Y) = value.compareTo(value)

  override fun equals(other: Any?) = if (other is RealNumber<*, *>) value == other.value else super.equals(other)
}