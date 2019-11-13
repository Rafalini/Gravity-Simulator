# Planets simulator

Description:

Idea is to simulate gravity influence of stars and planets on each other. It is simplified model, using Verlet integration for calculating position in next quants of time. All units are relative and have no relationship with real units, only principal of movement is recreated. Program is realised in MVC design pattern.

Running:

It is recomended to use executable .jar file. All source files are in src in appropriate packages, Main.java is main class.
To run .jar use:  :$ java -jar Planet_Simulatior_by_Rafalini.jar

Usage:

By clicking somwhere in black window (Okno na wszechświat) user creates space object (planet or star) with parameters visible in smaller window (Centrum dowodzenia wszechświatem). It is possible to change mass (mass slider) and initial speed (X and Y values, relative to object position not center of the window). To start simulation user has to change time value*(change on time slider, click on "czas++" to increase value by 1 or insert certain value, negative numbers are not supported :P). At this point simulation will procced, it is possible to add new objects all the time.

*visible movement at time value ~10, the higher this value is the faster objects move but calculation error increases.


Big text box in the middle is actions log. Buttons above it controll amount of information displayed and check boxes control additional dispaly features: 

"Ścieżki planet" -path of object is drawn, not from beginning, its last 'few' positions
"inne kreski"-    connects all planets with each other with lines, helps to track planets out side of the drawing area, but its readable                     only when there are a few of them.

It is possible to generate certain presets of space objects (buttons and sliders below white text field, log pannel). Slider controls amout of objects, first option list decides about shape and layout, second adds initial speed and third sets constant or random mass (random value is based on current mass selected on mass slider, second from the top of the window). Lwermost left button generates selected preset.

Lwermost right button removes all space objects.


**Notes:

**This problem has square complexity, with more than ~3000 objects simulation may freeze (its dependant on hardware, its usage etc)

**Due to java's task queing effect of freezes and 'jumps' may occur with one, or very small number of space objects

**Detailed instruction in "planety-dokumentacja.pdf", but for now in polish only

**It is recomended to use java 8 runtime environment, but older versions may wor as well
