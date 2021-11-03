package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoForward extends CommandBase{

  private final DriveSubsystem drivingSubsytem;
  private final Timer myTimer;

  public AutoForward(DriveSubsystem subsystem) {
    drivingSubsytem = subsystem;
    addRequirements(drivingSubsytem);
    myTimer = new Timer();
  }

  @Override
  public void initialize() {
    myTimer.reset();
    myTimer.start();
  }

  @Override
  public void execute() {
    drivingSubsytem.arcadeDrive(-.5, 0);
  }

  @Override
  public void end(boolean interrupted) {
    drivingSubsytem.arcadeDrive(0.0, 0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}