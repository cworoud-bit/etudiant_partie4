/** @type {import('next').NextConfig} */
const nextConfig = {
  // Toutes les requêtes API pointent vers l'API Gateway
  env: {
    API_GATEWAY_URL: process.env.API_GATEWAY_URL || 'http://localhost:8080',
  },
};

module.exports = nextConfig;
