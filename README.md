Food Delivery Backend
- The requirements for the test project are:
Write an application for Food Delivery
User must be able to create an account and log in.
- Implement 2 roles with different permission levels

- Regular User: Can see all restaurants and place orders from them
Restaurant Owner: Can CRUD restaurants and meals
A Restaurant should have a name and description of the type of food they serve
A meal should have a name, description, and price
Orders consist of a list of meals, date, total amount and status
An Order should be placed for a single Restaurant only, but it can have multiple meals
Restaurant Owners and Regular Users can change the Order Status respecting below flow and permissions:

- Placed: Once a Regular user places an Order
- Canceled: If the Regular User cancel the Order
- Processing: Once the Restaurant Owner starts to make the meals
- In Route: Once the meal is finished and Restaurant Owner marks it’s on the way
- Delivered: Once the Restaurant Owner receives information that the meal was delivered by their staff
- Received: Once the Regular User receives the meal and marks it as Received Status should follow the sequence as stated above, and not allowed to move back
Status can not be changed by a different user than is stated above


Orders should have a history about the date and time of the status changing
Both Regular Users and Restaurant Owners should be able to see a list of the orders
Restaurant Owners have the ability to block a User

REST API. Make it possible to perform all user actions via the API, including authentication.
In any case, you should be able to explain how a REST API works and demonstrate that by creating
functional tests that use the REST Layer directly. Please be prepared to use REST clients like Postman,
cURL, etc. for this purpose.

This project uses:
 - Doobie
 - Cats
 - Circe
 - http4s
 - cornichon for testing
 - scala meta for testing