package c011

import org.jcsp.groovy.ALT
import org.jcsp.lang.*

class TextFieldToIntegerSafe implements CSProcess {

  def ChannelInput inChannel
  def ChannelInput submitChannel
  def ChannelOutput outChannel

  @Override
  public void run() {
    def parsed
    def alt = new ALT ( [ submitChannel, inChannel ] )
    while (true) {
      int index = alt.priSelect();
      switch (index) {
        case 0:
          submitChannel.read()
          outChannel.write(parsed)
          break
        case 1:
          def input = inChannel.read()
          try {
            parsed = Integer.parseInt(input)
          } catch (NumberFormatException e) {
            // Ignore error
          }
          break
      }
    }

  }

}
