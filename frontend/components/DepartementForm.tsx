'use client';

import { useState } from 'react';

const API = process.env.NEXT_PUBLIC_API_GATEWAY_URL || 'http://localhost:8080';

interface DepartementFormProps {
  onSaved: () => void;
  initial?: { id?: number; nom: string };
}

export default function DepartementForm({ onSaved, initial }: DepartementFormProps) {
  const [nom, setNom] = useState(initial?.nom || '');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async () => {
    if (!nom.trim()) { setError('Le nom est requis'); return; }
    setLoading(true);
    setError('');
    try {
      const method = initial?.id ? 'PUT' : 'POST';
      const url = initial?.id
        ? `${API}/api/departements/${initial.id}`
        : `${API}/api/departements`;
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nom }),
      });
      if (!res.ok) throw new Error('Erreur lors de la sauvegarde');
      setNom('');
      onSaved();
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-xl shadow p-5 border border-gray-100">
      <h3 className="font-semibold text-gray-700 mb-3">
        {initial?.id ? 'Modifier le département' : 'Nouveau département'}
      </h3>
      {error && <p className="text-red-500 text-sm mb-2">{error}</p>}
      <div className="flex gap-2">
        <input
          className="flex-1 border border-gray-300 rounded px-3 py-2 text-sm focus:outline-blue-400"
          placeholder="Nom du département"
          value={nom}
          onChange={e => setNom(e.target.value)}
        />
        <button
          onClick={handleSubmit}
          disabled={loading}
          className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded text-sm transition disabled:opacity-50">
          {loading ? '...' : initial?.id ? 'Mettre à jour' : 'Ajouter'}
        </button>
      </div>
    </div>
  );
}
