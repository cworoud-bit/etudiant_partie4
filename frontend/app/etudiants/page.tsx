'use client';

import { useEffect, useState } from 'react';
import EtudiantCard from '../../components/EtudiantCard';

const API = process.env.NEXT_PUBLIC_API_GATEWAY_URL || 'http://localhost:8080';

export default function EtudiantsPage() {
  const [etudiants, setEtudiants] = useState<any[]>([]);
  const [departements, setDepartements] = useState<any[]>([]);
  const [selectedDept, setSelectedDept] = useState<string>('');
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    setLoading(true);
    const [e, d] = await Promise.all([
      fetch(`${API}/api/etudiants`).then(r => r.json()),
      fetch(`${API}/api/departements`).then(r => r.json()),
    ]);
    setEtudiants(e);
    setDepartements(d);
    setLoading(false);
  };

  useEffect(() => { fetchData(); }, []);

  const handleDelete = async (id: number) => {
    if (!confirm('Supprimer cet étudiant ?')) return;
    await fetch(`${API}/api/etudiants/${id}`, { method: 'DELETE' });
    fetchData();
  };

  const filtered = selectedDept
      ? etudiants.filter(e => String(e.departementId) === selectedDept)
      : etudiants;

  return (
      <div>
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Étudiants</h1>
          <a href="/etudiants/new"
             className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 text-sm transition">
            + Ajouter
          </a>
        </div>

        {/* Filtre par département */}
        <div className="mb-6">
          <select
              className="border border-gray-300 rounded px-3 py-2 text-sm w-64"
              value={selectedDept}
              onChange={e => setSelectedDept(e.target.value)}>
            <option value="">Tous les départements</option>
            {departements.map(d => (
                <option key={d.id} value={String(d.id)}>{d.nom}</option>
            ))}
          </select>
        </div>

        {loading ? (
            <p className="text-gray-500">Chargement...</p>
        ) : filtered.length === 0 ? (
            <p className="text-gray-400">Aucun étudiant trouvé.</p>
        ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
              {filtered.map(e => (
                  <EtudiantCard key={e.id} etudiant={e} onDelete={handleDelete} />
              ))}
            </div>
        )}
      </div>
  );
}