const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const authRoutes = require('./routes/auth');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Routes
app.use('/auth', authRoutes);

// Health check
app.get('/health', (req, res) => {
    res.status(200).json({ status: 'OK', service: 'auth-service', timestamp: new Date() });
});

// Connexion MongoDB
mongoose.connect(process.env.MONGODB_URI || 'mongodb://mongodb:27017/auth_db')
    .then(() => console.log('✅ MongoDB connected successfully'))
    .catch(err => console.error('❌ MongoDB connection error:', err));

// Start server
app.listen(PORT, () => {
    console.log(`🚀 Auth service running on port ${PORT}`);
});