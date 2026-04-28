'use client';

import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';

const API = process.env.NEXT_PUBLIC_API_GATEWAY_URL || 'http://localhost:8080';

export default function EtudiantDetailPage() {
  const router = useRouter();
  const params = useParams();
  const id = params?.id as string;
  const isNew = id === 'new';

  const [form, setForm] = useState({
    cin: '', nom: '', email: '',
    dateNaissance: '', anneePremiereInscription: new Date().getFullYear(), departementId: '',
  });
  const [departements, setDepartements] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetch(`${API}/api/departements`).then(r => r.json()).then(setDepartements);
    if (!isNew) {
      fetch(`${API}/api/etudiants/${id}`)
        .then(r => r.json())
        .then(e => setForm({
          cin: e.cin, nom: e.nom, email: e.email,
          dateNaissance: e.dateNaissance,
          anneePremiereInscription: e.anneePremiereInscription,
          departementId: String(e.departementId || ''),
        }));
    }
  }, [id]);

  const handleSubmit = async () => {
    setLoading(true); setError('');
    try {
      const body = { ...form, departementId: form.departementId ? Number(form.departementId) : null };
      const res = await fetch(
        isNew ? `${API}/api/etudiants` : `${API}/api/etudiants/${id}`,
        {
          method: isNew ? 'POST' : 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(body),
        }
      );
      if (!res.ok) { const err = await res.json(); throw new Error(err.error || 'Erreur'); }
      router.push('/etudiants');
    } catch (e: any) {
      setError(e.message);
    } finally { setLoading(false); }
  };

  const field = (label: string, key: keyof typeof form, type = 'text') => (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">{label}</label>
      <input
        type={type}
        className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-blue-400"
        value={form[key]}
        onChange={e => setForm(f => ({ ...f, [key]: e.target.value }))}
      />
    </div>
  );

  return (
    <div className="max-w-lg mx-auto">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">
        {isNew ? 'Nouvel étudiant' : 'Modifier l\'étudiant'}
      </h1>
      {error && <p className="text-red-500 text-sm mb-4 bg-red-50 p-3 rounded">{error}</p>}

      <div className="bg-white rounded-xl shadow p-6 flex flex-col gap-4">
        {field('CIN', 'cin')}
        {field('Nom complet', 'nom')}
        {field('Email', 'email', 'email')}
        {field('Date de naissance', 'dateNaissance', 'date')}
        {field('Année première inscription', 'anneePremiereInscription', 'number')}

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Département</label>
          <select
            className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
            value={form.departementId}
            onChange={e => setForm(f => ({ ...f, departementId: e.target.value }))}>
            <option value="">-- Aucun --</option>
            {departements.map(d => (
              <option key={d.id} value={String(d.id)}>{d.nom}</option>
            ))}
          </select>
        </div>

        <div className="flex gap-3 mt-2">
          <button onClick={() => router.back()}
            className="flex-1 border border-gray-300 text-gray-700 py-2 rounded hover:bg-gray-50 text-sm transition">
            Annuler
          </button>
          <button onClick={handleSubmit} disabled={loading}
            className="flex-1 bg-blue-600 text-white py-2 rounded hover:bg-blue-700 text-sm transition disabled:opacity-50">
            {loading ? 'Enregistrement...' : 'Enregistrer'}
          </button>
        </div>
      </div>
    </div>
  );
}
