'use client';

interface EtudiantCardProps {
  etudiant: {
    id: number;
    nom: string;
    cin: string;
    email: string;
    age: number;
    departementNom?: string;
    anneePremiereInscription: number;
  };
  onDelete: (id: number) => void;
}

export default function EtudiantCard({ etudiant, onDelete }: EtudiantCardProps) {
  return (
    <div className="bg-white rounded-xl shadow p-5 flex flex-col gap-2 border border-gray-100 hover:shadow-md transition">
      <div className="flex justify-between items-start">
        <div>
          <h2 className="text-lg font-semibold text-gray-800">{etudiant.nom}</h2>
          <span className="text-sm text-gray-500">CIN : {etudiant.cin}</span>
        </div>
        <span className="bg-blue-100 text-blue-700 text-xs font-medium px-2 py-1 rounded">
          {etudiant.departementNom || 'Sans département'}
        </span>
      </div>
      <p className="text-sm text-gray-600">📧 {etudiant.email}</p>
      <p className="text-sm text-gray-600">🎂 {etudiant.age} ans</p>
      <p className="text-sm text-gray-600">📅 Inscrit en {etudiant.anneePremiereInscription}</p>
      <div className="flex gap-2 mt-2">
        <a
          href={`/etudiants/${etudiant.id}`}
          className="flex-1 text-center text-sm bg-yellow-400 hover:bg-yellow-500 text-white py-1 rounded transition">
          Modifier
        </a>
        <button
          onClick={() => onDelete(etudiant.id)}
          className="flex-1 text-sm bg-red-500 hover:bg-red-600 text-white py-1 rounded transition">
          Supprimer
        </button>
      </div>
    </div>
  );
}
