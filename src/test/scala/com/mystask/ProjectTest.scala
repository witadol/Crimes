import org.scalatest._

/**
  * Unfortunately I did not have a time to write real tests
  */

class HelloSpec extends FunSuite with DiagrammedAssertions {
  test("Hello should start with H") {
    assert("Hello".startsWith("H"))
  }
}