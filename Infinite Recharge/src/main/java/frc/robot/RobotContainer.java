/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.*;

public class RobotContainer {
  private final DriveSubsystem driveSubsystem;
  private final ControllerSubsystem controllerSubsystem;
  // Commands
  private final Command goForward;
  private final JoystickDriveCommand joystickDrive;

  public RobotContainer() {
    // Init subsystems
    driveSubsystem = new DriveSubsystem();
    controllerSubsystem = new ControllerSubsystem();
    // Init controllers
    goForward = new SequentialCommandGroup(
      new InstantCommand(() -> driveSubsystem.tankDrive(0.5, 0.5), driveSubsystem),
      Utils.withRequirements(new WaitCommand(1.5), driveSubsystem),
      new InstantCommand(() -> driveSubsystem.stop(), driveSubsystem)
    );
    joystickDrive = new JoystickDriveCommand(driveSubsystem, controllerSubsystem);
    // Other
    configureButtonBindings();
    setDefaultCommands();
  }

  
  // Set default commands HERE
  private void setDefaultCommands() {
    CommandScheduler.getInstance().setDefaultCommand(driveSubsystem, joystickDrive);
  }

  // Configure button bindings HERE
  private void configureButtonBindings() {
    final Controller driver1 = new Controller(controllerSubsystem.getDriver1());
    final Controller driver2 = new Controller(controllerSubsystem.getDriver2());
  }

  // Return the command to be run during the autonomous period
  public Command getAutonomousCommand() {
    return goForward;
  }
}
