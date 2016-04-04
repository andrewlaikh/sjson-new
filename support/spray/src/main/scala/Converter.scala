package sjsonnew
package support.spray

import spray.json.{ deserializationError => _, _ }

object Converter extends SupportConverter[JsValue] {
  val facade: Facade[JsValue] =
    new SimpleFacade[JsValue] {
      def jnull() = JsNull
      def jfalse() = JsFalse
      def jtrue() = JsTrue
      def jnum(d: Double) = JsNumber(d)
      def jnum(s: String) = JsNumber(s)
      def jnum(d: BigDecimal) = JsNumber(d)
      def jint(i: Int) = JsNumber(i)
      def jlong(l: Long) = JsNumber(l)
      def jstring(s: String) = JsString(s)
      def jarray(vs: List[JsValue]) = JsArray(vs: _*)
      def jobject(vs: Map[String, JsValue]) = JsObject(vs)
      def extractInt(value: JsValue): Int =
        value match {
          case JsNumber(x) => x.intValue
          case x => deserializationError("Expected Int as JsNumber, but got " + x)
        }
      def extractLong(value: JsValue): Long =
        value match {
          case JsNumber(x) => x.longValue
          case x => deserializationError("Expected Long as JsNumber, but got " + x)
        }
      def extractFloat(value: JsValue): Float =
        value match {
          case JsNumber(x) => x.floatValue
          case JsNull      => Float.NaN
          case x => deserializationError("Expected Float as JsNumber, but got " + x)
        }
      def extractDouble(value: JsValue): Double =
        value match {
          case JsNumber(x) => x.doubleValue
          case JsNull      => Double.NaN
          case x => deserializationError("Expected Double as JsNumber, but got " + x)
        }
      def extractBigDecimal(value: JsValue): BigDecimal =
        value match {
          case JsNumber(x) => x
          case x => deserializationError("Expected BigDecimal as JsNumber, but got " + x)
        }
      def extractBoolean(value: JsValue): Boolean =
        value match {
          case JsTrue => true
          case JsFalse => false
          case x => deserializationError("Expected JsBoolean, but got " + x)
        }
      def extractString(value: JsValue): String =
        value match {
          case JsString(x) => x
          case x => deserializationError("Expected String as JsString, but got " + x)
        }
      def extractArray(value: JsValue): Vector[JsValue] =
        value match {
          case JsArray(elements) => elements
          case x => deserializationError("Expected List as JsArray, but got " + x)
        }
      def extractObject(value: JsValue): Vector[(String, JsValue)] =
        value match {
          case x: JsObject => x.fields.toVector
          case x => deserializationError("Expected Map as JsObject, but got " + x)
        }
    }
}