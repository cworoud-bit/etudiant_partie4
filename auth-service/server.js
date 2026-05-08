const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
require('dotenv').config();

const app = express();
app.use(express.json());

// 1. Connexion à MongoDB (Sera géré par Docker Compose)
const mongoURI = process.env.MONGO_URI || 'mongodb://localhost:27017/authdb';
mongoose.connect(mongoURI)
    .then(() => console.log('Connecté à MongoDB avec succès !'))
    .catch(err => console.error('Erreur de connexion MongoDB:', err));

// 2. Modèle Utilisateur
const UserSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    password: { type: String, required: true }
});
const User = mongoose.model('User', UserSchema);

// 3. Route d'Inscription (Register)
app.post('/auth/register', async (req, res) => {
    try {
        const { username, password } = req.body;
        const hashedPassword = await bcrypt.hash(password, 10);
        const newUser = new User({ username, password: hashedPassword });
        await newUser.save();
        res.status(201).json({ message: "Utilisateur créé !" });
    } catch (err) {
        res.status(500).json({ error: "Erreur lors de l'inscription" });
    }
});

// 4. Route de Connexion (Login)
app.post('/auth/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await User.findOne({ username });
        if (!user || !(await bcrypt.compare(password, user.password))) {
            return res.status(401).json({ error: "Identifiants incorrects" });
        }
        const token = jwt.sign({ id: user._id }, 'SECRET_KEY', { expiresIn: '1h' });
        res.json({ token });
    } catch (err) {
        res.status(500).json({ error: "Erreur lors de la connexion" });
    }
});

const PORT = 5000;
app.listen(PORT, () => console.log(`Auth-service lancé sur le port ${PORT}`));