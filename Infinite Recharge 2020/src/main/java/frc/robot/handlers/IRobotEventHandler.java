package frc.robot.handlers;

public interface IRobotEventHandler {
    
    default void robotInit() {}
    default void disabledInit() {}
    default void autonomousInit() {}
    default void autonomousPeriodic() {}
    default void teleopInit() {}
    default void teleopPeriodic() {}

}