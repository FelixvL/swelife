# swelife
Project SWE2


Project:  LIFE_2015 GUI Callback-demo v1.0
Release:  19/11/2016
Author:   Ron Olzheim


::::DESCRIPTION::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
Demo project demonstrating custom callback events and observables used for interlinking the domain 
classes with the 'PlayField' JavaFX GUI Class. The TileMap Class uses the ViewPort Class to dynamically
render only the visible Tiles while featuring manual XY-offsets and TileMap zooming as well as 
auto-zoom (which will fit the entire TileMap to the PlayField render area).


::::PROJECT GOALS:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
This application is an initial proof-of-concept code experiment for:
    - testing and demonstrating Domain / GUI Class interlinking using Callback Events and Observables,
    - testing software architecture concepts,
    - and measuring performance of the TileMap Renderer Algorithm.


::::CONCLUSION::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
Custom CallbackEvent Class:
    - Class features Generic Data-type event detail passing and works very well.
    - Multiple Event events can be created for a single class, by implementing instances as public fields.
    - A Single Event can be trapped by multiple listener Classes that are registered to this event.
    
TileMap Renderer Algorithm Performance:
    - The overal performance seems very good, so far: 
    - Full-screen rendering of a 140x140 TileMap that consists of 19600 rounded filled tiles, 
       with 9600 tiles simultanuously visible at any given time, shows no visible lag during scroll animation 
       on my Dell XPS15 with 4K Screen resolution.
    - Algorithm performance needs to be tested on other machines as well.
      
Software Architecture concept:
    - The Relationship between TileMap, ViewPort and PlayField (GUI) Classes ended up being far too complex
      and needs to be simplified / re-designed.
    - The concept of introducing a ViewPort, TileMapper and Tile Class, however, seems the way to go.
    - Perhaps, the Tile Class should be extended with knowledge about his own position in the TileMap.
    - The GUI should be extended with a cursor functionality for selecting Tiles.
    
To be continued....

