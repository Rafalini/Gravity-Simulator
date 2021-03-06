# Planets simulator

![Gravity1s](https://user-images.githubusercontent.com/44322872/79970879-e24d5800-8493-11ea-8d5b-7afaa51656af.JPG)

### Description:

Idea is to simulate gravity influence of stars and planets on each other. It is simplified model, using Verlet integration for calculating position in next quants of time. All units are relative and have no relationship with real units, only principal of movement is recreated. Program is realised in MVC design pattern.

## Running:

It is recomended to use executable .jar file. All source files are in src in appropriate packages, Main.java is main class.
To run .jar use:  :$ java -jar Planet_Simulatior_by_Rafalini.jar

### Usage:

By clicking somwhere in black window (Okno na wszechświat) user creates space object (planet or star) with parameters visible in smaller window (Centrum dowodzenia wszechświatem). It is possible to change mass (mass slider) and initial speed (X and Y values, relative to object position not center of the window). To start simulation user has to change time value*(change on time slider, click on "czas++" to increase value by 1 or insert certain value, negative numbers are not supported :P). At this point simulation will procced, it is possible to add new objects all the time.

* visible movement at time value ~10, the higher this value is the faster objects move but calculation error increases.


Big text box in the middle is actions log. Buttons above it controll amount of information displayed and check boxes control additional dispaly features: 

1. "Ścieżki planet" -path of object is drawn, not from beginning, its last 'few' positions
2. "inne kreski"-    connects all planets with each other with lines, helps to track planets out side of the drawing area, but its                            readable only when there are a few of them.

It is possible to generate certain presets of space objects (buttons and sliders below white text field, log pannel). Slider controls amout of objects, first option list decides about shape and layout, second adds initial speed and third sets constant or random mass (random value is based on current mass selected on mass slider, second from the top of the window). Lwermost left button generates selected preset.

Lowermost right button removes all space objects.

###Fireworks preset
![Grav4](https://user-images.githubusercontent.com/44322872/79970689-97cbdb80-8493-11ea-8404-e428125dc872.JPG)
###Grid preset
![Grav3](https://user-images.githubusercontent.com/44322872/79970702-9dc1bc80-8493-11ea-9db3-5df8d88190ec.JPG)
###Grid preset
![Grav2](https://user-images.githubusercontent.com/44322872/79970695-9b5f6280-8493-11ea-9540-3c3e0814a85a.JPG)

## Notes:

**This problem has square complexity, with more than ~3000 objects simulation may freeze (its dependant on hardware, work load etc)**

**Due to java's task queing effect of freezes and 'jumps' may occur with one, or very small number of space objects**

**Detailed instruction in "planety-dokumentacja.pdf", but for now in polish only**

**It is recomended to use java 8 runtime environment, but older versions may wor as well**
