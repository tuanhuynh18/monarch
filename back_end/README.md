# Admin Dashboard
A simple admin dashboard is available at [/admin][admin] that provides a tabular way to examine the data for each
object class.

# API Documentation
When submitting form data to the API, set the `Content-Type` and `Accepts` HTTP headers to `application/json`. Any form
data sent to the server (e.g. via `POST` actions) should have a JSON body encoded as `raw json`, `form-data`, or
`x-www-form-urlencoded`.

## Querying the API via Postman
[Postman][postman_download] is an app to query APIs. Open the [Monarch Postman Collection][postman_collection] and 
the [Monarch Postman Environment File][postman_environment] in the `back_end` folder in Postman to query and check the 
API on your computer.

Refer to the [schema.rb][schema] file for a complete
look at the objects and properties available. Most fields shown will be sent in JSON responses, some are internal
fields.

## Sample Data
The API sets up [seed data][seeds] to generate some fake sample data. Most of this data will look weird and random 
because it has been randomly generated. Refrain from deleting too much sample data.  

## Authentication
Authenication is handled by user session cookies generated by the Ruby on Rails server. After signing in, a 
`_back_end_session` cookie will be returned. This cookie must be sent in subsequent request headers to authenticate the 
user.

| **Function**   | **Method** | **Endpoint**           | **Body**                                                       |
|----------------|:----------:|------------------------|----------------------------------------------------------------|
| Create Account |   `POST`   | `/users.json`          | `{ "user" : { "username" : USERNAME, "password": PASSWORD } }` |
| Log In         |   `POST`   | `/users/sign_in.json`  | `{ "user" : { "username" : USERNAME, "password": PASSWORD } }` |
| Sign Out       |    `GET`   | `/users/sign_out.json` |                                                                |

[admin]: https://yata-monarch.herokuapp.com/admin
[postman_download]: https://www.postman.com/downloads/
[postman_env]: https://github.com/tuanhuynh18/monarch/blob/main/back_end/yata_back_end.postman_environment.json
[postman_collection]: https://github.com/tuanhuynh18/monarch/blob/main/back_end/yata_back_end.postman_collection.json
[schema]: https://github.com/tuanhuynh18/monarch/blob/main/back_end/db/schema.rb
[seeds]: https://github.com/tuanhuynh18/monarch/blob/main/back_end/db/seeds.rb