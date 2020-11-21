package zio.web.codec

import zio.json.JsonEncoder
import zio.stream.ZTransducer
import zio.web.schema._
import zio.{ Chunk, ZIO }

object JsonCodec extends Codec {
  override def encoder[A](schema: Schema[A]): ZTransducer[Any, Nothing, A, Byte] =
    schema match {
      case Schema.Primitive(standardType) => primitiveEncoder(standardType)
      case Schema.Record(_)               => ???
      case Schema.Sequence(_)             => ???
      case Schema.Enumeration(_)          => ???
      case Schema.Transform(_, _, _)      => ???
      case Schema.Tuple(_, _)             => ???
      case Schema.Optional(_)             => ???
    }

  private val emptyChunk = ZIO.succeed(Chunk.empty)

  private val unitEncoder: ZTransducer[Any, Nothing, Unit, Nothing] = ZTransducer.fromPush {
    case Some(_) => emptyChunk
    case None    => emptyChunk
  }

  private val stringEncoder: ZTransducer[Any, Nothing, String, Byte] =
    ZTransducer
      .fromFunction[String, Chunk[Byte]] { s =>
        val chars = JsonEncoder[String].encodeJson(s, None)
        charSequenceToByteChunk(chars)
      }
      .mapChunks(_.flatten)

  private def primitiveEncoder[A](standardType: StandardType[A]): ZTransducer[Any, Nothing, A, Byte] =
    standardType match {
      case StandardType.UnitType   => unitEncoder
      case StandardType.StringType => stringEncoder
      case StandardType.BoolType   => ???
      case StandardType.ShortType  => ???
      case StandardType.IntType    => ???
      case StandardType.LongType   => ???
      case StandardType.FloatType  => ???
      case StandardType.DoubleType => ???
      case StandardType.ByteType   => ???
      case StandardType.CharType   => ???
    }

  private def charSequenceToByteChunk(chars: CharSequence): Chunk[Byte] = ???

  override def decoder[A](codec: Schema[A]): ZTransducer[Any, String, Byte, A] = ???
}
