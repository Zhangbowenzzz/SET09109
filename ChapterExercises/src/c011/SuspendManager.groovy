package c011

import org.jcsp.lang.*

class SuspendManager implements CSProcess {

  def ChannelInput pauseIn
  def ChannelOutput pauseOut
  def ChannelInput previousScale
  def ChannelOutput pauseButtonEnableOut
  def ChannelOutput injectTextViewEnableOut

  @Override
  public void run() {
    while (true) {
      def pause = pauseIn.read()
      pauseOut.write(1)
      pauseButtonEnableOut.write(Boolean.FALSE)
      injectTextViewEnableOut.write(Boolean.TRUE)
      previousScale.read() // Discard previous scale value
    }
  }

}
