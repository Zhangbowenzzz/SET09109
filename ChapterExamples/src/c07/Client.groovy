package c07

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.groovy.*
import org.jcsp.lang.*


class Client implements CSProcess{

  def ChannelInput receiveChannel
  def ChannelOutput requestChannel
  def clientNumber
  def selectList = [ ]

  void run () {
    def iterations = selectList.size
    println "Client $clientNumber has $iterations values in $selectList"

    for ( i in 0 ..< iterations) {
      def key = selectList[i]
      println "Client $clientNumber requesting ${key}"
      requestChannel.write(key)
      println "Client $clientNumber receiving ${key}"
      def v = receiveChannel.read()
    }

    println "Client $clientNumber has finished"
  }
}
