# Stage 1: Build the application with Node.js
FROM node:14.15.0-alpine as builder

WORKDIR /app

# Copiez le package.json et le package-lock.json pour installer les dépendances
COPY package*.json ./

# Installez les dépendances
RUN npm install

# Copiez les fichiers du projet dans le conteneur
COPY . .

# Exécutez la commande de build
RUN npm run build

# Stage 2: Create the final image with Nginx
FROM nginx:alpine

# Copiez le contenu du répertoire de construction vers le répertoire NGINX
COPY --from=builder /app/build /usr/share/nginx/html
