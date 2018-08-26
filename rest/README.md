# Monitoring service

## Overview

This web app provides ability to create targetUsers, attach to them links
to social media and shows targetUsers posts in them. All operation on service you
(customer) can do after registration ang getting auth token.
Information about social activity _**updates every 30 min by schedule**_.

There is two open routes:
- `auth/sign-up` (POST) - create user (customer). Email should be unique.
```json
{
  "email": "*****",
  "firstName": "*****",
  "lastName": "*****",
  "password": "*****",
  "phoneNumber": "*****"
}
```
#### To login on service on top level exists route `/login`, so
- `/login` (POST)
```json
{
  "email": "*****",
  "password": "*****"
}
```
After that customer get access token (JWT) which pinned to header and open access
to service.

The bellow routes are protected and need auth token
### Auth controller (`/auth`)

- '/' (GET) - get customer
```json
{
  "id": "*****",
  "email": "*****",
  "firstName": "*****",
  "lastName": "*****",
  "phoneNumber": "*****"
}
```
- `/` (PATCH) - update customer
```json
{
  "email": "*****",
  "firstName": "*****",
  "lastName": "*****",
  "password": "*****",
  "phoneNumber": "*****"
}
```
- `/` (DELETE) - delete customer (all associated targetUsers deleted also)

### TargetUser controller (`api/users`)

- `/` (GET) - get all targetUsers
```json
[
    {
        "id": 18,
        "firstName": "*****",
        "lastName": "*****",
        "socialMedia": {
            "id": 12,
            "twitter": {
                "id": 12,
                "profile": {
                    "id": 9,
                    "userName": "*****",
                    "alias": "*****",
                    "profileURL": "*****",
                    "location": "*****",
                    "description": "*****",
                    "followersCount": 144,
                    "friendsCount": 160,
                    "favouritesCount": 488,
                    "statusesCount": 1132,
                    "profileImageURL": "*****"
                }
            }
        }
    }
]
```

- `/` (POST) - save new targetUser
```json
{
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (PATCH) - update targetUser by id
```json
{
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (DELETE) - delete targetUser by id

- `/{userId}/media` (POST) - set social media nickname to targetUser (like nickname at Twitter)
```json
{
	"alias": "*****"
}
```

### Social media controller (`api/media`)

- `?page=***
    &size=***
    [[ &orderBy=***, OPTIONAL PARAMETER} ]]
    [[ &d=***, SORT DIRECTION , OPTIONAL PARAMETER, BY DEFAULT desc} ]]`
    
    For `orderBy` you can set:
    - date - sorted by date of post creation
    - fav - sorted by favourites count 
    - share - sorted by shares count.
    
    So, the below url are valid:
    - `/api/media?page=0&size=5` - without sorting
    - `/api/media?page=0&size=5&orderBy=fav` - sorted by favourites count, 
    sort direction by default descending
    - `/api/media?page=0&size=5&orderBy=share&d=desc` - sorted by shares count, 
        sort direction descending (explicitly)
    - `/api/media?page=0&size=5&orderBy=date&d=asc` - sorted by creation date, sort
    direction ascending

(GET) - get posts of targeted users with pagination (set page and size value)
and sorted by optional params
```json
{
    "content": [
        {
            "createdAt": "*****",
            "text": "*****",
            "textUrl": "*****",
            "favouritesCount": 31,
            "retweetCount": 30,
            "originalAuthor": {
                "id": 12,
                "userName": "*****",
                "alias": "*****",
                "profileURL": "*****",
                "location": "*****",
                "description": "*****",
                "followersCount": 17,
                "friendsCount": 297,
                "favouritesCount": 0,
                "statusesCount": 28,
                "profileImageURL": "*****"
            },
            "targetUser": {
                "id": 9,
                "userName": "*****",
                "alias": "*****",
                "profileURL": "*****",
                "location": "*****",
                "description": "*****",
                "followersCount": 144,
                "friendsCount": 160,
                "favouritesCount": 488,
                "statusesCount": 1132,
                "profileImageURL": "*****"
            }
        }
    ],
    "totalPages": 20,
    "currentPage": 0,
    "totalElements": 20
}
```

## Prerequisites
- initially install `twitter4j-spring-boot-starter` from [there](https://github.com/sivaprasadreddy/twitter4j-spring-boot-starter)
by `mvn clean install`
- app should be run with "dev" profile
- start postgreSQL and write actual info into `resources/application-dev.yml`.

## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service.git`
2) enter to downloaded folder
3) run cmd `mvn spring-boot:run -Dspring.profiles.active=dev`