package c4


import org.jcsp.groovy.*
import org.jcsp.lang.*

class ResetSuccessor implements CSProcess {

  def ChannelOutput outChannel
  def ChannelInput  inChannel
  def ChannelInput  resetChannel

  void run () {
    def guards = [ resetChannel, inChannel  ]
    def alt = new ALT ( guards )
    while (true) {
      def index = alt.priSelect();
      switch( index ) {
      case 0:
        inChannel.read();
        outChannel.write( resetChannel.read() );
        break;
      case 1:
        outChannel.write( inChannel.read() + 1 );
      }
    }
  }
}
