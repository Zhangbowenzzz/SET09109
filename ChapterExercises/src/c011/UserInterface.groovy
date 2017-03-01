package c011

import java.awt.*

import org.jcsp.awt.*
import org.jcsp.groovy.*
import org.jcsp.groovy.plugAndPlay.GNumbers
import org.jcsp.groovy.plugAndPlay.GObjectToConsoleString
import org.jcsp.lang.*

class UserInterface implements CSProcess {

  def baseContainer
  def originalTitleLabel
  def originalValueLabel
  def scaledTitleLabel
  def scaledValueLabel
  def pauseButton
  def resetTextField

  def originalValueChannel = Channel.one2one()
  def a = Channel.one2one()

  def network = [
      new GNumbers ( outChannel: a.out() ),
      new GObjectToConsoleString(inChannel: a.in(),
        outChannel: originalValueChannel.out())

    ]

  void run() {
    def root = new ActiveClosingFrame ("Scaler")
    def mainFrame = root.getActiveFrame()
    mainFrame.add (getBaseContainer())
    mainFrame.pack()
    mainFrame.setVisible (true)
    def interfaceNetwork = [ root, getOriginalValueLabel() ]
    new PAR (interfaceNetwork + network).run()
  }

  Container getBaseContainer() {
    if (baseContainer == null) {
      baseContainer = new Container()
      baseContainer.setLayout(new GridLayout(3,2))
      baseContainer.add(getOriginalTitleLabel())
      baseContainer.add(getOriginalValueLabel())
      baseContainer.add(getScaledTitleLabel())
      baseContainer.add(getScaledValueLabel())
      baseContainer.add(getPauseButton())
      baseContainer.add(getResetTextBox())
    }
    return baseContainer
  }

  Label getOriginalTitleLabel() {
    if (originalTitleLabel == null) {
      originalTitleLabel = new Label()
      originalTitleLabel.setText("Original: ")
    }
    return originalTitleLabel
  }

  ActiveLabel getOriginalValueLabel() {
    if (originalValueLabel == null) {
      originalValueLabel = new ActiveLabel(originalValueChannel.in(), "0")
    }
    return originalValueLabel
  }

  Label getScaledTitleLabel() {
    if (scaledTitleLabel == null) {
      scaledTitleLabel = new Label()
      scaledTitleLabel.setText("Scaled: ")
    }
    return scaledTitleLabel
  }

  ActiveLabel getScaledValueLabel() {
    if (scaledValueLabel == null) {
      scaledValueLabel = new ActiveLabel()
      scaledValueLabel.setText("0")
    }
    return scaledValueLabel
  }

  ActiveButton getPauseButton() {
    if (pauseButton == null) {
      pauseButton = new ActiveButton("Pause")
    }
    return pauseButton
  }

  ActiveTextField getResetTextBox() {
    if (resetTextField == null) {
      resetTextField = new ActiveTextField()
    }
    return resetTextField
  }

}
