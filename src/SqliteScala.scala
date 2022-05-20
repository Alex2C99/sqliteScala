
/**
  * Created by А.Скрипкин on 09.01.2017.
  */

import java.sql._
import scala.language.implicitConversions

object SqliteScala  extends App {

    case class ClientType(tCode: Int, name: String) {
        override def toString: String = "--%3d\t%-20s".format(tCode, name)
    }

    extension (rs: ResultSet)
        def getString(name: String, encoding: String) = String(rs.getBytes(name),encoding)

    given Conversion[ResultSet, TypeResultSet] = TypeResultSet(_)

    class TypeResultSet(rs: ResultSet) extends Iterator[ClientType] {
        override def hasNext: Boolean = rs.next
        override def next: ClientType = ClientType(rs.getInt("type"), rs.getString("name", "Windows-1251"))
    }

    Class.forName("org.sqlite.JDBC")

    lazy val conn: Connection = DriverManager.getConnection("jdbc:sqlite:data/proc_new.db")
    lazy val stmt: Statement = conn.createStatement
    lazy val rs:   ResultSet = stmt.executeQuery("SELECT type, name FROM type")

    rs foreach println
}
