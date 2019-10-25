FROM node:13.0.1-alpine

RUN mkdir -p /src/app

WORKDIR /src/app

RUN npm set strict-ssl false

RUN npm install express --no-save

COPY ./dist .

COPY ./server .

EXPOSE 80

ENV PORT=80

CMD ["node", "server.js"]

# docker build -t ayortanli/todoappclient:1.0.0 .
# docker run -d --name todoappclientcontainer -p 80:80 ayortanli/todoappclient:1.0.0