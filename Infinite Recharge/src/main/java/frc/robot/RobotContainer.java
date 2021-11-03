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
  private final ShooterSubsystem shooterSubsystem;
  private final IntakeSubsystem intakeSubsystem;
  private final TowerSubsystem towerSubsystem;

  // Commands
  private final AutoForward autoForward;
  private final JoystickDriveCommand joystickDrive;

  public RobotContainer() {
    // Init subsystems
    driveSubsystem = new DriveSubsystem();
    controllerSubsystem = new ControllerSubsystem();
    shooterSubsystem = new ShooterSubsystem();
    intakeSubsystem = new IntakeSubsystem();
    towerSubsystem = new TowerSubsystem();
    autoForward = new AutoForward(driveSubsystem);
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

    driver1.getBButton().whileHeld(new StartEndCommand(
      () -> {
        intakeSubsystem.setSpeed(1.0);
      },
      () -> {
        intakeSubsystem.stopIntake();
      }
    ));

    driver1.getLeftBumper().whileHeld(new StartEndCommand(
      () -> {
        // intakeSubsystem.setTower(0.50);
        towerSubsystem.oneBall(.35);
      },
      () -> {
        // intakeSubsystem.stopTower();
        towerSubsystem.stopTower();
      }
    ));

    driver1.getAButton().whileHeld(new StartEndCommand(
      () -> {
        // intakeSubsystem.setTower(0.50);
        towerSubsystem.purge(.50);
      },
      () -> {
        // intakeSubsystem.stopTower();
        towerSubsystem.stopTower();
      }
    ));


    driver1.getRightBumper().whileHeld(new StartEndCommand(
      () -> {
        shooterSubsystem.setSpeed(1.0);
      },
      () -> {
        shooterSubsystem.stop();
      }
    ));
  }

  // Return the command to be run during the autonomous period
  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
      new AutoShoot(towerSubsystem, shooterSubsystem),
      new AutoForward(driveSubsystem).withTimeout(3.5)
    );
  }
}
