#This file needs to be fixed in order to run the Angular app.
#see: https://mherman.org/blog/dockerizing-an-angular-app/

# base image
FROM node:14.5.0-stretch

LABEL author="Roberto Rocha"
LABEL email="rrocha.roberto@gmail.com"
LABEL data="20/07/2020"

# install chrome for protractor tests
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
RUN apt-get update && apt-get install -yq google-chrome-stable

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/student-frontend/node_modules/.bin:$PATH

# clone the github repository
RUN git clone https://github.com/rrocharoberto/devops-training.git

WORKDIR /app/devops-training/student-frontend
RUN npm install
RUN npm install -g @angular/cli

# start app
CMD ng serve --proxy-config proxy.config-docker.js --host 0.0.0.0


