public class PID {
  private double integral = 0;
  private double previousError = 0;
  private long previousTime = 0;
  
  public double pid(double target, double current){
    double error = target - current;
    long currentTime = System.nanoTime();
    double deltaTime = (currentTime-previousTime) / (1e9);
    if (previousTime == 0 || deltaTime <= 0){
      previousTime = currentTime;
      previousError = error;
      return 0;
    }

    double proportion = 0.001*error;
    integral += error*deltaTime;
    double integralTerm = 0.00001 * integral;
    double derivative = (error-previousError)/deltaTime;
    double derivativeTerm = 0.0001*derivative;
    
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
