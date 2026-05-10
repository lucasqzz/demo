FROM redis:7-alpine AS base

FROM base
RUN apk add --no-cache nginx && mkdir -p /run/nginx
COPY dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/http.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
