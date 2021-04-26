package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

public class IntakeHandler implements IRobotEventHandler {

	private final WPI_TalonSRX intakeMotor;
  private final XboxController driver1;

  public IntakeHandler(WPI_TalonSRX intakeMotor, XboxController driver1) {
    this.intakeMotor = intakeMotor;
    this.driver1 = driver1;
	}

  @Override
  public void teleopPeriodic() {
    if (driver1.getBButton()) {
      intakeMotor.set(0.75);
    } else {
      intakeMotor.stopMotor();
    }
  }

}