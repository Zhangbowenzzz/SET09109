package c011

import org.jcsp.lang.*

class ScaledToLabel implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput normalOutChannel
  def ChannelOutput scaledOutChannel

  @Override
  public void run() {
    while (true) {
      def i = inChannel.read()
      normalOutChannel.write(i.original.toString());
      scaledOutChannel.write(i.scaled.toString());
    }
  }

}
