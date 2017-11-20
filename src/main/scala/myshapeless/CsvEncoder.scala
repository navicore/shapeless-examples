package myshapeless

import shapeless._

trait CsvEncoder[A] {

  def encode(value: A): List[String]
}

object CsvEncoder {

  def pure[A](func: A => List[String]): CsvEncoder[A] =
    (value: A) => func(value)

  implicit val stringEnc: CsvEncoder[String] = pure(str => List(str))
  implicit val intEnc: CsvEncoder[Int] = pure(int => List(int.toString))
  implicit val boolEnc: CsvEncoder[Boolean] = pure(
    b => List(if (b) "yES" else "nO"))
  implicit val hnilEnc: CsvEncoder[HNil] = pure(_ => Nil)
  implicit def hlistEnc[H, T <: HList](
      implicit
      hEnc: CsvEncoder[H],
      tEnc: CsvEncoder[T]
  ): CsvEncoder[H :: T] =
    pure {
      case head :: tail =>
        hEnc.encode(head) ++ tEnc.encode(tail)
    }

  implicit def genericEnc[A, L](
      implicit
      gen: Generic[A] { type Repr = L },
      enc: CsvEncoder[L]
  ): CsvEncoder[A] =
    pure(a => enc.encode(gen.to(a)))
}

object Main extends App {

  def encodeCsv[A](value: A)(implicit enc: CsvEncoder[A]): List[String] =
    enc.encode(value)

  println(encodeCsv("Dave"))
  println(encodeCsv(123))
  println(encodeCsv(true))
  println(encodeCsv("Dave" :: 123 :: true :: HNil))

  case class Employee(name: String, number: Int, manager: Boolean)
  case class IceCream(name: String, numCherries: Int, inCone: Boolean)
  val employee = Employee("Ed", 1, manager = false)
  val iceCream = IceCream("Chocolate", 1, inCone = true)

  println(encodeCsv(employee))
  println(encodeCsv[Employee](employee))
  println(encodeCsv(iceCream))
}
