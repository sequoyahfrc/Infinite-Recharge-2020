package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class AutoShoot extends SequentialCommandGroup {

  TowerSubsystem towerSubsystem;
  ShooterSubsystem shooterSubsystem;
  
  public AutoShoot(TowerSubsystem towerSubsystem, ShooterSubsystem shooterSubsystem) {
    this.towerSubsystem = towerSubsystem;
    this.shooterSubsystem = shooterSubsystem;
    `
    addCommands(
      new InstantCommand(() -> shooterSubsystem.setSpeed(0.8)),
      new WaitCommand(4.0),
      new InstantCommand(() -> towerSubsystem.setTower(1.0)),
      new WaitCommand(0.2),
      new InstantCommand(() -> towerSubsystem.stopTower()),
      new WaitCommand(3.0),
      new InstantCommand(() -> towerSubsystem.setTower(1.0)),
      new WaitCommand(0.2),
      new InstantCommand(() -> towerSubsystem.stopTower()),
      new WaitCommand(3.0),
      new InstantCommand(() -> towerSubsystem.setTower(1.0)),
      new WaitCommand(0.4),
      new InstantCommand(() -> shooterSubsystem.stop()),
      new InstantCommand(() -> towerSubsystem.stopTower())
    );
  }
  
}