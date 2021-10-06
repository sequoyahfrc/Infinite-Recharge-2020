package frc.robot.handlers.old;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.handlers.IRobotEventHandler;

public class CompressorHandler implements IRobotEventHandler {

  private final Compressor compressor;

  public CompressorHandler(Compressor compressor) {
    this.compressor = compressor;
  }

  @Override
  public void teleopInit() {
    compressor.start();
  }

  @Override
  public void disabledInit() {
    compressor.stop();
  }
  
}