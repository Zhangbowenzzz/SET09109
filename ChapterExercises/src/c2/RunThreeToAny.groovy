package c2

import org.jcsp.groovy.*
import org.jcsp.lang.*

One2OneChannel connect1 = Channel.createOne2One()
One2OneChannel connect2 = Channel.createOne2One()

def processList = [ new GenerateSetsOfThree ( outChannel: connect1.out() ),
                    new ListToStream ( inChannel: connect1.in(),
                      outChannel: connect2.out() ),
                    new CreateSetsParameterised ( inChannel: connect2.in(),
                      groupSize: 4 )
                  ]
new PAR (processList).run()