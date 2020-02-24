package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;

class MotorButtonBinding {

    //should ONLY be set in the constructor, and not modified in ANY WAY
    //"[]" means array, or list, so motors is a array (list) of SpeedControllers
    private final SpeedController[] motors;
    //the speed to set all of the motors to in the array above
    private double speed;

    //"..." means you can pass any amout of values for one argument, it is stored as an array (see line 9)
    public MotorButtonBinding(double speed, SpeedController... motors) {
        this.motors = motors; //see line 9
        this.speed = speed; //see line 11
    }

    //if "expression" is true, start the motor at a certain speed stored in "speed" (see line 11)
    //you can do "SetMotors(driver1.getXButton())" to bind all of
    //the motors in motors array (see line 9) to the X button on the driver1 controller
    //so if you press X on driver1, the motors you specified for this MotorButtonBinding
    //will turn on
    public void SetMotors(boolean expression) {
        for (SpeedController motor : motors) { //motors is stored on line 9
            if (expression) {
                motor.set(speed); //speed is stored on line 11
            } else {
                motor.stopMotor();
            }
        }
    }

    //use this to change the speed in case you want to change the speed
    //APPLIES TO ALL MOTORS IN THE MOTORS ARRAY!!! (see line 9)
    public void SetSpeed(double speed) {
        this.speed = speed;
    }
}