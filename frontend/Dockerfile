FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build

# CMD ["npm", "run", "build", "--", "--host", "0.0.0.0"]
CMD ["node", "build"]
