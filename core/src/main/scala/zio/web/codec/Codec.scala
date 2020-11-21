package zio.web.codec

import zio.blocking.Blocking
import zio.web.schema._
import zio.stream.ZTransducer

trait Codec {
  def encoder[A](schema: Schema[A]): ZTransducer[Blocking, Nothing, A, Byte]
  def decoder[A](schema: Schema[A]): ZTransducer[Blocking, String, Byte, A]
}
