package frc.robot.handlers.New;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.handlers.IRobotEventHandler;
public class IntakeRun implements IRobotEventHandler {
  private final WPI_TalonSRX iMot;
  private final XboxController drivePrime;
  public IntakeRun( WPI_TalonSRX iMot, XboxController drivePrime) {
    this.iMot= iMot;
    this.drivePrime=drivePrime;
  }
  @Override 
  public void teleopPeriodic(){
    if (drivePrime.getBButton()){
      iMot.set(.75);
    }else{
      iMot.stopMotor();
    }
  }

}