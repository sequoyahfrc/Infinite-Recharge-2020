package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final WPI_TalonSRX intake, towerL, towerR;

  public IntakeSubsystem() {
    intake = new WPI_TalonSRX(4);
    intake.setInverted(true);
    towerR = new WPI_TalonSRX(7);
    towerR.setInverted(true);
    towerL = new WPI_TalonSRX(5);
  }

  public void setSpeed(double value) {
    intake.set(value);
    towerL.set(value * 0.75);
    towerR.set(value * 0.75);
  }

  public void stop() {
    intake.stopMotor();
    towerL.stopMotor();
    towerR.stopMotor();
  }
}