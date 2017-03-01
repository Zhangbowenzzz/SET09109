package c011

import java.awt.event.KeyEvent

import org.jcsp.lang.*

class KeyEventFilter implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def allowedChars = []

  @Override
  public void run() {
    while (true) {
      def event = inChannel.read()
      if (eventIsReleased(event) && event.getKeyCode() in allowedChars) {
        outChannel.write(event)
      }
    }
  }

  private boolean eventIsReleased(KeyEvent event) {
    return event.paramString().contains("KEY_RELEASED")
  }

}
