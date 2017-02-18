package c6

import org.jcsp.groovy.*
import org.jcsp.lang.*

import c2.CreateSetsOfEight
import c2.GenerateSetsOfThree
import c2.ListToStream
class TestThreeToEight extends GroovyTestCase {

  void testQueue() {

    ByteArrayOutputStream output = new ByteArrayOutputStream()
    System.setOut(new PrintStream(output))

    One2OneChannel connect1 = Channel.createOne2One()
    One2OneChannel connect2 = Channel.createOne2One()

    def processList = [ new GenerateSetsOfThree ( outChannel: connect1.out() ),
                        new ListToStream ( inChannel: connect1.in(),
                          outChannel: connect2.out() ),
                        new CreateSetsOfEight ( inChannel: connect2.in() )
                      ]
    new PAR (processList).run()

    def expected = " Eight Object is [1, 2, 3, 4, 5, 6, 7, 8]\n" +
                " Eight Object is [9, 10, 11, 12, 13, 14, 15, 16]\n" +
                " Eight Object is [17, 18, 19, 20, 21, 22, 23, 24]\n" +
                "Finished\n"

    assertEquals(expected, output.toString())

    System.setOut(null)

  }
}
