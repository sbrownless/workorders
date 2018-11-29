# Work Order Project
This project is a small self contained project to test the management of sinple work orders. It basically demostrates the use of REST services to manage a repository of work ord4ers.

## Point of note
All dates that are passed into any service must be in ISO 8601 format. 
e.g. **YYYY-MM-DDTHH:mm:SS** : 2018-11-25T23:45:42Z (24 hour clock)
The default port that the services start up on is 8080. If there is a clash on the target machine in order to change this add an entry to the application.properties file in the resources directory.
i.e. **server.port = 8090**

The work order id's are limited to the range 1 - 9223372036854775807

This is a standard spring boot project to interact with it the following needs to be performed. (Assuming maven is installed)

1. Clone the repository **git clone git@github.com:sbrownless/workorders.git**
2. Build the application from the check out directory  **mvn clean install**
3. Run the application **mvn spring-boot:run**

Once the service is running the repository will be empty so it is necessary to add some entries in order to demonstrate the functionality. The project may be tested from the swagger user interface which by default is located at

***http://localhost:8080/swagger-ui.html#/work-order-controller***
of course the server and port may change depending on your deployment preferences.

I attempted to add more comprehensive documentation to swagger but there seems to be some issues with the version that I was using. As swagger is not the point of this exercise I did not pursue it.

The interfaces can be clearly seen on the Swagger UI but a short synopsis is provided below.

**REST Services**

***Create a New Work Order entry***  
Create a new work order item in the repository.  
***Parameter:id*** - must be a valid string that can be converted to a positive BigInteger as documented above.  
***Parameter:entryDate*** - must be a valid string that can be converted to a date as documented above. ***Cannot be in the future***  

***TYPE:POST***  
***LOCATION:/workorder***  
***EXAMPLE:***curl -X POST "http://localhost:8080/workorder?entryDate=2018-11-25T23%3A45%3A42Z&id=34" -H "accept: application/json"  
***Return*** Work Order object that was created e.g.  
{  
  "id": 36,  
  "workOrderClassification": "PRIORITY",  
  "timeInQueue": 322680,  
  "rank": 4093007.483645748  
}   

***Errors:*** An IllegalArgumentException will be raised if any of the inputs do not conform. Resulting in a status of 500

======================================================================================================  
***Retrieve and Delete the top Work Order entry***  
Retrieves the item number at the top of the list and removes it from the list.  
***Parameter:NONE***  

***TYPE:PATCH***  
***LOCATION:/workorder***  
***EXAMPLE:***curl -X PATCH "http://localhost:8080/workorder" -H "accept: application/json""  
***Return*** identtiy of the top work order e.g.  
36  

***Errors:*** An IllegalArgumentException will be raised if any of the inputs do not conform. Resulting in a status of 500  

====================================================================================================== 

***Deletes a Work Order entry***  
Deletes the given work entry from the repository.    
***Parameter:id*** must be a valid string that can be converted to a positive BigInteger as documented above.     

***TYPE:DELETE***  
***LOCATION:/workorder/id***  
***EXAMPLE:***curl -X DELETE "http://localhost:8080/workorder/654" -H "accept: application/json"    
***Return*** Work Order object that was created e.g.  
{  
  "id": 36,  
  "workOrderClassification": "PRIORITY",  
  "timeInQueue": 322680,  
  "rank": 4093007.483645748  
}   

***Errors:*** A NoSuchElementException will be raised if item to be deleted does not exist. Resulting in a status of 500

====================================================================================================== 

***Get the mean wait time from the reference time***  
Retrieves the mean wait time from the given reference time for all valid items in the queue. Work orders that are in the future from the reference date are not considered.     
***Parameter:referenceDate*** date from which to perform the calculation - must be a valid string that can be converted to a date as documented above.        

***TYPE:GET***  
***LOCATION:/workorder/meanwaittime/referenceDate***  
***EXAMPLE:***curl -X GET "http://localhost:8080/workorder/meanwaittime/2018-11-25T23%3A46%3A43Z" -H "accept: application/json"    
***Return:*** Mean wait time for all eligible work orders e.g.  
60   

***Errors:*** An IllegalArgumentException will be raised if any of the inputs do not conform. Resulting in a status of 500

====================================================================================================== 

***Get the work order queue position for the given identity***  
Retrieves the position for the given id, if one exists. If it does not exist -1 is simply returned.     
***Parameter:id*** - must be a valid string that can be converted to a positive BigInteger as documented above.    

***TYPE:GET***  
***LOCATION:/workorder/queue_position/id***  
***EXAMPLE:***curl -X GET "http://localhost:8080/workorder/queue_position/7" -H "accept: application/json"      
***Return:*** The location in the queue for the id, if it exists on the system. e.g.  
32   

***Errors:*** Will return -1 if the item is not in the queue. This includes if a bogus value is passed.  

====================================================================================================== 

***Get a sorted list of identities***  
Gets a sorted list of BigIntegers id's as they are ordered in the queue     
***Parameter:NONE***  
    
***TYPE:GET***  
***LOCATION:/workorder/sortedlist***  
***EXAMPLE:***curl -X GET "http://localhost:8080/workorder/sortedlist" -H "accept: application/json"      
***Return:*** The list of identifiers in the queue as they exist. e.g.  
[
  15,
  12,
  1,
  37
]   

***Errors:*** None an empty list will be returned if there is nothing to process.  

======================================================================================================   


