# Sahaj Parking Lot

This project contains the solution to the parking lot problem. The problem statements have been completed as JUnit5 tests, see `MallProblemStatement1Test`, `MallProblemStatement2Test`, `StadiumProblemStatementTest` and `AirportProblemStatementTest`.

This is a gradle project built using Java 17, the tests can be run using `./gradlew check`.

The code is made up of 3 packages, `lot` for parking lot logic, `model` to represent the public model and `repository` for data access.

The parking lot codes main entry point is the `ParkingLotFactory` which gives you access to "standard" parking lot configurations for mall, stadium and airport though you can create your own custom implementations.

The standard parking lot using the `SpotManager` to obtain/release parking spots and the `FeeCalculator` to calculator the final parking fee, these can be replaced with new implementations if you need to manage spot availability or fee calculation in a different way. There shouldn't be a need for a custom `ParkingLot` implementation but you can create a custom implementation to change the interactions with the spot manager and fee calculator if needed.

...

Within `model` is the `SpotConfiguration` this is how the parking lot is configured along with a list of `IntervalCost`, this model could be exposed to our customers allowing them to configure their own parking lot. Then `Ticket` and `Receipt` models can be used to produce paper or electric versions of these objects to provide to our customers, customers.

