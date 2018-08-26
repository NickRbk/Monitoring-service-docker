FROM node:alpine as builder
WORKDIR '/app'
RUN apk add yarn
COPY ./package.json ./
RUN yarn install
COPY ./ ./
RUN yarn build
EXPOSE 4000
CMD yarn start