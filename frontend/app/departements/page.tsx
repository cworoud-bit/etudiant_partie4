'use client';

import { useEffect, useState } from 'react';
import DepartementForm from '@/components/DepartementForm';

const API = process.env.NEXT_PUBLIC_API_GATEWAY_URL || 'http://localhost:8080';

export default function DepartementsPage() {
  const [departements, setDepartements] = useState<any[]>([]);
  const [editing, setEditing] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  const fetchDepts = async () => {
    setLoading(true);
    const data = await fetch(`${API}/api/departements`).then(r => r.json());
    setDepartements(data);
    setLoading(false);
  };

  useEffect(() => { fetchDepts(); }, []);

  const handleDelete = async (id: number) => {
    if (!confirm('Supprimer ce département ?')) return;
    await fetch(`${API}/api/departements/${id}`, { method: 'DELETE' });
    fetchDepts();
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Départements</h1>

      <div className="mb-6">
        <DepartementForm
          initial={editing}
          onSaved={() => { setEditing(null); fetchDepts(); }}
        />
      </div>

      {loading ? (
        <p className="text-gray-500">Chargement...</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {departements.map(d => (
            <div key={d.id}
              className="bg-white rounded-xl shadow p-5 flex justify-between items-center border border-gray-100">
              <div>
                <p className="font-semibold text-gray-800">{d.nom}</p>
                <p className="text-xs text-gray-400">ID : {d.id}</p>
              </div>
              <div className="flex gap-2">
                <button
                  onClick={() => setEditing(d)}
                  className="text-sm bg-yellow-400 hover:bg-yellow-500 text-white px-3 py-1 rounded transition">
                  Modifier
                </button>
                <button
                  onClick={() => handleDelete(d.id)}
                  className="text-sm bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded transition">
                  Supprimer
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
