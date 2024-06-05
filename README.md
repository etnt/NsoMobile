# NSO Mobile
> Android App for getting data from a Cisco NSO system

This is an experimental Android App that can retrieve
data from a running Cisco NSO system over the RESTCONF
interface.

Currently you can retrieve information about:

* Devices
* NSO packages
* Alarms
* System counters
* Progress Trace Info
* Internal Debug Info (optional)

You can specify a number of NSO systems which you can switch between
in order to dig up the current information.

The application code is structured so that it should be relatively 
straightforward to add new functionality, e.g a custom service model.
Remember: any model loaded into NSO is also accessible via the RESTCONF API.

## Application structure

The NSO Mobile application is structured according to the MVI (Model View Intent)
principles, meaning that we separate the data (Model) from the user interface (View).
In between we have a mediating/controller layer centered around the ViewModel.

The code for each function, is located into (sub-) packages, where each package
is split into four components: `data`, `di`, `domain` and `presentation`.

* `data`: Here is input/output of data handled, e.g parsing of the incoming JSON.
* `di`: Deals with the Dependency Injection for which we are using the `koin` application.
* `domain`: Here is the middle layer located that connects the `data` and `presentation` layers.
It provides a `StateFlow` (i.e a data stream) that the `presentation` layer subscribe and react to.
It also receive and process any `intent` (i.e actions) from the `presentation` layer.
* `presentation`: Here we subscribe to a flow of data that we are interested in. This data
is displayed to the user. When any user interaction takes place we tell the `domain` layer
about our `intent` (i.e action we want to perform), which may cause external communication to
take place and/or the subscribed data to change (which may be reflected in the user interface).

Note: it is important to realize that the `presentation` layer never operates directly on the
underlaying data handled by the `domain` layer. It only tells the `domain` layer what it want to
do, e.g "sort column X in ascending order" and whatever the `domain` layer does will be reflected
in the subscribed data stream which will cause a re-rendering of the user interface.

## Adding new functionality

There are a number of steps to be taken, where some (if not all) could be
automated (in the future?). It can be helpful to study any sibling package
for the structure, naming conventions, etc (e.g `devices` or `alarms`).

1. Create a new (sub-) package under "java/se/kruskakli/nsomobile" (use functionality
in Android Studio to do this). Under this new package, create four (sub-) packages:
`data`, `di`, `domain` and `presentation`.

2. `data`: We need to parse the retrieved JSON data into the corresponding Kotlin data classes.
There is a helpful function in Android Studio that will take some JSON and produce these
data classes, which is a good starting point. You may need to annotate some of the data class
elements in order to help the `kotlinx.serialization` to get the parsing right. You also need
to define the actual internal (soutbound) API interface which you do in the Repository code.

3. `domain`: Create the beginning of the ViewModel and the UI data class that will hold the
data *copied* from the `data` layer. Also, add the *intent interface* which define the API
between the `presentation` and `domain` layer.

4. `di`: Create the dependency injection Module. This code is defining a Koin module that should contain
a singleton instance of the Repository and the ViewModel instance, both of which can be injected wherever
needed in the codebase. Don't forget to add the new module to the `NsoMobileApplication.kt` file.

5. `presentation`: Add the beginning of the *screen* , i.e our User Interface. It will make use of the
View Model for subscribing to the data and the Intent Interface for communicating with the View Model.

This will get you into a starting point for your new functionality that will compile. For an example,
look at the git tag: `adding-new-functionality-example`.

