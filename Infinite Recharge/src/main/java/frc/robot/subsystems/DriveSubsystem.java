package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private final WPI_VictorSPX masterLeft, slaveLeft, masterRight, slaveRight;
  private final SpeedControllerGroup groupLeft, groupRight;
  private final DifferentialDrive robot;

  public DriveSubsystem() {
    masterLeft = new WPI_VictorSPX(0);
    slaveLeft = new WPI_VictorSPX(1);
    masterRight = new WPI_VictorSPX(2);
    slaveRight = new WPI_VictorSPX(3);

    groupLeft = new SpeedControllerGroup(masterLeft, slaveLeft);
    groupRight = new SpeedControllerGroup(masterRight, slaveRight);

    robot = new DifferentialDrive(groupLeft, groupRight);
  }

  public void tankDrive(double left, double right) {
    robot.tankDrive(left, right);
  }

  public void arcadeDrive(double speed, double angle) {
    robot.arcadeDrive(speed, angle);
  }

  public void stop() {
    robot.tankDrive(0, 0);
  }

}