package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class IntakeSubsystem extends SubsystemBase {

  private final WPI_TalonSRX intake, towerL, towerR;
  private final DigitalInput stopIntakeSwitch;

  public IntakeSubsystem() {
    intake = new WPI_TalonSRX(4);
    intake.setInverted(true);
    towerR = new WPI_TalonSRX(7);
    towerR.setInverted(true);
    towerL = new WPI_TalonSRX(5);
    stopIntakeSwitch = new DigitalInput(0); //TODO: change ID
  }

  public void setSpeed(double value) {
    intake.set(value);
  }

  public void setTower(double value) {
    towerL.set(value);
    towerR.set(value);
  }

  public void stopIntake() {
    intake.stopMotor();
  }

  public void stopTower() {
    towerL.stopMotor();
    towerR.stopMotor();
  }

  public boolean getStopIntakeSwitchValue() {
    return stopIntakeSwitch.get();
  }

  public Trigger getStopIntakeSwitch() {
    return new Trigger(this::getStopIntakeSwitchValue);
  }
}