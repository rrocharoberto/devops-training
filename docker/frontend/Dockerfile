FROM nginx

LABEL author="Roberto Rocha"
LABEL email="rrocha.roberto@gmail.com"
LABEL data="03/09/2020"

## Copy our default nginx config
COPY nginx/default.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY ./dist/student-frontend/ /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
