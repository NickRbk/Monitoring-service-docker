const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

const app = express(),
  port = process.env.PORT || 4000;

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

// configure our app to handle CORS requests
app.use( (req, res, next) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
  res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With, content-type, Authorization');
  next();
});

app.use(express.static(__dirname + '/dist/monitoring-service-front'));

app.get('/*', (req, res) => {
  res.sendFile(path.join(__dirname+'/dist/monitoring-service-front/index.html'))
});

app.listen(port, () => {
  console.log(`App running on port ${port}`);
});
