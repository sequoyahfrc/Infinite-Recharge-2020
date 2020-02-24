package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;

class MotorButtonBinding {
    private final SpeedController[] motors;
    private double speed;

    public MotorButtonBinding(final double speed, final SpeedController... motors) {
        this.motors = motors;
        this.speed = speed;
    }

    //if expression is true, start the motor at a certain speed specified in the constructor
    public void SetMotors(final boolean expression) {
        for (SpeedController motor : motors) {
            if (expression) {
                motor.set(speed);
            } else {
                motor.stopMotor();
            }
        }
    }

    public void SetSpeed(double speed) {
        this.speed = speed;
    }
}