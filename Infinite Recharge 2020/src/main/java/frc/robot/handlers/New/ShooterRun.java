package frc.robot.handlers.New;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.handlers.IRobotEventHandler;

public class ShooterRun implements IRobotEventHandler {
  private final WPI_VictorSPX SMot; 
  private final XboxController drivePrime;

  public ShooterRun(XboxController drivePrime, WPI_VictorSPX shooterMotor) {
    this.drivePrime = drivePrime;
    this.SMot = shooterMotor;
  }

  @Override
  public void teleopPeriodic() {
    double trigger = drivePrime.getTriggerAxis(Hand.kRight);
    SMot.set(trigger);
  }

}