package c07

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.groovy.*
import org.jcsp.lang.*


class Server implements CSProcess{

  def ChannelInput clientRequest
  def ChannelOutput clientSend
  def ChannelOutput thisServerRequest
  def ChannelInput thisServerReceive
  def ChannelInput otherServerRequest
  def ChannelOutput otherServerSend
  def serverNumber
  def dataMap = [ : ]

  void run () {
    def CLIENT = 0
    def OTHER_REQUEST = 1
    def THIS_RECEIVE = 2
    def serverAlt = new ALT ([clientRequest,
                              otherServerRequest,
                              thisServerReceive])
    while (true) {
      def index = serverAlt.select()

      switch (index) {
        case CLIENT :
          def key = clientRequest.read()
          println "\tServer $serverNumber received client request for ${key}"
          if ( dataMap.containsKey(key) ) {
            clientSend.write(dataMap[key])
          } else {
            println "\tServer $serverNumber sending server request for ${key}"
            thisServerRequest.write(key)
          }
          break
        case OTHER_REQUEST :
          def key = otherServerRequest.read()
          println "\tServer $serverNumber recieved server request for ${key}"
          if ( dataMap.containsKey(key) ) {
            otherServerSend.write(dataMap[key])
          } else {
            otherServerSend.write(-1)
          }
          break
        case THIS_RECEIVE :
          def key = thisServerReceive.read()
          println "\tServer $serverNumber received ${key} value from server"
          clientSend.write(key)
          break
      } // end switch
    } //end while
  } //end run
}
