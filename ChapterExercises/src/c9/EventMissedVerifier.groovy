package c9

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class EventMissedVerifier implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def initialDataValue = 0

  @Override
  public void run() {
    def previousDataValue = initialDataValue - 1
    while (true) {
      def e = inChannel.read()
      if ( e.data != e.missed + previousDataValue + 1 ) {
        throw new RuntimeException("$e.data != $e.missed + $previousDataValue + 1")
      }
      previousDataValue = e.data
      outChannel.write(e)
    }
  }

}
