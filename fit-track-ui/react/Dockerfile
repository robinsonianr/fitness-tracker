FROM node:18-alpine

ARG api_base_url

ENV NODE_OPTIONS="--max-old-space-size=4096 --gc-interval=100"

WORKDIR /app
COPY package*.json .

RUN npm i --silent

COPY . .
RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env

EXPOSE 5173

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:5173/ || exit 1

CMD ["npm", "run", "start"]