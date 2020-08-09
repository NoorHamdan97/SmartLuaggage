#define LOGGING
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h> 

///////////////////////////////////
const char *ssid =  "G5 SE_9713";
const char *pass =  "night-90210";

const char* host = "http://us-central1-case-37c67.cloudfunctions.net";
String url = "/checkSwitch/";
String face_url = "/isNotUser/";
String check_url = "/checkUser/";
String vacuum_on_url = "/turnOnVacuum/";
String vacuum_off_url = "/turnOffVacuum/";

// defines pins numbers
const int stepPin = 0; //D3 
const int dirPin = 2; //D4 blue
int ledPin = 14; //D5
int pushButton = 13; // D7

/////////////////////////////////////////
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  delay(10);
 
 // Sets the pins as Outputs
  pinMode(stepPin,OUTPUT); 
  pinMode(dirPin,OUTPUT);
  pinMode(ledPin,OUTPUT);
  pinMode(pushButton,INPUT);
               
  Serial.println("Connecting to: ");
  Serial.println(ssid); 

  //WiFi.mode(WIFI_STA); 
  WiFi.begin(ssid, pass); 
  while (WiFi.status() != WL_CONNECTED) 
  {
      delay(500);
      Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected"); 
  Serial.println("IP address: "); 
  Serial.println(WiFi.localIP());
  Serial.print("Host: ");
  Serial.println(host); // Use WiFiClient class to create TCP connections
}

void do_func(){
  WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(url);
  String address = host + url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  auto httpCode = http.GET(); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  if(payload == "true"){
    Serial.println("I am Hereeeeeee");
    int val;
    val=digitalRead(ledPin);
    if(val==0){//eza eldw mtfe y3ne awl mra mnfot mn7rk ,b3deen mnser bs nfot n5le eldy mdwe
      //turn on vacuum
     digitalWrite(dirPin,HIGH); // Enables the motor to move in a particular direction
     for(int x = 0; x < 2000; x++) {
       digitalWrite(stepPin,HIGH); 
       delayMicroseconds(500); 
       digitalWrite(stepPin,LOW); 
       delayMicroseconds(500); 
      }
      delay(1000); // One second delay
    }
    digitalWrite(ledPin,HIGH);  //turn LED on
    delay(1000);
  }
  else if(payload == "false"){
    int val;
    val=digitalRead(ledPin);
    if(val==1){

    //turn off vacuum
    digitalWrite(dirPin,LOW); //Changes the rotations direction
    for(int x = 0; x < 2000; x++) {
      digitalWrite(stepPin,HIGH);
      delayMicroseconds(500);
      digitalWrite(stepPin,LOW);
      delayMicroseconds(500);
     }
     delay(1000);
    }
    digitalWrite(ledPin,LOW);  //turn LED off
    delay(1000);
  }
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection");
}

void faceUnknown(bool status){
  WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(face_url);
  String address = host + face_url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  String postData = "num=" + String(status);
  auto httpCode = http.POST(postData); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection");
}

bool checkUser(){
  bool user_status;
  WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return false;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(check_url);
  String address = host + check_url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  auto httpCode = http.GET(); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  if(payload == "true"){
    user_status=true;
  }
  else if(payload == "false"){
    user_status=false;
  }
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection");
  return user_status;
}

void turnOnVacuum(){
    WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(vacuum_on_url);
  String address = host + vacuum_on_url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  String postData = "num=" + String(1);
  auto httpCode = http.POST(postData); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection"); 
}


void turnOffVacuum(){
    WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(vacuum_off_url);
  String address = host + vacuum_off_url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  String postData = "num=" + String(0);
  auto httpCode = http.POST(postData); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection"); 
}

int checkVacuumStatus(){
  int status;
  WiFiClient client;
  const int httpPort = 80; 
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return 2;
  }
  Serial.print("Requesting URL: "); 
  Serial.println(url);
  String address = host + url; 
  HTTPClient http; 
  http.begin(address); 
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  auto httpCode = http.GET(); 
  Serial.println(httpCode); //Print HTTP return code 
  String payload = http.getString(); 
  if(payload == "true"){
    status=1;
  }
  else if(payload == "false"){
    status=0;
}
  Serial.println(payload); //Print request response payload 
  http.end(); //Close connection Serial.println(); 
  Serial.println("closing connection");
  return status;
}


void loop() {
do_func();
  delay(2000);
  
  //if we press the button and face is recognized send http request to post 1 to the switch 
  int button_state = digitalRead(pushButton);
  Serial.println(button_state);
  delay(500);
  if(button_state == 1){
    Serial.println("the button is pressed");
    bool user_status=checkUser();
    //delay(1000);
    if(user_status){
      int vacuum = checkVacuumStatus();
      if(vacuum==0){
      turnOnVacuum();
      }else if(vacuum==1){
        turnOffVacuum();
      }
      //delay(1000);
    }
//    else{
//     ignore();
//    }
//    
    faceUnknown(false);
  }
  delay(2000);
  
  //if we press the button and face is not recognized ignore


}
