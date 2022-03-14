# creditline
Credit Line approval API

##About the credit line project

creditline is a RESTful API intended to be used for evaluating credit line requests based on the provided input data.

It is a Java project that uses the Springboot framework and can be built/run using the gradle tool.

##Prerequisites

You need to have Java SDK version 11 or later installed on your system.

Clone the Git repository:

>git clone https://github.com/gunterhm/creditline.git

##Running the application

In order to download dependencies, build the project and run it, you just need to type the following command:

>./gradlew bootRun

##Request

For local testing we will use the following URL:

http://localhost:8080/creditline

It needs to be POST request with Basic Authorization header that will include a user/password information.

If you use Postman, just go to the Authorization tab and select Basic Auth, then type user and password.

For this release the password is not going to be authenticated, however the user will be used to identify the customer requesting the credit line approval.

The body for this POST request is a JSON object with the following format:

```javascript
{
"foundingType": "SME",
"cashBalance": 435.30,
"monthlyRevenue": 4235.45,
"requestedCreditLine": 100,
"requestedDate": "2021-07-19T16:32:59.860Z"
}
```

##Output
The response from the service is a JSON object with the following format:

```javascript
{
    "accepted": true,
    "acceptedCreditLine": 100.0,
    "timesRejected": 1
}
```

