package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControllerSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class JoystickDriveCommand extends CommandBase {

  private final XboxController driver1;
	private final DriveSubsystem drive;

  public JoystickDriveCommand(DriveSubsystem drive, ControllerSubsystem controllers) {
    this.drive = drive;
    driver1 = controllers.getDriver1();
    addRequirements(drive, controllers);
  }

  @Override
  public void execute() {
    double l = driver1.getY(Hand.kLeft);
    double r = driver1.getY(Hand.kRight);
    drive.tankDrive(l, r);    
  }
}