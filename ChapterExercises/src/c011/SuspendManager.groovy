package c011

import org.jcsp.lang.*

class SuspendManager implements CSProcess {

  def ChannelInput pauseIn
  def ChannelOutput pauseOut
  def ChannelInput previousScale

  @Override
  public void run() {
    while (true) {
      def pause = pauseIn.read()
      pauseOut.write(1)
      previousScale.read() // Discard previous scale value
    }
  }

}
