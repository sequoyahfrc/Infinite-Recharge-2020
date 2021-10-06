package frc.robot.handlers.old;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.handlers.IRobotEventHandler;
import frc.robot.util.Constants;
import edu.wpi.first.wpilibj.XboxController;

public class ConveyorHandler implements IRobotEventHandler {

  private final WPI_TalonSRX conveyorMotor;
  private final XboxController driver1;

  public ConveyorHandler(WPI_TalonSRX conveyorMotor, XboxController driver1) {
    this.conveyorMotor = conveyorMotor;
    this.driver1 = driver1;
  }

  @Override
  public void teleopPeriodic() {
    if (driver1.getYButton()) {
      conveyorMotor.set(Constants.conveyorSpeed);
    } else {
      conveyorMotor.stopMotor();
    }
  }
}