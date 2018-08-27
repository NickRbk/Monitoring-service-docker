# Monitoring service on docker

## Overview

This web app provides ability to create targetUsers, attach to them links
to social media and shows targetUsers posts in them. All operation on service you
(customer) can do after registration ang getting auth token.
Information about social activity _**updates every 30 min by schedule**_.

The back-end layer you can see [here](https://github.com/NickRbk/Monitoring-service).

## Prerequesits
Provide actual data about Twitter API to `docker-compose.*` files.

## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service-docker.git`
2) enter to downloaded folder

- You can run app from images on Docker Hub by `docker-compose up`
- Or you can run app in dev mode by `docker-compose -f docker-compose-dev.yml up`

**To shut down app use `docker-compose down` or `docker-compose -f docker-compose-dev.yml down`**