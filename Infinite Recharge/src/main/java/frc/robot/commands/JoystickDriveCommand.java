package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControllerSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class JoystickDriveCommand extends CommandBase {

  private final XboxController driver2;
	private final DriveSubsystem drive;

  public JoystickDriveCommand(DriveSubsystem drive, ControllerSubsystem controllers) {
    this.drive = drive;
    driver2 = controllers.getDriver2();
    addRequirements(drive);
  }

  @Override
  public void execute() {
    double l = driver2.getY(Hand.kLeft);
    double r = driver2.getY(Hand.kRight);
    l = l < 0 ? -(l * l) : l * l;
    r = r < 0 ? -(r * r) : r * r;
    l *= 0.9;
    r *= 0.9;
    drive.tankDrive(l, r);    
  }
}
