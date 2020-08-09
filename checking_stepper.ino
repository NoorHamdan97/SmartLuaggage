/*     Simple Stepper Motor Control Exaple Code
 *      
 *  by Dejan Nedelkovski, www.HowToMechatronics.com
 *  
 */
// defines pins numbers
const int stepPin = 0; //D3 -Fixed
const int dirPin = 2; //D4 -Fixed
int range=0;
 
void setup() {
  // Sets the two pins as Outputs
  pinMode(stepPin,OUTPUT); 
  pinMode(dirPin,OUTPUT);
}
void loop() {
  
//  digitalWrite(dirPin,HIGH); // Enables the motor to move in a particular direction
//  // Makes 200 pulses for making one full cycle rotation
//  for(int x = 0; x < 2800; x++) {
//    digitalWrite(stepPin,HIGH); 
//    delayMicroseconds(500); 
//    digitalWrite(stepPin,LOW); 
//    delayMicroseconds(500); 
//  }
//  delay(1000); // One second delay
//  
  digitalWrite(dirPin,LOW); //Changes the rotations direction
  // Makes 400 pulses for making two full cycle rotation
  for(int x = 0; x < 2800; x++) {
    digitalWrite(stepPin,HIGH);
    delayMicroseconds(500);
    digitalWrite(stepPin,LOW);
    delayMicroseconds(500);
  }
  delay(1000);
}
