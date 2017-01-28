package c3

import org.jcsp.lang.*

class Negator implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel

  void run () {
    while (true) {
      def i = inChannel.read()
      outChannel.write(-i)
    }
  }
}
