package c2

import org.jcsp.lang.*

class CreateSetsParameterised implements CSProcess{

  def ChannelInput inChannel
  def int groupSize

  void run(){
    def outList = []
    def v = inChannel.read()
    while (v != -1){
      for ( i in 1 .. groupSize ) {
        outList << v;
        v = inChannel.read();
      }
      println " Object is ${outList}"
      outList = [];
    }
    println "Finished"
  }
}
