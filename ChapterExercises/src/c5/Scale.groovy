package c5

import org.jcsp.groovy.*
import org.jcsp.lang.*

import c05.ScaledData

class Scale implements CSProcess {
  def int scaling
  def int multiplier
  def ChannelOutput outChannel
  def ChannelOutput factor
  def ChannelInput inChannel
  def ChannelInput suspend
  def ChannelInput injector

  void run () {
    def SECOND = 1000
    def DOUBLE_INTERVAL = 5 * SECOND
    def SUSPEND  = 0
    def INJECT   = 1
    def TIMER    = 2
    def INPUT    = 3

    def timer = new CSTimer()
    def scaleAlt = new ALT ( [ suspend, injector, timer, inChannel ] )

    def preCon = new boolean [4]
    preCon[SUSPEND] = true
    preCon[INJECT] = false
    preCon[TIMER] = true
    preCon[INPUT] = true
    def suspended = false

    def timeout = timer.read() + DOUBLE_INTERVAL
    timer.setAlarm ( timeout )

    while (true) {
      switch ( scaleAlt.priSelect(preCon) ) {
        case SUSPEND :
          suspend.read()
          factor.write(scaling)
          suspended = true
          preCon[TIMER] = false
          preCon[SUSPEND] = false
          preCon[INJECT] = true
          println "Suspended"
          break
        case INJECT: // Also resume
          scaling = injector.read()
          println "Injected scaling is $scaling"
          suspended = false
          preCon[TIMER] = true
          preCon[SUSPEND] = true
          preCon[INJECT] = false
          timeout = timer.read() + DOUBLE_INTERVAL
          timer.setAlarm ( timeout )
          break
        case TIMER:
          timeout = timer.read() + DOUBLE_INTERVAL
          timer.setAlarm ( timeout )
          scaling = scaling * multiplier
          println "Normal Timer: new scaling is ${scaling}"
          break
        case INPUT:
          def inValue = inChannel.read()
          def result = new ScaledData()
          result.original = inValue
          result.scaled = suspended ? inValue : inValue * scaling
          outChannel.write ( result )
          break
      } //end-switch
    } //end-while
  } //end-run
}