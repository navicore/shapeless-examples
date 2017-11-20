import shapeless._

case class Employee(name: String, number: Int, manager: Boolean)
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val e = Employee("Ed", 1, manager = false)

val genericEmployee = Generic[Employee].to(Employee("Dave", 123, manager = false))
val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, inCone = false))

def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] =
  List(gen(0).toString, gen(1).toString, gen(2).toString)

genericCsv(genericEmployee)
genericCsv(genericIceCream)

/////////////////

sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

val rect: Shape = Rectangle(3.0, 4.0)
val circ: Shape = Circle(1.0)

def area(shape: Shape): Double =
  shape match {
    case Rectangle(w, h) => w * h
    case Circle(r)       => math.Pi * r * r
  }

area(rect)
area(circ)

/////////////////

type Rectangle2 = (Double, Double)
type Circle2 = Double
type Shape2 = Either[Rectangle2, Circle2]

val rect2: Shape2 = Left((2.1,0.2))
val circ2: Shape2 = Right(0.9)

def area2(shape: Shape2): Double =
  shape match {
    case Left((w, h)) => w * h
    case Right(r)     => math.Pi * r * r
  }

area2(rect2)
area2(circ2)

/////////////////

val product: String :: Int :: Boolean :: HNil =
  "Sunday" :: 1 :: false :: HNil

product.head
product.tail.head
product.tail.tail.head

