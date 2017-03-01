package c9;

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel


import org.jcsp.groovy.*
import org.jcsp.lang.*

import c09.EventOWBuffer
import c09.EventPrompter
import c09.EventReceiver

class EventHandler implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel

  void run () {
    def get = Channel.one2one()
    def transfer = Channel.one2one()
    def toBuffer = Channel.one2one()
    def toVerifier = Channel.one2one()

    def handlerList = [ new EventReceiver ( eventIn: inChannel,
                                            eventOut: toBuffer.out()),
                        new EventOWBuffer ( inChannel: toBuffer.in(),
                                            getChannel: get.in(),
                                            outChannel: transfer.out() ),
                        new EventPrompter ( inChannel: transfer.in(),
                                            getChannel: get.out(),
                                            outChannel: toVerifier.out() ),
                        new EventMissedVerifier ( inChannel: toVerifier.in(),
                                                  outChannel: outChannel,
                                                  initialDataValue: 100 )
                      ]
    new PAR ( handlerList ).run()
  }
}
