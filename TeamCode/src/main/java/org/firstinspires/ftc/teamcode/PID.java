package org.firstinspires.ftc.teamcode.subsystems;

public class PID {
  private double integral = 0;
  private double previousError = 0;
  private long previousTime = 0;
  private final double kP = 0.1;
  private final double kI = 0.001;
  private final double kD = 0.01;
  private final int maxTPS = 2800;
  
  public double pid(double target, double current){
    target = Math.max(-1.0, Math.min(1.0, target/maxTPS));
    current = Math.max(-1.0, Math.min(1.0, current/maxTPS));
    
    double error = target - current;
    long currentTime = System.nanoTime();
    double deltaTime = (currentTime-previousTime) / (1e9);
    if (previousTime == 0 || deltaTime <= 0){
      previousTime = currentTime;
      previousError = error;
      return 0;
    }

    double proportion = kP*error;
    double integralTerm = kI * integral;
    if (Math.abs(proportion + integralTerm) >= 0){
      integral += error*deltaTime;
      integralTerm = kI * integral;
    }
    double derivative = (error-previousError)/deltaTime;
    double derivativeTerm = kD*derivative;
    
    double output = proportion + integralTerm + derivativeTerm;
    output = Math.max(-1,Math.min(1, output));
    previousError = error;
    previousTime = currentTime;
    return output;
  }

  public void reset(){
    integral = 0;
    previousError = 0;
    previousTime = 0;
  }
}
