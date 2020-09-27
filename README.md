This is the README for The Easy Animation Model made by Lisa Lam and Soumeng Chea. We will discuss our decision choices on the designs for this assignment. Also, we will discuss what we took out, added, and etc. for this assignment.

# Part 1

After much discussion about the assignment, we both decided to focus our first animation model into two types of interfaces – the ShapesInterface and AnimationInterface, which would allow us to our animation efficiently. By taking this route, we able to create a ShapeInterface for our implement shape, which the class method only contains the getter functions. Since we need to pass any shape implements after its mutation, we do not want the user to be able to input anything else, which would potentially mess the shape field. By using this approach, we able to mutate the field shapes within our model command. Otherwise, we would have to iterate and improvise new objects for every iteration of the animation. If we decided to use a different approach, it would take thousand of secs to run the program because there are thousands of shapes. Along with ShapeInterface, we also use an enum class for ShapeTypes, a Position class, and AbstractShape to implement the necessary getters and constructors for each field without repetitive codes for each shape. Having two main interface makes it easier to create any shapes and the implementation of our shape interface.

Another interface constructor that we created is AnimationInterface. Similar to ShapeInterface, the interface mainly getter command such as getStartState(), getEndState(), and so on. By implementing a time and shape type, the get state method would return the changes according to the command line. Along with the interface, we also made an abstract command class – AbstractAnimation. With this abstract command class, we created a class method where it throws in an illegal exception statement if either the width or height is negative or if the appearance time is less than disappearance time. For when we want to change object color, its dimension, and its move – we decided to create a separate class method, which extends from AbstractAnimation. We decided to design the three command classes separately because we anticipate that the user would want to move the shapes around, change the shape color, or enhance the shape size.

# Part 2

For this assignment, we spent days trying to connect our part 1 code to the AnimationBuilder and AnimationReader. After working on it for quite some time, we both concluded that we decided to scrap everything and went back to square 1. 

We both decided to start the assignment from scratch and tried to refactor some of our part 1 code from the previous classes and interface to get to "work" with the new re-design animation model. 

The first route that we took in this assignment is to created new interfaces for the model but also provided supporting interfaces along with the "main" interface. We overhauled our entire builder class to support the new incoming interface(s) that used to create models that given to us with the new code. In doing so, we had to redo how we wrote commands as we initially divided commands up based on what fields were changing. Another change we made was that we added getters for the width, height, commands, and shapes to our model, making sure that we do not reference the maps that the model uses to create the animation. And finally, we also implement a new method – draw() and duplicate(), which we did not have for the previous assignment. These methods would help us to draw our shapes for the animation and make a copy of the shape that we need.

On the new code, we have a total of 5 interfaces for our model functions. Well, technically, 4 if we exclude the AnimationBuilder interface, which was provided by the professor. The interfaces act us a ReadOnly methods – Getters and Setters function, for the animation and shapes. We added MCommand. We also created getters for each of the fields of the master command to facilitate this update. This MCommand allows us to update any changes within the fields all at once. We also created an Ellipse class that is similar to the oval class since the txt files call for an ellipse. 

The main course of this assignment would focusing on we approach the View commands of the animation.

In this assignment, we decided to create one interface called IView, which is the primary interface that SVG, Textual, and Visual view all share one standard method, play(). This method either starts the animation in the visual case or creates the append containing the text that represents the animation in the test cases. We also created an IView extension interface for SVG and Textual view that only focuses on a getText() command – IViewText. This method only returns the string that represents the animation as a text, a description text.

Out of the three classes for Views, the TextualView is probably the most simple out of all. This class implemented the play() as well as getText() as we formatted. The play() use as an initialized for the append, which goes through shapes and commands of the given model. Their output is a string of the shape start and ends the time of the shape.

Similarly to TextualView, the SVG is also somewhat merely. Like TextualView, it takes in a model and gets its commands and shape. The output for which it creates a string of text in XML format. This format is a representation of the movement of each shape. The constructor in SVG would take in the tick speed and allow the users to implement new input for the animation, whether they want it to slow down or faster. The SVG will initializes each shape with the correct tags, at the correct location and writes each of the commands for their designate tag.

Last View methods, the Visual View methods use a Javax swing library that creates the animation and plays it. It takes a model and a tick per the second parameter that sets the speed of the animation. The animation that played the set in and used it initialize width and height in the model, then we use the scroll bar library that allows the user to scroll to whichever direction they want the animation to use. The panel of the animation is to draw the set in values for shape. These values are the initialize of how big the shape with the draw method, and it starts and ends time. There is an interface called IDrawAnimationPanel that we used for drawing the shapes and is used in the Visual View to create the animation. 

Last but not least, we also wrote the main class called "Main." We wrote this class in a not subtle nod quality compare to our work. In the method, we look through the given parameter and a method that allows the user to input their preferred values for the animation.

# Part 3 – Finale

In comparison to Part 2, we did not do anything much – such as re-design from scratch, in this assignment. However, we did tweak small things here and there. We rename some files, so it fits more appropriately with their contractor duties. We changed MCommand to AnimationImpl, and I think that is all the small change we made, although we did add enum for the model package – ShapeTypes.

For a starter, we refactored some of our code and rearranged them, so it would able to work with new methods and classes efficiently. We added another interface to our View package called IEditView. The new interface contains a bunch of editing methods in it, and we also use the Java build-in library, ActionListener, for the setter methods. We also extended the existing builder constructor with another interface – IBuild, which contains a getter for shapes and commands. 

As for new codes, we created an interface – IController, that contains all the methods for the controller implementations. We only gave it one method in the interface void start() that takes no arguments and creates and runs the controller.  Inside of our controller constructor, we had the method to take the editable builder, an editable view, and a tick speed. In our controller, we implement methods such as actionPerformed() – a method that allows users to play, pause, loop, and restart the animation via mouse clicking. Other methods we included in the controller like run() – a method that allows the action to perform by initiate tick, change the shape states, and call any display method into the view. 

We deal with some errors along the way. Because of that, we decided to implements an error display for the user, so when the user inputs the wrong implementation, the field would not crash, which also located in IEditView – View package. This method is super helpful when animation continues to run, and the user attempt to insert a non-positive number for either width, height, and speed. 

The play() method in our controller uses the builder that contained inside the controller to build a model based on the command, which then with the schedule timer based on the given tick – it builts the animation for the user.

The main reason behind our decision in our choice to pass the builder into the controller is that it makes the animation run smoothly. As for our implementation, we also store all of the shapes and commands in a LinkedHashmap – which sort any commands based on their start time. The implementation mainly focus on contructors in Model package and View package.

# Running The Animation

To run the program, user have to download A8_1.jar from the folder resources as well as all the text files, which are in the in the same file. Then, put all the files in a new folder, type out the configuration in the command-prompt/terminal. In the run configuration, the user can also specify command-line arguments, such as the file you want to read in, the location you want the output to be printed, the view name you want to use, and the speed of the animation. The options for the view name are "text," "visual," "edit," and "svg". For example,

java -jar A8_1.jar -in text-transcript.txt -speed 50 -view visual -out out.txt

This command will use text-transcript.txt for the animation file with its output going to the file out.txt, and create a visual view to show the animation at a speed of 50 ticks per second.

