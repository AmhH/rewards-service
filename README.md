#reward-service
This service calculate reward points earned for each customer per month and total.

To run application
``
./mvnw spring-boot:run
``
Make http Get request to
``http://localhost:8080/rewards``

Sample response Response :
```json
[
    {
        "customerId": 1,
        "customerName": "John Doe",
        "rewardsByMonth": {
            "JANUARY": 466,
            "DECEMBER": 198
        },
        "totalReward": 664
    },
    {
        "customerId": 2,
        "customerName": "Sam Smith",
        "rewardsByMonth": {
            "JANUARY": 0,
            "DECEMBER": 0,
            "FEBRUARY": 248
        },
        "totalReward": 248
    }
]
```