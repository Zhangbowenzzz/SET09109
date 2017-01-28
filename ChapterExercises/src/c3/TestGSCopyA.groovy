package c3

import org.jcsp.groovy.*
import org.jcsp.groovy.plugAndPlay.GPrint
import org.jcsp.lang.*

One2OneChannel S2P = Channel.createOne2One()

def testList = [ new GSquaresA ( outChannel: S2P.out() ),
                 new GPrint   ( inChannel: S2P.in(),
                                heading : "Squares" )
               ]

new PAR ( testList ).run()