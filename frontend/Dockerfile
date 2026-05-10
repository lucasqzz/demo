FROM redis:7-alpine AS build
RUN apk add --no-cache nodejs npm
WORKDIR /app
COPY package.json ./
RUN --mount=type=cache,target=/app/node_modules \
    --mount=type=cache,target=/root/.npm \
    npm install --legacy-peer-deps
COPY . .
RUN --mount=type=cache,target=/app/node_modules \
    npm run build

FROM redis:7-alpine
RUN apk add --no-cache nginx && mkdir -p /run/nginx
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/http.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
