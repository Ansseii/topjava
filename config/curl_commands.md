
#### Get All Users
> curl http://localhost:8080/topjava/rest/admin/users
#### Get All Meals
> curl http://localhost:8080/topjava/rest/meals
#### Get Meal with id 100002
> curl http://localhost:8080/topjava/rest/meals/100002
#### Get User with id 100000
> curl http://localhost:8080/topjava/rest/admin/users/100000
#### Create Meal 
> curl -X  POST -d '{"dateTime":"2018-09-25T00:00","description":"Test meal created with curl","calories":500}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals
#### Update Meal 
> curl -X  PUT  -d '{"dateTime":"2018-11-27T10:00", "description":"Updated by curl", "calories":400}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100002
#### Delete Meal 
> curl -X DELETE http://localhost:8080/topjava/rest/meals/100002