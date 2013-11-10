Arduino_Manager
===============

My first Arduino+Netbeans project. This implementation shows how to connect Arduino and Netbeans based on "blink" example.  

NetBeans part is commited on GitHub, please find the Arduino part below:
--
int val = 0;
int led = 13;

void setup()
{
 Serial.begin(9600);
 pinMode(led, OUTPUT);
}
void loop()
{
 delay(100);
}
void serialEvent() // To check if there is any data on the Serial line
{
  while (Serial.available())  {
    val = Serial.parseInt();
    //Switch on the LED, if the received value is 1.
    if(val == 1) {   
      digitalWrite(led, HIGH);
      //Switch off the LED, if the received value is 1.  
    }  else if (val == 0) { 
        digitalWrite(led, LOW);        
      }
  }
//Serial.println("Arduino says...OK");
Serial.println(digitalRead(led));
}
