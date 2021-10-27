package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonSRX motor;

  public ShooterSubsystem() {
    motor = new WPI_TalonSRX(6);
  }

  public void setSpeed(double value) {
    motor.set(value);
  }

  public void stop() {
    motor.stopMotor();
  }

}