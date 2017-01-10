
/**
  * Created by А.Скрипкин on 09.01.2017.
  */

import java.sql._

object SqliteScala  extends App {

  implicit class TypeResultSet(rs: ResultSet) extends Iterator[ClientType] {

    def getString(name: String, encoding: String) = new String(rs.getBytes(name),encoding)

    override def hasNext: Boolean = rs.next
    override def next: ClientType = ClientType(rs.getInt("type"), rs.getString("name", "Windows-1251"))
  }

  case class ClientType(tCode: Int, name: String) {
    override def toString: String = "--%3d\t%-20s".format(tCode, name)
  }

  Class.forName("org.sqlite.JDBC")

  lazy val conn: Connection = DriverManager.getConnection("jdbc:sqlite:data/proc_new.db")
  lazy val stmt: Statement = conn.createStatement
  lazy val rs:   ResultSet = stmt.executeQuery("SELECT type, name FROM type")

  rs foreach println
}
