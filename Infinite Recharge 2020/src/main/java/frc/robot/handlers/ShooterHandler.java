package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ShooterHandler implements IRobotEventHandler {

  private final WPI_VictorSPX shooterMotor;
  private final XboxController driver1;

  public ShooterHandler(XboxController driver1, WPI_VictorSPX shooterMotor) {
    this.driver1 = driver1;
    this.shooterMotor = shooterMotor;
  }

  @Override
  public void teleopPeriodic() {
    double trigger = driver1.getTriggerAxis(Hand.kRight);
    shooterMotor.set(trigger);
  }
  
}