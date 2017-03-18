package c08

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

      // Assumes vM = 10 * M
      // where M is a key and vM is the associated value
      if (v != selectList[i] * 10) {
        throw new RuntimeException("Client $clientNumber recieved value out of order: $v");
      }
    }

    println "Client $clientNumber has finished"
  }
}
