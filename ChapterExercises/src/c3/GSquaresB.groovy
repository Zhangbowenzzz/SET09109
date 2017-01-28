package c3

import org.jcsp.groovy.*
import org.jcsp.groovy.plugAndPlay.*
import org.jcsp.lang.*

class GSquaresB implements CSProcess {

  def ChannelOutput outChannel

  void run () {

    One2OneChannel N2I = Channel.createOne2One()
    One2OneChannel I2P = Channel.createOne2One()

    def testList =  [ new GNumbers   ( outChannel: N2I.out() ),
                      new GIntegrate ( inChannel: N2I.in(),
                                       outChannel: I2P.out() ),
                      new GSPairsB ( inChannel: I2P.in(),
                                     outChannel: outChannel )
                    ]
    new PAR ( testList ).run()
  }
}
